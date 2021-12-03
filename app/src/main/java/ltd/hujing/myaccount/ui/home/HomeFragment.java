package ltd.hujing.myaccount.ui.home;

import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.databinding.FragmentHomeBinding;
import ltd.hujing.myaccount.db.AccountBean;
import ltd.hujing.myaccount.db.DBManager;
import ltd.hujing.myaccount.ui.addinfo.addincome;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;     //展示最近收支
    List<AccountBean> mDatas;
    MyRecycleViewAdapter adapter;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //设置副标事件
        FloatingActionButton floatingActionButton = root.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), addincome.class);
                startActivity(intent);
            }
        });
        mDatas = new ArrayList<>();
        loadDBDate();
        //获取recycleView
        recyclerView = root.findViewById(R.id.home_recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置Adapter
        adapter = new MyRecycleViewAdapter(mDatas);
        recyclerView.setAdapter(adapter);
        return root;
    }


    private void loadDBDate(){
        List<AccountBean> accountBeans = DBManager.getAccountListFromAccounttb();
        mDatas.clear();
        mDatas.addAll(accountBeans);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDBDate();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;


    }

    private class MyRecycleViewAdapter extends RecyclerView.Adapter {
        public static final int ITEM_TYPE_HEADER = 0;
        public static final int ITEM_TYPE_CONTENT = 1;
        private int mHeaderCount = 1;      //定义头部View个数
        private List<AccountBean> mDatas;

        public MyRecycleViewAdapter(List<AccountBean> mDatas) {
            this.mDatas = mDatas;
        }

        public boolean isHeaderView(int position) {
            return mHeaderCount != 0 && position < mHeaderCount;
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
            if(viewType == ITEM_TYPE_HEADER){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header,parent,false);
                return new HeaderViewHolder(view);
            }else{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mainlv, parent,false);
                return new MyViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof HeaderViewHolder){
                HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
//                viewHolder.getAlltv().setText(0);

            }else{
                MyViewHolder viewHolder = (MyViewHolder) holder;
                viewHolder.getTypeIv().setImageResource(mDatas.get(position).getImageid());
                viewHolder.getTypeTv().setText(mDatas.get(position).getTypename());
                viewHolder.getDescriptionTv().setText(mDatas.get(position).getDescription());
                viewHolder.getMoneyTv().setText(String.valueOf(mDatas.get(position).getMoney()));
                viewHolder.getTimeTv().setText(mDatas.get(position).getTime());
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size() + mHeaderCount;
        }
        //头部holder
        private  class HeaderViewHolder extends RecyclerView.ViewHolder{


            private TextView alltv, incometv, outcometv;
            public HeaderViewHolder(@NonNull View view) {
                super(view);
                setAlltv(view.findViewById(R.id.item_header_all));
                setIncometv(view.findViewById(R.id.item_header_income));
                setOutcometv(view.findViewById(R.id.item_header_outcome));
            }

            public TextView getAlltv() {
                return alltv;
            }

            public void setAlltv(TextView alltv) {
                this.alltv = alltv;
            }

            public TextView getIncometv() {
                return incometv;
            }

            public void setIncometv(TextView incometv) {
                this.incometv = incometv;
            }

            public TextView getOutcometv() {
                return outcometv;
            }

            public void setOutcometv(TextView outcometv) {
                this.outcometv = outcometv;
            }
        }
        //内容holder
        private class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView typeIv;
            private TextView typeTv;
            private TextView descriptionTv;
            private TextView timeTv;
            private TextView moneyTv;
            public MyViewHolder(View view) {
                super(view);
                setTypeIv(view.findViewById(R.id.item_mainlv_iv));
                setTypeTv(view.findViewById(R.id.item_mainlv_tv_typename));
                setDescriptionTv(view.findViewById(R.id.item_mainlv_tv_description));
                setTimeTv(view.findViewById(R.id.item_mainlv_tv_time));
                setMoneyTv(view.findViewById(R.id.item_mainlv_tv_money));

            }

            public ImageView getTypeIv() {
                return typeIv;
            }

            public void setTypeIv(ImageView typeIv) {
                this.typeIv = typeIv;
            }

            public TextView getTypeTv() {
                return typeTv;
            }

            public void setTypeTv(TextView typeTv) {
                this.typeTv = typeTv;
            }

            public TextView getDescriptionTv() {
                return descriptionTv;
            }

            public void setDescriptionTv(TextView descriptionTv) {
                this.descriptionTv = descriptionTv;
            }

            public TextView getTimeTv() {
                return timeTv;
            }

            public void setTimeTv(TextView timeTv) {
                this.timeTv = timeTv;
            }

            public TextView getMoneyTv() {
                return moneyTv;
            }

            public void setMoneyTv(TextView moneyTv) {
                this.moneyTv = moneyTv;
            }
        }
    }
}