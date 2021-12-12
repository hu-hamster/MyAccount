package ltd.hujing.myaccount.addinfo;

import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.db.AccountBean;
import ltd.hujing.myaccount.db.DBManager;
import ltd.hujing.myaccount.db.TypeBean;

public class OutcomeFragment extends BaseRecordFragment{
    public OutcomeFragment(int flag, AccountBean accountBean) {
        this.flag = flag;
        this.accountBean = accountBean;
    }

    //重载，插入数据
    @Override
    public void saveAccountToDB() {
        if(flag==1){
            AccountBean accountBean = getAccountBean();
            accountBean.setKind(0);
            accountBean.setMoney(-1*Math.abs(accountBean.getMoney()));
            DBManager.updateItemFromAccounttbByChange(accountBean);
        }else{
            AccountBean accountBean = getAccountBean();
            accountBean.setKind(0);
            accountBean.setMoney(-1*accountBean.getMoney());
            DBManager.insertItemAccounttb(accountBean);
        }
    }

    //重写
    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        //获取数据库当中的数据源
        List<TypeBean> outlist = DBManager.getTypeList(0);
        getTypeBeanList().addAll(outlist);
        getAdapter().notifyDataSetChanged();
        if(flag==1){
            getTypeTv().setText(accountBean.getTypename());
            getTypeIv().setImageResource(accountBean.getImageid());
        }else{
            getTypeTv().setText("其他");
            getTypeIv().setImageResource(R.mipmap.other);
        }

    }


}
