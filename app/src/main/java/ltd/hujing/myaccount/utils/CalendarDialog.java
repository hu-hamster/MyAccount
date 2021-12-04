package ltd.hujing.myaccount.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.adapter.CalendarAdapter;
import ltd.hujing.myaccount.db.DBManager;

public class CalendarDialog extends Dialog implements View.OnClickListener {
    private ImageView closeIv;
    private GridView gv;
    private LinearLayout hsvLayout;
    private List<TextView> hsvViewList;
    private List<Integer> yearList;
    private int selectPos = -1 ;  //表示被点击的年份的位置
    private CalendarAdapter calendarAdapter;
    private int selectMonth = -1;
    private OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public interface OnRefreshListener{
        public void onRefresh(int selPos, int year, int month);
    }

    public CalendarDialog(@NonNull Context context,int selectPos, int selectMonth) {
        super(context);
        this.selectPos=selectPos;
        this.selectMonth=selectMonth;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calendar);
        gv = findViewById(R.id.dialog_calendar_gv);
        closeIv = findViewById(R.id.dialog_calendar_iv);
        hsvLayout = findViewById(R.id.dialog_calendar_layout);
        closeIv.setOnClickListener(this);
        //向横向的ScrollView当中添加View
        addViewToLayout();
        initGridView();
        //设置GridView当中每一个item的点击事件
        setGVListener();
    }

    private void setGVListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                calendarAdapter.setSelPos(position);
                calendarAdapter.notifyDataSetInvalidated();
                int year = calendarAdapter.getYear();
                int month = position+1;
                //获取到被选中的年份和月份
                onRefreshListener.onRefresh(selectPos,year,month);
                cancel();
            }
        });
    }

    private void initGridView() {
        int selYear = yearList.get(selectPos);
        calendarAdapter = new CalendarAdapter(getContext(), selYear);
        if (selectMonth == -1) {
            int month = Calendar.getInstance().get(Calendar.MONTH);
            calendarAdapter.setSelPos(month);
        }else{
            calendarAdapter.setSelPos(selectMonth - 1);
        }
        gv.setAdapter(calendarAdapter);
    }

    //向横向的ScrollView中添加View方法
    private void addViewToLayout() {
        hsvViewList = new ArrayList<>();   //将添加进入线性布局当中的TextView进行同一的管理的集合
        yearList = DBManager.getYearListFromAccounttb();   //获取数据库中存储了多少个年份
        //如果数据库当中没有记录，就添加今年的记录
        if (yearList.size()==0) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            yearList.add(year);
        }
        //遍历年份，有几年就想ScrollView中添加几个view
        for(int i = 0;i<yearList.size();i++){
            int year = yearList.get(i);
            View view = getLayoutInflater().inflate(R.layout.item_dialogcal_hsv,null);
            hsvLayout.addView(view);  //将view添加到布局中
            TextView hsvTv = view.findViewById(R.id.item_dialogcal_hsv_tv);
            hsvTv.setText(year+"");
            hsvViewList.add(hsvTv);
        }
        if (selectPos==-1) {
            selectPos = hsvViewList.size() - 1;     //设置被选中的是最近的年份
        }
        changeTvbg(selectPos);     //将最后一个设置为选中状态
        setHSVClickListener();     //设置每一个View的监听事件
    }

    //给横向的ScrollView中每一个TextView设置点击事件
    private void setHSVClickListener() {
        for(int i = 0; i< hsvViewList.size(); i++){
            TextView textView =  hsvViewList.get(i);
            final int pos = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeTvbg(pos);
                    selectPos = pos;
                    //获取被选中的年份，然后下面的GridView显示数据源会发生变化
                    Integer year = yearList.get(selectPos);
                    calendarAdapter.setYear(year);
                }
            });
        }
    }

    //传入被选中的位置，改变此位置的背景和文字的颜色
    private void changeTvbg(int selectPos) {
        for (int i = 0; i<hsvViewList.size();i++){
            TextView textView = hsvViewList.get(i);
            textView.setBackgroundResource(R.drawable.dialog_btn_bg);
            textView.setTextColor(Color.BLACK);
        }
        TextView selView = hsvViewList.get(selectPos);
        selView.setBackgroundResource(R.drawable.main_recordbtn_bg);
        selView.setTextColor(Color.WHITE);
    }

    //设置Dialog的尺寸和屏幕的尺寸一致
    public void setDialogSize(){
        //获取当前窗口对象
        Window window = getWindow();
        //获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
        //获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int)(d.getWidth());  //对话框窗口为屏幕窗口
        wlp.gravity = Gravity.TOP;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_calendar_iv:
                cancel();
                break;
        }
    }
}
