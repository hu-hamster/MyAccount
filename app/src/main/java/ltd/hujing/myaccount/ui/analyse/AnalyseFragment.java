package ltd.hujing.myaccount.ui.analyse;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.db.DBManager;
import ltd.hujing.myaccount.utils.CalendarDialog;


public class AnalyseFragment extends Fragment implements View.OnClickListener{

    private Button inButton, outButton;
    private TextView dataTv, inTv, outTv;
    private ViewPager2 chartViewpager2;
    private IncomeChartFragment incomeChartFragment;
    private OutcomeChartFragment outcomeChartFragment;
    private int year, month;
    private int selectPos = -1,selectMonth =-1;
    List<Fragment> chartFraglist;
    public AnalyseFragment() {
        // Required empty public constructor
    }


    public static AnalyseFragment newInstance() {
        AnalyseFragment fragment = new AnalyseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置菜单可见
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analyse, container, false);
        initView(view);
        initTime();
        initFrag();
        initStatistics(year,month);
        inButton.setOnClickListener(this);
        outButton.setOnClickListener(this);
        return view;
    }



    //初始化Fragment
    private void initFrag() {
        chartFraglist = new ArrayList<>();
        //添加fragment对象
        incomeChartFragment = new IncomeChartFragment();
        outcomeChartFragment = new OutcomeChartFragment();
        //添加数据到fragment中
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        incomeChartFragment.setArguments(bundle);
        outcomeChartFragment.setArguments(bundle);
        //将Fragment添加到数据源中
        chartFraglist.add(outcomeChartFragment);
        chartFraglist.add(incomeChartFragment);
        //添加适配器
        MyViewpagerAdapter myViewpagerAdapter = new MyViewpagerAdapter(this);
        chartViewpager2.setAdapter(myViewpagerAdapter);
        //设置viewpager2滑动监听
        chartViewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setButtonStyle(position);
            }
        });


    }

    //内部类、设置适配器
    private class MyViewpagerAdapter extends FragmentStateAdapter{
        public MyViewpagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return chartFraglist.get(position);
        }

        @Override
        public int getItemCount() {
            return chartFraglist.size();
        }
    }



    //统计某年某月的收支情况数据
    private void initStatistics(int year, int month) {
        double inMoneyOneMonth =  DBManager.getSumMoneyOneMonth(year,month,1);    //收入总金额
        double outMoneyOneMonth = DBManager.getSumMoneyOneMonth(year,month,0);    //支出总金额
        int inCountItemOneMonth = DBManager.getCountItemOneMonth(year,month,1);   //收入多少笔
        int outCountItemOneMonth = DBManager.getCountItemOneMonth(year,month,0);  //支出多少笔
        dataTv.setText(year+"年"+month+"月账单");
        inTv.setText("共"+inCountItemOneMonth+"笔收入，￥"+inMoneyOneMonth);
        outTv.setText("共"+outCountItemOneMonth+"笔支出，￥"+outMoneyOneMonth);
    }

    //初始化时间的方法
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
    }

    //初始化控件
    private void initView(View view) {
        inButton = view.findViewById(R.id.analyse_btn_in);
        outButton = view.findViewById(R.id.analyse_btn_out);
        dataTv = view.findViewById(R.id.analyse_tv_date);
        inTv = view.findViewById(R.id.analyse_tv_in);
        outTv = view.findViewById(R.id.analyse_tv_out);
        chartViewpager2 =  view.findViewById(R.id.analyse_viewpager2);
    }


    //为fragment设置菜单项
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.analyse_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.analyse_calendar){
            showCalendarDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    //显示日历
    private void showCalendarDialog() {
        CalendarDialog dialog = new CalendarDialog(this.getContext(), selectPos, selectMonth);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnRefreshListener(new CalendarDialog.OnRefreshListener() {
            @Override
            public void onRefresh(int selPos, int year, int month) {
                AnalyseFragment.this.selectPos = selPos;
                AnalyseFragment.this.selectMonth = month;
                initStatistics(year,month);
                incomeChartFragment.setDate(year,month);
                outcomeChartFragment.setDate(year,month);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.analyse_btn_in:
                setButtonStyle(1);
                chartViewpager2.setCurrentItem(1);
                break;
            case R.id.analyse_btn_out:
                setButtonStyle(0);
                chartViewpager2.setCurrentItem(0);
                break;
        }
    }

    //设置button样式发生改变  支出-0 收入-1
    private void setButtonStyle(int kind){
        if (kind == 0) {
            outButton.setBackgroundResource(R.drawable.main_recordbtn_bg);
            outButton.setTextColor(Color.WHITE);
            inButton.setBackgroundResource(R.drawable.dialog_btn_bg);
            inButton.setTextColor(Color.BLACK);
        }else if(kind == 1){
            inButton.setBackgroundResource(R.drawable.main_recordbtn_bg);
            inButton.setTextColor(Color.WHITE);
            outButton.setBackgroundResource(R.drawable.dialog_btn_bg);
            outButton.setTextColor(Color.BLACK);
        }
    }
}