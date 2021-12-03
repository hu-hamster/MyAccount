package ltd.hujing.myaccount.addinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import ltd.hujing.myaccount.R;

public class addincome extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addincome);
        ActionBar actionBar = getSupportActionBar();   //隐藏自带标题栏
        if(actionBar != null){
            actionBar.hide();
        }
        tabLayout = findViewById(R.id.add_item_tab);
        viewPager2 = findViewById(R.id.add_item_viewpager2);
        //Adapter
        initPaper();

    }

    private void initPaper() {
        List<Fragment>fragmentList = new ArrayList<>();
        IncomeFragment incomefragment = new IncomeFragment();     //收入
        OutcomeFragment outcomefragment = new OutcomeFragment();    //支出
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

