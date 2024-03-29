package ltd.hujing.myaccount.ui.analyse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.db.ChartItemBean;
import ltd.hujing.myaccount.db.DBManager;


abstract public class BaseChartFragment extends Fragment {
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mDatas=new ArrayList<>();
        //设置适配器
        recyclerView = root.findViewById(R.id.frag_chart_lv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyRecycleViewAdapter(mDatas);
        recyclerView.setAdapter(adapter);
        return root;
    }

    public void loadData(int year, int month, int kind) {
        List<ChartItemBean>list = DBManager.getChartListFromAccounttb(year,month,kind);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    abstract public void setDate(int year, int month);

    private class MyRecycleViewAdapter extends RecyclerView.Adapter {
        List<ChartItemBean> mDatas;

        public MyRecycleViewAdapter(List<ChartItemBean> mDatas) {
            this.mDatas = mDatas;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(BaseChartFragment.this.getContext()).inflate(R.layout.item_analysefrag_lv,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.getImageView().setImageResource(mDatas.get(position).getsImageId());
            viewHolder.getTypeTv().setText(mDatas.get(position).getType());
            viewHolder.getRadioTv().setText(String.format("%.2f%%",mDatas.get(position).getRatio()*100));
            viewHolder.getTotalTv().setText("￥ "+mDatas.get(position).getTotalMoney());
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
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
    }
}