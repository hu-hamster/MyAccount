package ltd.hujing.myaccount.ui.analyse;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ltd.hujing.myaccount.R;


public class AnalyseFragment extends Fragment {


    public AnalyseFragment() {
        // Required empty public constructor
    }


    public static AnalyseFragment newInstance() {
        AnalyseFragment fragment = new AnalyseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analyse, container, false);
        return view;
    }
}