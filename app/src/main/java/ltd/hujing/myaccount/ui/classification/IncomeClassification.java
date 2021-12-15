package ltd.hujing.myaccount.ui.classification;

import java.util.List;

import ltd.hujing.myaccount.db.DBManager;
import ltd.hujing.myaccount.db.TypeBean;

public class IncomeClassification extends BaseClassificationFragment {

    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        List<TypeBean> inlist = DBManager.getTypeList(1);
        getTypeBeanList().addAll(inlist);

    }

}
