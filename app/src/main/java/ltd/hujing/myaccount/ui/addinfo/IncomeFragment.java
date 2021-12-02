package ltd.hujing.myaccount.ui.addinfo;

import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.db.AccountBean;
import ltd.hujing.myaccount.db.DBManager;
import ltd.hujing.myaccount.db.TypeBean;

public class IncomeFragment extends BaseRecordFragment {
    //重载
    @Override
    public void saveAccountToDB() {
        AccountBean accountBean = getAccountBean();
        accountBean.setKind(1);
        DBManager.insertItemAccounttb(accountBean);
    }

    //重写
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        List<TypeBean> inlist = DBManager.getTypeList(1);
        getTypeBeanList().addAll(inlist);
        getAdapter().notifyDataSetChanged();
        getTypeTv().setText("其他");
        getTypeIv().setImageResource(R.mipmap.other);
    }
}