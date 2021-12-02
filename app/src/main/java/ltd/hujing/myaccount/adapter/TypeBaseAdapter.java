package ltd.hujing.myaccount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.db.TypeBean;

public class TypeBaseAdapter extends BaseAdapter {
    Context context;
    List<TypeBean> mDatas;
    private int selectPos = 0;  //选中位置

    public TypeBaseAdapter(Context context, List<TypeBean> mDatas) {
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

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
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
        //判断当前位置是否为选中位置，设置不同图片

        if(selectPos == position){
            imageView.setImageResource(typeBean.getSimageid());
        }else{
            imageView.setImageResource(typeBean.getImageid());
        }
        return convertView;
    }
}
