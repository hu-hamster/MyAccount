package ltd.hujing.myaccount.ui.analyse;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.db.BarChartItemBean;
import ltd.hujing.myaccount.db.ChartItemBean;
import ltd.hujing.myaccount.db.DBManager;
import ltd.hujing.myaccount.utils.DoubleUtils;


public class IncomeChartFragment extends Fragment {
    RecyclerView recyclerView;
    int year;
    int month;
    private MyRecycleViewAdapter adapter;
    private List<ChartItemBean> mDatas;


    public MyRecycleViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MyRecycleViewAdapter adapter) {
        this.adapter = adapter;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_income_chart, container, false);

        //获取Activity传递的对象
        Bundle bundle = getArguments();
        year = bundle.getInt("year");
        month = bundle.getInt("month");
        //设置数据源
        mDatas = new ArrayList<>();

        //设置适配器
        recyclerView = root.findViewById(R.id.frag_chart_lv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyRecycleViewAdapter(getContext(),mDatas);
        recyclerView.setAdapter(adapter);
        //添加头布局

        return root;
    }

    public void loadData(int year, int month, int kind) {
        List<ChartItemBean>list = DBManager.getChartListFromAccounttb(year,month,kind);
        mDatas.clear();
        mDatas.addAll(list);

    }

    public void onResume() {
        super.onResume();
        loadData(year, month, 1);
        adapter.notifyDataSetChanged();
    }


    public void setDate(int year, int month){
        loadData(year,month,1);

    }

    private class MyRecycleViewAdapter extends RecyclerView.Adapter {
        public static final int ITEM_TYPE_HEADER = 0;
        public static final int ITEM_TYPE_CONTENT = 1;
        private int mHeaderCount = 1;      //定义头部View个数
        Context context;
        List<ChartItemBean> mDatas;
        LayoutInflater inflater;

        public MyRecycleViewAdapter(Context context, List<ChartItemBean> mDatas) {
            this.context = context;
            this.mDatas = mDatas;
            inflater = LayoutInflater.from(context);
        }
        //判断当前item类型
        @Override
        public int getItemViewType(int position) {
            if (mHeaderCount != 0 && position < mHeaderCount) {
                //头部View
                return ITEM_TYPE_HEADER;
            }
            else{
                //内容View
                return ITEM_TYPE_CONTENT;
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            if (viewType == ITEM_TYPE_HEADER){
                view = LayoutInflater.from(IncomeChartFragment.this.getContext()).inflate(R.layout.item_analysefrag_header,parent,false);
                return new HeaderHolder(view);
            }else{
                view = LayoutInflater.from(IncomeChartFragment.this.getContext()).inflate(R.layout.item_analysefrag_lv,parent,false);
                return new MyViewHolder(view);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof HeaderHolder){

            }else{
                MyViewHolder viewHolder = (MyViewHolder) holder;
                viewHolder.getImageView().setImageResource(mDatas.get(position-1).getsImageId());
                viewHolder.getTypeTv().setText(mDatas.get(position-1).getType());
                viewHolder.getRadioTv().setText(DoubleUtils.ratioToPercent(mDatas.get(position-1).getRatio()));
                viewHolder.getTotalTv().setText("￥ "+mDatas.get(position-1).getTotalMoney());
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size()+1;
        }
        private class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView typeTv, radioTv, totalTv;
            private ImageView imageView;
            public MyViewHolder(View view) {
                super(view);
                typeTv = view.findViewById(R.id.item_analysefrag_tv_type);
                radioTv = view.findViewById(R.id.item_analysefrag_tv_pert);
                totalTv = view.findViewById(R.id.item_analysefrag_tv_sum);
                imageView = view.findViewById(R.id.item_analysefrag_iv);
            }

            public TextView getTypeTv() {
                return typeTv;
            }

            public void setTypeTv(TextView typeTv) {
                this.typeTv = typeTv;
            }

            public TextView getRadioTv() {
                return radioTv;
            }

            public void setRadioTv(TextView radioTv) {
                this.radioTv = radioTv;
            }

            public TextView getTotalTv() {
                return totalTv;
            }

            public void setTotalTv(TextView totalTv) {
                this.totalTv = totalTv;
            }

            public ImageView getImageView() {
                return imageView;
            }

            public void setImageView(ImageView imageView) {
                this.imageView = imageView;
            }
        }

        private class HeaderHolder extends RecyclerView.ViewHolder {
            BarChart barChart;
            TextView chartTv;     //如果没有收支情况，显示的TextView

            public HeaderHolder(View view) {
                super(view);
                barChart = view.findViewById(R.id.item_analysefrag_chart);
                chartTv = view.findViewById(R.id.item_analysefrag_header_tv);
                //设置柱状图不显示描述
                barChart.getDescription().setEnabled(false);
                //设置柱状图内边距
                barChart.setExtraOffsets(20, 20, 20, 20);
                //设置坐标轴
                setAxis(year, month);
                setAxisData(year,month);
            }



            //设置柱状图坐标轴的显示
            private void setAxis(int year, int month) {
                //设置X轴
                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //设置x轴显示在下方
                xAxis.setDrawGridLines(true);  //设置绘制该轴的网格线
                //设置x轴标签的个数
                xAxis.setLabelCount(31);
                xAxis.setTextSize(12f);  //x轴标签的大小
                //设置X轴显示的值的格式
                xAxis.setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        int val = (int) value;
                        if (val == 0) {
                            return month + "-1";
                        }
                        if (val == 14) {
                            return month + "-15";
                        }
                        //根据不同的月份，显示最后一天的位置
                        if (month == 2) {
                            if (val == 27) {
                                return month + "-28";
                            }
                        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                            if (val == 30) {
                                return month + "-31";
                            }
                        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                            if (val == 29) {
                                return month + "-30";
                            }
                        }
                        return "";
                    }
                });
                xAxis.setYOffset(10); // 设置标签对x轴的偏移量，垂直方向

                // y轴在子类的设置
                setYAxis(year, month);
            }

            protected void setAxisData(int year, int month) {
                List<IBarDataSet> sets = new ArrayList<>();
//        获取这个月每天的支出总金额，
                List<BarChartItemBean> list = DBManager.getSumMoneyOneDayInMonth(year, month, 1);
                if (list.size() == 0) {
                    barChart.setVisibility(View.GONE);
                    chartTv.setVisibility(View.VISIBLE);
                }else{
                    barChart.setVisibility(View.VISIBLE);
                    chartTv.setVisibility(View.GONE);
//            设置有多少根柱子
                    List<BarEntry> barEntries1 = new ArrayList<>();
                    for (int i = 0; i < 31; i++) {
//                初始化每一根柱子，添加到柱状图当中
                        BarEntry entry = new BarEntry(i, 0.0f);
                        barEntries1.add(entry);
                    }
                    for (int i = 0; i < list.size(); i++) {
                        BarChartItemBean itemBean = list.get(i);
                        int day = itemBean.getDay();   //获取日期
                        // 根据天数，获取x轴的位置
                        int xIndex = day-1;
                        BarEntry barEntry = barEntries1.get(xIndex);
                        barEntry.setY(itemBean.getSummoney());
                    }
                    BarDataSet barDataSet1 = new BarDataSet(barEntries1, "");
                    barDataSet1.setValueTextColor(Color.BLACK); // 值的颜色
                    barDataSet1.setValueTextSize(8f); // 值的大小
                    barDataSet1.setColor(Color.parseColor("#006400")); // 柱子的颜色

                    // 设置柱子上数据显示的格式
                    barDataSet1.setValueFormatter(new IValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                            // 此处的value默认保存一位小数
                            if (value==0) {
                                return "";
                            }
                            return value + "";
                        }
                    });
                    sets.add(barDataSet1);
                    BarData barData = new BarData(sets);
                    barData.setBarWidth(0.2f); // 设置柱子的宽度
                    barChart.setData(barData);
                }

            }


            protected void setYAxis(int year, int month) {
                //        获取本月收入最高的一天为多少，将他设定为y轴的最大值
                double maxMoney = DBManager.getMaxMoneyOneDayInMonth(year, month, 1);
                float max = (float) Math.ceil(maxMoney);   // 将最大金额向上取整
//        设置y轴
                YAxis yAxis_right = barChart.getAxisRight();
                yAxis_right.setAxisMaximum(max);  // 设置y轴的最大值
                yAxis_right.setAxisMinimum(0f);  // 设置y轴的最小值
                yAxis_right.setEnabled(false);  // 不显示右边的y轴

                YAxis yAxis_left = barChart.getAxisLeft();
                yAxis_left.setAxisMaximum(max);
                yAxis_left.setAxisMinimum(0f);
                yAxis_left.setEnabled(false);
//        设置不显示图例
                Legend legend = barChart.getLegend();
                legend.setEnabled(false);
            }
        }
    }


}