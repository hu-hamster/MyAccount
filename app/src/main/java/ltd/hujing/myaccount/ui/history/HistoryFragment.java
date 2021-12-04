package ltd.hujing.myaccount.ui.history;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.db.AccountBean;
import ltd.hujing.myaccount.db.DBManager;
import ltd.hujing.myaccount.ui.home.DetailsActivity;
import ltd.hujing.myaccount.utils.CalendarDialog;


public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView timeTv;
    private List<AccountBean> mDatas;
    private MyRecycleViewAdapter adapter;
    private int year, month;
    private int dialogSelPos = -1;
    private int dialogSelMonth = -1;
    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        initTime();  //初始化时间

        mDatas = new ArrayList<>();

        timeTv = root.findViewById(R.id.history_tv_time);
        timeTv.setText(year+"-"+month);
        loadDate(year,month);
        recyclerView = root.findViewById(R.id.history_recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置Adapter
        adapter = new HistoryFragment.MyRecycleViewAdapter(mDatas);
        recyclerView.setAdapter(adapter);
        loadDate(year, month);

        return root;

    }

    //获取指定年月的item
    private void loadDate(int year,int month) {
        List<AccountBean> accountBeans = DBManager.getAccountListOneMonthFromAccounttb(year,month);
        mDatas.clear();
        mDatas.addAll(accountBeans);

    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDate(year,month);
        adapter.notifyDataSetChanged();
    }

    private class MyRecycleViewAdapter extends RecyclerView.Adapter {
        public static final int ITEM_TYPE_HEADER = 0;
        public static final int ITEM_TYPE_CONTENT = 1;

        private List<AccountBean> mDatas;

        public MyRecycleViewAdapter(List<AccountBean> mDatas) {
            this.mDatas = mDatas;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(HistoryFragment.this.getContext()).inflate(R.layout.item_mainlv, parent,false);
            return new HistoryFragment.MyRecycleViewAdapter.MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            HistoryFragment.MyRecycleViewAdapter.MyViewHolder viewHolder = (HistoryFragment.MyRecycleViewAdapter.MyViewHolder) holder;
            viewHolder.getTypeIv().setImageResource(mDatas.get(position).getImageid());
            viewHolder.getTypeTv().setText(mDatas.get(position).getTypename());
            viewHolder.getDescriptionTv().setText(mDatas.get(position).getDescription());
            viewHolder.getMoneyTv().setText(String.valueOf(mDatas.get(position).getMoney()));
            viewHolder.getTimeTv().setText(mDatas.get(position).getTime());

        }

        @Override
        public int getItemCount() {
            return mDatas.size() ;
        }
        //头部holder

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
                int position = getAdapterPosition();
                Intent intent;
                switch (menuItem.getItemId()){
                    case 0:
                        //查看信息
                        System.out.println(String.valueOf(mDatas.get(position).getMoney()));
                        intent = new Intent(HistoryFragment.this.getContext(), DetailsActivity.class);
                        intent.putExtra("details_tv_amount",String.valueOf(mDatas.get(position).getMoney()));
                        intent.putExtra("details_tv_typename",mDatas.get(position).getTypename());
                        intent.putExtra("details_tv_account_type",mDatas.get(position).getKind()==1?"收入":"支出");
                        intent.putExtra("details_tv_time",mDatas.get(position).getTime());
                        intent.putExtra("details_tv_balance", DBManager.getSumMoneyAll()+"");
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

                        break;
                }
                return false;
            }

            private void showDeleteItemDialog(final AccountBean accountBean) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryFragment.this.getContext());
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
        inflater.inflate(R.menu.history_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.history_calendar){
            CalendarDialog calendarDialog = new CalendarDialog(this.getContext(),this.dialogSelPos,this.dialogSelMonth);
            calendarDialog.show();
            calendarDialog.setDialogSize();
            calendarDialog.setOnRefreshListener(new CalendarDialog.OnRefreshListener() {
                @Override
                public void onRefresh(int selPos, int year, int month) {
                    timeTv.setText(year+"-"+month+"");
                    loadDate(year,month);
                    dialogSelPos = selPos;
                    dialogSelMonth = month;
                    adapter.notifyDataSetChanged();
                }
            });
        }else if(item.getItemId()==R.id.history_delete){
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("删除提示")
                    .setMessage("您确定要删除所有记录吗？\n注意：删除后无法恢复!")
                    .setPositiveButton("取消",null)
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DBManager.deleteAllAccount();
                            Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }
}