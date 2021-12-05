package ltd.hujing.myaccount.utils;

import java.math.BigDecimal;

public class DoubleUtils {
    /*
     * 进行除法运算，保留四位小数
     */
    public static double div(double v1,double v2){
        double v3 = v1/v2;
        BigDecimal b1 = new BigDecimal(v3);
        double val = b1.setScale(4,4).doubleValue();
        return val;
    }
    /*
    * 将浮点数类型，转换成百分比显示形式
     */
    public static String ratioToPercent(double val){
        double v = val*100;
        BigDecimal b1 = new BigDecimal(v);
        double v1 = b1.setScale(2, 4).floatValue();
        String per = v1+"%";
        return per;
    }
}
