package ltd.hujing.myaccount.ui.analyse;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ltd.hujing.myaccount.R;


public class IncomeAnalyseFragment extends Fragment {



    public IncomeAnalyseFragment() {
        // Required empty public constructor
    }


    public static IncomeAnalyseFragment newInstance() {
        IncomeAnalyseFragment fragment = new IncomeAnalyseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_income_analyse, container, false);
    }
}