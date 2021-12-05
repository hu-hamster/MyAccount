package ltd.hujing.myaccount.ui.analyse;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.db.DBManager;
import ltd.hujing.myaccount.utils.CalendarDialog;


public class AnalyseFragment extends Fragment implements View.OnClickListener{

    private Button inButton, outButton;
    private TextView dataTv, inTv, outTv;
    private ViewPager2 chartViewpager2;
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
        inButton.setOnClickListener(this);
        outButton.setOnClickListener(this);
        return view;
    }

    //初始化时间的方法
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        
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

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.analyse_btn_in:
                setButtonStyle(1);
                break;
            case R.id.analyse_btn_out:
                setButtonStyle(0);
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