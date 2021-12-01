package ltd.hujing.myaccount.ui.addinfo;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.adapter.TypeBaseAdapter;
import ltd.hujing.myaccount.db.DBManager;
import ltd.hujing.myaccount.db.TypeBean;
import ltd.hujing.myaccount.utils.KeyBoardUtils;

/**
 * 支出模块
 */
public class OutcomeFragment extends Fragment {

    private KeyboardView keyboardView;
    private EditText moneyEt;
    private ImageView typeIv;
    private TextView typeTv,descriptionTv,timeTv;
    private GridView typeGv;
    private List<TypeBean> typeBeanList;

    public void initView(View view){
        keyboardView = view.findViewById(R.id.frag_record_keyboard);
        moneyEt = view.findViewById(R.id.frag_record_et_money);
        typeIv = view.findViewById(R.id.frag_record_iv);
        typeGv = view.findViewById(R.id.frag_record_gv);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        descriptionTv = view.findViewById(R.id.frag_record_tv_description);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        //显示键盘
        KeyBoardUtils keyBoardUtils = new KeyBoardUtils(keyboardView, moneyEt);
        keyBoardUtils.showKeyboard();
        //设置监听接口，捕捉确定按钮
        keyBoardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                //点击了确定按钮->更新数据库->返回上一级
            }
        });
    }

    public OutcomeFragment() {
        // Required empty public constructor
    }

    public static OutcomeFragment newInstance(String param1, String param2) {
        OutcomeFragment fragment = new OutcomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_outcome, container, false);
        initView(root);
        //添加给GridView填充数据的方法
        loadDataToGV();
        return root;
    }
    //给GridView填充数据
    private void loadDataToGV() {
        typeBeanList = new ArrayList<>();
        TypeBaseAdapter adapter = new TypeBaseAdapter(getContext(),typeBeanList);
        typeGv.setAdapter(adapter);
        //获取数据库当中的数据源
        List<TypeBean> outlist = DBManager.getTypeList(0);
        typeBeanList.addAll(outlist);
        adapter.notifyDataSetChanged();
    }
}