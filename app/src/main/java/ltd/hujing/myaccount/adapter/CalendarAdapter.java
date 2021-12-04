package ltd.hujing.myaccount.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ltd.hujing.myaccount.R;

/*
* 历史账单界面，点击日历表，弹出对话框，当中的GridView对应的适配器
 */
public class CalendarAdapter extends BaseAdapter {
    private Context context;
    private List<String>mDatas;
    private int year;
    private int selPos = -1;

    public CalendarAdapter(Context context, int year){
        this.context = context;
        this.year = year;
        mDatas = new ArrayList<>();
        loadDatas(year);
    }

    private void loadDatas(int year) {
        for(int i = 1 ; i< 13;i++){
            String data = year +"/"+i;
            mDatas.add(data);
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        mDatas.clear();
        loadDatas(year);
        notifyDataSetChanged();
    }

    public int getSelPos() {
        return selPos;
    }

    public void setSelPos(int selPos) {
        this.selPos = selPos;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_dialogcal_gv,parent,false);
        TextView textView = convertView.findViewById(R.id.item_dialogcal_gv_tv);
        textView.setText(mDatas.get(position));
        textView.setBackgroundResource(R.color.grey_f3f3f3);
        textView.setTextColor(Color.BLACK);
        if (position == selPos) {
            textView.setBackgroundResource(R.color.grey_f3f3f3);
            textView.setTextColor(Color.RED);
        }
        return convertView;
    }
}
