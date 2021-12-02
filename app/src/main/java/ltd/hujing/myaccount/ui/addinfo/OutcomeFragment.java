package ltd.hujing.myaccount.ui.addinfo;

import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.db.AccountBean;
import ltd.hujing.myaccount.db.DBManager;
import ltd.hujing.myaccount.db.TypeBean;

public class OutcomeFragment extends BaseRecordFragment{
    //重载，插入数据
    @Override
    public void saveAccountToDB() {
        AccountBean accountBean = getAccountBean();
        accountBean.setKind(0);
        DBManager.insertItemAccounttb(accountBean);
    }

    //重写
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        List<TypeBean> outlist = DBManager.getTypeList(0);
        getTypeBeanList().addAll(outlist);
        getAdapter().notifyDataSetChanged();
        getTypeTv().setText("其他");
        getTypeIv().setImageResource(R.mipmap.other);
    }


}
