package ltd.hujing.myaccount.db;

public class ChartItemBean {
    int imageId;
    String type;
    double ratio;   //所占比例
    double totalMoney;  //此项的总钱数

    public ChartItemBean() {
    }

    public void setsImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getsImageId() {
        return imageId;
    }

    public String getType() {
        return type;
    }

    public double getRatio() {
        return ratio;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public ChartItemBean(int imageId, String type, double ratio, double totalMoney) {
        this.imageId = imageId;
        this.type = type;
        this.ratio = ratio;
        this.totalMoney = totalMoney;
    }
}
