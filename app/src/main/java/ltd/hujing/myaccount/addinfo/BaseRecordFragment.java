package ltd.hujing.myaccount.addinfo;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.adapter.TypeBaseAdapter;
import ltd.hujing.myaccount.db.AccountBean;
import ltd.hujing.myaccount.db.TypeBean;
import ltd.hujing.myaccount.utils.DescriptionDialog;
import ltd.hujing.myaccount.utils.KeyBoardUtils;
import ltd.hujing.myaccount.utils.SelectTimeDialog;

/**
 * 基础模块
 * 存在bug：如果光点击确认不主动设置时间，数据库中的年月日有问题
 */
public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener {

    private KeyboardView keyboardView;
    private EditText moneyEt;
    private ImageView typeIv;
    private TextView typeTv,descriptionTv,timeTv;
    private GridView typeGv;
    private List<TypeBean> typeBeanList;
    private TypeBaseAdapter adapter;
    private AccountBean accountBean;     //将需要插入到记账本当中的数据保存成对象的形式

    public KeyboardView getKeyboardView() {
        return keyboardView;
    }

    public void setKeyboardView(KeyboardView keyboardView) {
        this.keyboardView = keyboardView;
    }

    public EditText getMoneyEt() {
        return moneyEt;
    }

    public void setMoneyEt(EditText moneyEt) {
        this.moneyEt = moneyEt;
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

    public GridView getTypeGv() {
        return typeGv;
    }

    public void setTypeGv(GridView typeGv) {
        this.typeGv = typeGv;
    }

    public List<TypeBean> getTypeBeanList() {
        return typeBeanList;
    }

    public void setTypeBeanList(List<TypeBean> typeBeanList) {
        this.typeBeanList = typeBeanList;
    }

    public TypeBaseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(TypeBaseAdapter adapter) {
        this.adapter = adapter;
    }

    public AccountBean getAccountBean() {
        return accountBean;
    }

    public void setAccountBean(AccountBean accountBean) {
        this.accountBean = accountBean;
    }

    public void initView(View view){
        keyboardView = view.findViewById(R.id.frag_record_keyboard);
        moneyEt = view.findViewById(R.id.frag_record_et_money);
        typeIv = view.findViewById(R.id.frag_record_iv);
        typeGv = view.findViewById(R.id.frag_record_gv);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        descriptionTv = view.findViewById(R.id.frag_record_tv_description);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        //初始化当前时间
        setInitTime();
        //初始化
        //设置备注和时间的点击事件
        descriptionTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        //显示键盘
        KeyBoardUtils keyBoardUtils = new KeyBoardUtils(keyboardView, moneyEt);
        keyBoardUtils.showKeyboard();
        //设置监听接口，捕捉确定按钮
        keyBoardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                //点击了确定按钮->更新数据库->返回上一级
                String moneyStr = moneyEt.getText().toString();
                if(TextUtils.isEmpty(moneyStr)||moneyStr.equals("0")){
                    getActivity().finish();
                    return;
                }
                double money = Double.parseDouble(moneyStr);
                accountBean.setMoney(money);
                saveAccountToDB();
                //返回上级界面
                getActivity().finish();
            }
        });
    }
    //抽象类，子类必须要重写吗，保存数据
    public abstract void saveAccountToDB();

    public BaseRecordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean = new AccountBean();
        accountBean.setTypename("其他");
        accountBean.setImageid(R.mipmap.other);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_baserecord, container, false);
        initView(root);
        //添加给GridView填充数据的方法
        loadDataToGV();
        setGVLister();
        return root;
    }

    //获取当前时间，显示在timeTv上
    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = simpleDateFormat.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMoney(month);
        accountBean.setDay(day);
    }

    //设置GridView每一项的点击事件
    private void setGVLister() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectPos(position);
                adapter.notifyDataSetChanged();      //提示绘制发生变化
                TypeBean typeBean = typeBeanList.get(position);
                String typename = typeBean.getTypename();
                typeTv.setText(typename);
                accountBean.setTypename(typename);
                int imageId = typeBean.getImageid();
                typeIv.setImageResource(imageId);
                accountBean.setImageid(imageId);
            }
        });
    }

    //给GridView填充数据
    public void loadDataToGV() {
        typeBeanList = new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(),typeBeanList);
        typeGv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击备注
            case R.id.frag_record_tv_description:
                showDescriptionDialog();
                break;
            //点击时间
            case R.id.frag_record_tv_time:
                showTimeDialog();
                break;
        }
    }
    //弹出显示时间的对话框
    private void showTimeDialog() {
        SelectTimeDialog selectTimeDialog = new SelectTimeDialog(getContext());
        selectTimeDialog.show();
        //设定确定按钮被点击监听器
        selectTimeDialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);
                accountBean.setTime(time);
                accountBean.setYear(year);
                accountBean.setMonth(month);
                accountBean.setDay(day);
            }
        });
    }

    //弹出备注的对话框
    public void showDescriptionDialog(){
        DescriptionDialog descriptionDialog = new DescriptionDialog(getContext());
        descriptionDialog.show();
        descriptionDialog.setDialogSize();
        descriptionDialog.setOnEnsureListener(new DescriptionDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String description = descriptionDialog.getEditText();  //获取输入的数据
                if(!TextUtils.isEmpty(description)){
                    descriptionTv.setText(description);
                    accountBean.setDescription(description);
                }
                descriptionDialog.cancel();
            }
        });
    }
}