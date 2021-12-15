package ltd.hujing.myaccount.ui.classification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.adapter.TypeBaseAdapter;
import ltd.hujing.myaccount.db.TypeBean;


public class BaseClassificationFragment extends Fragment {
    private GridView typeGv;
    private MyTypeBaseAdapter adapter;
    private List<TypeBean> typeBeanList;
    public BaseClassificationFragment() {
        // Required empty public constructor
    }


    public static BaseClassificationFragment newInstance() {
        BaseClassificationFragment fragment = new BaseClassificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_base_classification, container, false);
        initView(root);
        loadDataToGV();
        setGVLister();   //设置表格监听
        return root;
    }

    //设置GridView每一项的点击事件
    private void setGVLister() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.notifyDataSetChanged();      //提示绘制发生变化
                TypeBean typeBean = typeBeanList.get(position);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(BaseClassificationFragment.this.getActivity(),DetailsClassificationActivity.class);
                intent.putExtra("imageid",typeBean.getImageid());
                intent.putExtra("typename",typeBean.getTypename());
                intent.putExtra("kind",typeBean.getKind());
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        typeGv = view.findViewById(R.id.frag_classification_gv);
    }

    //给GridView填充数据
    public void loadDataToGV() {
        typeBeanList = new ArrayList<>();
        adapter = new MyTypeBaseAdapter(getContext(),typeBeanList);
        typeGv.setAdapter(adapter);
    }

    private class MyTypeBaseAdapter extends BaseAdapter{
        Context context;
        List<TypeBean> mDatas;
        public MyTypeBaseAdapter(Context context, List<TypeBean> mDatas) {
            this.context = context;
            this.mDatas = mDatas;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv,parent,false);
            //查找布局中的控件
            ImageView imageView = convertView.findViewById(R.id.item_recordfrag_iv);
            TextView textView = convertView.findViewById(R.id.item_recordfrag_tv);
            //获取指定位置的数据源
            TypeBean typeBean = mDatas.get(position);
            textView.setText(typeBean.getTypename());
            imageView.setImageResource(typeBean.getImageid());
            return convertView;
        }
    }

    public GridView getTypeGv() {
        return typeGv;
    }

    public void setTypeGv(GridView typeGv) {
        this.typeGv = typeGv;
    }

    public MyTypeBaseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MyTypeBaseAdapter adapter) {
        this.adapter = adapter;
    }

    public List<TypeBean> getTypeBeanList() {
        return typeBeanList;
    }

    public void setTypeBeanList(List<TypeBean> typeBeanList) {
        this.typeBeanList = typeBeanList;
    }


}