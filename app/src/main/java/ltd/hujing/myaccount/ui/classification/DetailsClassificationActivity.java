package ltd.hujing.myaccount.ui.classification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.addinfo.addinfo;
import ltd.hujing.myaccount.db.AccountBean;
import ltd.hujing.myaccount.db.DBManager;
import ltd.hujing.myaccount.ui.home.DetailsActivity;
import ltd.hujing.myaccount.ui.home.HomeFragment;

public class DetailsClassificationActivity extends AppCompatActivity {
    private TextView typenameTv, accountTv, moneyTv;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private int imageId,kind;
    private String typename;
    private List<AccountBean> mDatas;
    private MyRecycleViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_classification);
        ActionBar actionBar = getSupportActionBar();   //隐藏自带标题栏
        if(actionBar != null){
            actionBar.hide();
        }
        initView();
        loadDBDate();
        initFrag();

    }

    private void loadDBDate() {
        List<AccountBean> accountBeans = DBManager.getAccountListFromAccounttbByTypename(typename,kind);
        mDatas.clear();
        mDatas.addAll(accountBeans);
    }

    private void initFrag() {
        setTopTvShow();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyRecycleViewAdapter(mDatas);
        recyclerView.setAdapter(adapter);

    }

    private void setTopTvShow() {
        typenameTv.setText(typename);
        imageView.setImageResource(imageId);
        int account = DBManager.getCountItemFromTypename(typename,kind);
        accountTv.setText("共"+account+"笔");
        double income = DBManager.getSumMoneyFromTypename(typename,kind);
        if(kind==0){
            moneyTv.setText("支出总额："+income);
        }else{
            moneyTv.setText("收入总额："+income);
        }
    }

    private void initView() {
        typenameTv = findViewById(R.id.details_classification_tv_typename);
        accountTv = findViewById(R.id.details_classification_tv_account);
        moneyTv = findViewById(R.id.details_classification_tv_money);
        imageView = findViewById(R.id.details_classification_ig);
        recyclerView = findViewById(R.id.details_classification_recycle);
        Intent intent = getIntent();
        typename = intent.getStringExtra("typename");
        imageId = intent.getIntExtra("imageid",0);
        kind = intent.getIntExtra("kind",0);
        mDatas = new ArrayList<>();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.details_classification_back:
                finish();
                break;
            case R.id.details_classification_to_top:
                recyclerView.smoothScrollToPosition(0);
                break;

        }
    }

    private class MyRecycleViewAdapter extends RecyclerView.Adapter {
        List<AccountBean> mDatas;
        public MyRecycleViewAdapter(List<AccountBean> mDatas) {
            this.mDatas = mDatas;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mainlv, parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            MyRecycleViewAdapter.MyViewHolder viewHolder = (MyRecycleViewAdapter.MyViewHolder) holder;
            //添加显示逻辑
            viewHolder.getRelativeLayout().setVisibility(View.VISIBLE);
            if(kind == 0){
                viewHolder.getIncomeTv().setText("");
                viewHolder.getOutcomeTv().setText("支："+Math.abs(DBManager.getSumMoneyOneDay(mDatas.get(position).getYear(),mDatas.get(position).getMonth(),mDatas.get(position).getDay(),0)));
            }else {
                viewHolder.getIncomeTv().setText("收："+DBManager.getSumMoneyOneDay(mDatas.get(position).getYear(),mDatas.get(position).getMonth(),mDatas.get(position).getDay(),1));
                viewHolder.getOutcomeTv().setText("");
            }
            viewHolder.getTypeIv().setImageResource(mDatas.get(position).getImageid());
            viewHolder.getTypeTv().setText(mDatas.get(position).getTypename());
            viewHolder.getDescriptionTv().setText(mDatas.get(position).getDescription());
            viewHolder.getMoneyTv().setText(String.valueOf(mDatas.get(position).getMoney()));
            viewHolder.getTimeTv().setText(mDatas.get(position).getYear()+"-"+mDatas.get(position).getMonth()+"-"+mDatas.get(position).getDay());
            if(position!=0&&mDatas.get(position).getDay()==mDatas.get(position-1).getDay()&&mDatas.get(position).getMonth()==mDatas.get(position-1).getMonth()&&mDatas.get(position).getYear()==mDatas.get(position-1).getYear()){
                viewHolder.getRelativeLayout().setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            private ImageView typeIv;
            private RelativeLayout relativeLayout;
            private TextView typeTv;
            private TextView descriptionTv;
            private TextView timeTv;
            private TextView moneyTv;
            private TextView incomeTv;
            private TextView outcomeTv;
            public MyViewHolder(View view) {
                super(view);
                setRelativeLayout(view.findViewById(R.id.item_mainlv_rl));
                setIncomeTv(view.findViewById(R.id.item_mainlv_tv_income));
                setOutcomeTv(view.findViewById(R.id.item_mainlv_tv_outcome));
                setTypeIv(view.findViewById(R.id.item_mainlv_iv));
                setTypeTv(view.findViewById(R.id.item_mainlv_tv_typename));
                setDescriptionTv(view.findViewById(R.id.item_mainlv_tv_description));
                setTimeTv(view.findViewById(R.id.item_mainlv_tv_time));
                setMoneyTv(view.findViewById(R.id.item_mainlv_tv_money));
                view.setOnCreateContextMenuListener(this);
            }

            public RelativeLayout getRelativeLayout() {
                return relativeLayout;
            }

            public void setRelativeLayout(RelativeLayout relativeLayout) {
                this.relativeLayout = relativeLayout;
            }

            public TextView getIncomeTv() {
                return incomeTv;
            }

            public void setIncomeTv(TextView incomeTv) {
                this.incomeTv = incomeTv;
            }

            public TextView getOutcomeTv() {
                return outcomeTv;
            }

            public void setOutcomeTv(TextView outcomeTv) {
                this.outcomeTv = outcomeTv;
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
                        intent = new Intent(DetailsClassificationActivity.this, DetailsActivity.class);
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
                        adapter.notifyDataSetChanged();
                        break;
                    case 2:
                        //修改信息
                        intent = new Intent(DetailsClassificationActivity.this, addinfo.class);
                        intent.putExtra("flag",1);   //修改标志位
                        intent.putExtra("id",mDatas.get(position).getId());
                        startActivity(intent);
                        break;
                }
                return false;
            }

            private void showDeleteItemDialog(final AccountBean accountBean) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsClassificationActivity.this);
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


}