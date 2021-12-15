package ltd.hujing.myaccount.ui.classification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.addinfo.IncomeFragment;


public class ClassificationFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;


    public ClassificationFragment() {
        // Required empty public constructor
    }


    public static ClassificationFragment newInstance() {
        ClassificationFragment fragment = new ClassificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_classification, container, false);
        initFrag(root);   //初始化组件
        return root;
    }

    private void initFrag(View view) {
        tabLayout = view.findViewById(R.id.classification_tab);
        viewPager2 = view.findViewById(R.id.classification_viewpager2);
        List<Fragment> fragmentList = new ArrayList<>();
        IncomeClassification incomeClassification = new IncomeClassification();
        OutcomeClassification outcomeClassification = new OutcomeClassification();
        fragmentList.add(outcomeClassification);
        fragmentList.add(incomeClassification);
        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
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
}