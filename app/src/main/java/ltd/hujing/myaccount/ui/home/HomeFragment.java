package ltd.hujing.myaccount.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.addinfo.addincome;
import ltd.hujing.myaccount.db.AccountBean;
import ltd.hujing.myaccount.db.DBManager;


/*
* 主界面模块，recycleview实现条例显示
* 当前任务：实现查找
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;     //展示最近收支
    List<AccountBean> mDatas;
    MyRecycleViewAdapter adapter;
    double incomeMoney;
    double outcomeMoney;
    double allMoney;
    int year;
    int month;
    int day;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //菜单可见
        setHasOptionsMenu(true);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //设置副标事件
        FloatingActionButton floatingActionButton = root.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), addincome.class);
                startActivity(intent);
            }
        });
        initTime();
        mDatas = new ArrayList<>();
        loadDBDate();
        setTopTvShow();
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

    /* 获取今日的具体时间*/
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDBDate();
        setTopTvShow();
        adapter.notifyDataSetChanged();

    }
    //设置头布局当中文本内容的显示
    private void setTopTvShow() {
        incomeMoney = DBManager.getSumMoneyOneMonth(year,month,1);
        System.out.println(incomeMoney);
        outcomeMoney = DBManager.getSumMoneyOneMonth(year,month,0);
        allMoney = incomeMoney+outcomeMoney;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
                view = LayoutInflater.from(HomeFragment.this.getContext()).inflate(R.layout.item_header,parent,false);
                return new HeaderViewHolder(view);
            }else{
                view = LayoutInflater.from(HomeFragment.this.getContext()).inflate(R.layout.item_mainlv, parent,false);
                return new MyViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof HeaderViewHolder){
                HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
                viewHolder.getAlltv().setText("￥ "+ allMoney);
                viewHolder.getIncometv().setText("￥ "+ incomeMoney);
                viewHolder.getOutcometv().setText("￥ "+ outcomeMoney);
            }else{
                MyViewHolder viewHolder = (MyViewHolder) holder;
                viewHolder.getTypeIv().setImageResource(mDatas.get(position-1).getImageid());
                viewHolder.getTypeTv().setText(mDatas.get(position-1).getTypename());
                viewHolder.getDescriptionTv().setText(mDatas.get(position-1).getDescription());
                viewHolder.getMoneyTv().setText(String.valueOf(mDatas.get(position-1).getMoney()));
                viewHolder.getTimeTv().setText(mDatas.get(position-1).getTime());
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
        private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener  {
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
                view.setOnCreateContextMenuListener(this);


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

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int position = getAdapterPosition()-1;
                Intent intent;
                switch (menuItem.getItemId()){
                    case 0:
                        //查看信息
                        System.out.println(String.valueOf(mDatas.get(position).getMoney()));
                        intent = new Intent(HomeFragment.this.getContext(),DetailsActivity.class);
                        intent.putExtra("details_tv_amount",String.valueOf(mDatas.get(position).getMoney()));
                        intent.putExtra("details_tv_typename",mDatas.get(position).getTypename());
                        intent.putExtra("details_tv_account_type",mDatas.get(position).getKind()==1?"收入":"支出");
                        intent.putExtra("details_tv_time",mDatas.get(position).getTime());
                        intent.putExtra("details_tv_balance",DBManager.getSumMoneyAll()+"");
                        intent.putExtra("details_tv_description",mDatas.get(position).getDescription());
                        intent.putExtra("details_iv_type",mDatas.get(position).getImageid());
                        startActivity(intent);
                        break;
                    case 1:
                        AccountBean accountBean = mDatas.get(position);
                        showDeleteItemDialog(accountBean);     //删除框
                        break;
                    case 2:
                        //修改信息
                        intent = new Intent(HomeFragment.this.getActivity(), addincome.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }

            private void showDeleteItemDialog(final AccountBean accountBean) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeFragment.this.getContext());
                builder.setTitle("提示信息").setMessage("是否删除该信息")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int clickId = accountBean.getId();
                                //删除
                                DBManager.deleteItemFromAccounttbById(clickId);
                                mDatas.remove(accountBean);        //实时刷新，移除集合当中的对象
                                notifyDataSetChanged();    //更新数据
                                setTopTvShow();
                            }
                        });
                builder.create().show();    //显示对话框
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                MenuItem menuItemDetail=contextMenu.add(ContextMenu.NONE, 0, ContextMenu.NONE, "详情");
                MenuItem menuItemDelete=contextMenu.add(ContextMenu.NONE, 1, ContextMenu.NONE, "删除");
                MenuItem menuItemEdit=contextMenu.add(ContextMenu.NONE, 2, ContextMenu.NONE, "修改");
                menuItemDetail.setOnMenuItemClickListener(this);
                menuItemDelete.setOnMenuItemClickListener(this);
                menuItemEdit.setOnMenuItemClickListener(this);

            }
        }
    }

    //为fragment设置菜单项
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_to_top, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.action_to_top){
            Toast.makeText(this.getContext(),"123",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}