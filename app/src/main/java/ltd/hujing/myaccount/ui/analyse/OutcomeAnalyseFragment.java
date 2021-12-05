package ltd.hujing.myaccount.ui.analyse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ltd.hujing.myaccount.R;


public class OutcomeAnalyseFragment extends Fragment {



    public OutcomeAnalyseFragment() {
        // Required empty public constructor
    }


    public static OutcomeAnalyseFragment newInstance() {
        OutcomeAnalyseFragment fragment = new OutcomeAnalyseFragment();
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