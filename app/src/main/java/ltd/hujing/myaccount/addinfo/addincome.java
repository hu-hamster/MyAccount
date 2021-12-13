package ltd.hujing.myaccount.addinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.db.AccountBean;
import ltd.hujing.myaccount.db.DBManager;

/*
* 添加item的具体实现，主要使用tablayouth加viewpager2
 */
public class addincome extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private int flag;  //设置修改或添加标志位
    private int id;    //如果为修改标志位，则获取item的id
    AccountBean accountBean;  //如果为修改标志位，则获得要修改的item
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addincome);
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag",0);
        accountBean = new AccountBean();
        if(flag==1){
            id = intent.getIntExtra("id",-1);
            accountBean = DBManager.getItemFromAccounttbById(id);
        }
        ActionBar actionBar = getSupportActionBar();   //隐藏自带标题栏
        if(actionBar != null){
            actionBar.hide();
        }
        tabLayout = findViewById(R.id.add_item_tab);
        viewPager2 = findViewById(R.id.add_item_viewpager2);
        //Adapter
        initPaper();  //初始化界面
        if(flag==1) viewPager2.setCurrentItem(accountBean.getKind());   //如果为修改则选择不同的界面

    }

    private void initPaper() {
        List<Fragment>fragmentList = new ArrayList<>();
        IncomeFragment incomefragment = new IncomeFragment(flag,accountBean);     //收入
        OutcomeFragment outcomefragment = new OutcomeFragment(flag,accountBean);    //支出
        fragmentList.add(outcomefragment);
        fragmentList.add(incomefragment);
        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override

            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }
            @Override
            public int getItemCount() {
                return 2;
            }
        });
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("支出");
                        break;
                    default:
                        tab.setText("收入");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }

    //返回的逻辑实现
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_item_back:
                finish();
                break;
        }
    }
}

