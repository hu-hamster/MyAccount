package ltd.hujing.myaccount.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;

import ltd.hujing.myaccount.R;

/*
* 在记录页面弹出时间对话框
* */
public class SelectTimeDialog extends Dialog implements View.OnClickListener {
    private EditText hourEt, minuteEt;
    private DatePicker datePicker;
    private Button ensurebutton,cancelbutton;
    OnEnsureListener onEnsureListener;

    public interface OnEnsureListener{
        public void onEnsure(String time, int year, int month, int day);
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public SelectTimeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);
        hourEt = findViewById(R.id.dialog_time_et_hour);
        minuteEt = findViewById(R.id.dialog_time_et_minute);
        datePicker = findViewById(R.id.dialog_time_dp);
        ensurebutton = findViewById(R.id.dialog_time_btn_ensure);
        cancelbutton = findViewById(R.id.dialog_time_btn_cancel);
        ensurebutton.setOnClickListener(this);   //添加监听事件
        cancelbutton.setOnClickListener(this);
        hideDataPickerHeader();   //隐藏头布局
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_time_btn_ensure:
                int year = datePicker.getYear();   //选择年份
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                String monStr = String.valueOf(month);
                if(month<10){
                    monStr = "0"+monStr;
                }
                String dayStr = String.valueOf(day);
                if(day<10){
                    dayStr = "0"+dayStr;
                }
                //获取
                String hourStr =hourEt.getText().toString();
                String minuteStr = minuteEt.getText().toString();
                int hour = 0;
                int minute = 0;
                if(!TextUtils.isEmpty(hourStr)){
                    hour = Integer.parseInt(hourStr);
                    hour %= 24;
                }
                if(!TextUtils.isEmpty(minuteStr)){
                    minute = Integer.parseInt(minuteStr);
                    minute%=60;
                }
                hourStr = String.valueOf(hour);
                minuteStr = String.valueOf(minute);
                if(hour<10){
                    hourStr = "0"+hourStr;
                }
                if(minute<10){
                    minuteStr = "0"+minuteStr;
                }
                String timeFormat = year + "年" + monStr + "月" + dayStr + "日 "+hourStr + ":" +minuteStr;
                if(onEnsureListener!=null){
                    onEnsureListener.onEnsure(timeFormat,year,month,day);
                }
                cancel();
                break;
            case R.id.dialog_time_btn_cancel:
                cancel();
                break;
        }
    }

    //隐藏DataPicker头布局
    private void hideDataPickerHeader(){
        ViewGroup rootview = (ViewGroup) datePicker.getChildAt(0);
        if(rootview == null){
            return;
        }
        View headerview = rootview.getChildAt(0);
        if(headerview == null){
            return;
        }
//        int headerId = getContext().getResources().getIdentifier("day_picker_selector_layout","id","android");
//        if(headerId == headerview.getId()){
//            headerview.setVisibility(View.GONE);
//            ViewGroup.LayoutParams layoutParamsRoot = rootview.getLayoutParams();
//            layoutParamsRoot.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//            rootview.setLayoutParams(layoutParamsRoot);
//            rootview.getChildAt(1);
//
//            ViewGroup animator = (ViewGroup) rootview.getChildAt(1);
//            ViewGroup.LayoutParams layoutParamsAnimator = animator.getLayoutParams();
//            layoutParamsAnimator.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//            animator.setLayoutParams(layoutParamsAnimator);
//
//            View child = animator.getChildAt(0);
//            ViewGroup.LayoutParams layoutParamsChild =  child.getLayoutParams();
//            layoutParamsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//            child.setLayoutParams(layoutParamsChild);
//            return;
//        }
        int headerId = getContext().getResources().getIdentifier("date_picker_header","id","android");
        if(headerId == headerview.getId()) {
            headerview.setVisibility(View.GONE);
        }

    }
}
