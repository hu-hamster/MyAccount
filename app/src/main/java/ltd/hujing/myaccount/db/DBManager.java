package ltd.hujing.myaccount.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ltd.hujing.myaccount.utils.DoubleUtils;

/*
*负责管理数据库对表中的数据进行增删改查
**/
public class DBManager {
    private  static SQLiteDatabase db;

    public static void initDB(Context context){
        DBOpen dbOpen = new DBOpen(context);
        db = dbOpen.getWritableDatabase();    //获取数据库对象
    }

    /*
    * 读取数据库中的数据，写入内存中
     */
    public static List<TypeBean> getTypeList(int kind){
        List<TypeBean> list = new ArrayList<>();
        //读取typedb表中的数据
        String sql = "select * from typetb where kind = "+kind;
        Cursor cursor = db.rawQuery(sql, null);
        //循环读取图标内容，存储到对象当中
        while(cursor.moveToNext()){
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            TypeBean typeBean = new TypeBean(id, typename, imageId, sImageId, kind);
            list.add(typeBean);
        }
        return list;
    }

    /*
    * 向记账表中插入一条元素
     */
    public static void insertItemAccounttb(AccountBean accountBean){
        ContentValues contentValues = new ContentValues();
        contentValues.put("typename",accountBean.getTypename());
        contentValues.put("imageid",accountBean.getImageid());
        contentValues.put("description",accountBean.getDescription());
        contentValues.put("money",accountBean.getMoney());
        contentValues.put("time",accountBean.getTime());
        contentValues.put("year",accountBean.getYear());
        contentValues.put("month",accountBean.getMonth());
        contentValues.put("day",accountBean.getDay());
        contentValues.put("kind",accountBean.getKind());
        db.insert("accounttb",null,contentValues);
    }

    /*
    * 向记账表中删除一条元素
     */
    public static int deleteItemFromAccounttbById(int id){
        int i = db.delete("accounttb", "id=?", new String[]{id + ""});
        return i;
    }

    /*
    * 获取数据库中的全部支出收入情况
     */
    public static List<AccountBean>getAccountListFromAccounttb(){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb order by id desc";   //逆序查找
        Cursor cursor = db.rawQuery(sql,null);
        //便利数据库每一行
        while(cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") int imageid = cursor.getInt(cursor.getColumnIndex("imageid"));
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") double money = cursor.getDouble(cursor.getColumnIndex("money"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") int month = cursor.getInt(cursor.getColumnIndex("month"));
            @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex("day"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            AccountBean accountBean = new AccountBean(id, typename, imageid, description, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }

    /*
    * 获取记账表中某一个月的所有支出或者收入情况
     */
    public static List<AccountBean>getAccountListOneMonthFromAccounttb(int year, int month){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? order by id desc";   //逆序查找
        Cursor cursor = db.rawQuery(sql,new String[]{year+"",month+""});
        //便利数据库每一行
        while(cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") int imageid = cursor.getInt(cursor.getColumnIndex("imageid"));
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") double money = cursor.getDouble(cursor.getColumnIndex("money"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex("day"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            AccountBean accountBean = new AccountBean(id, typename, imageid, description, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }

    /*
    * 获取某一天的支出或者收入的总金额   kind： 支出-0  收入-1
     */
    public static double getSumMoneyOneDay(int year,int month,int day,int kind) {
        double total = 0.0;
        String sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=?";
        Cursor cursor =  db.rawQuery(sql,new String[]{year+"",month+"",day+"",kind+""});
        //遍历
        if(cursor.moveToFirst()){
            @SuppressLint("Range") double money = cursor.getDouble(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }

    /*
     * 获取某一个月的支出或者收入的总金额   kind： 支出-0  收入-1
     */
    public static double getSumMoneyOneMonth(int year,int month,int kind) {
        double total = 0.0;
        String sql = "select sum(money) from accounttb where year=? and month=?  and kind=?";
        Cursor cursor =  db.rawQuery(sql,new String[]{year+"",month+"",kind+""});
        //遍历
        if(cursor.moveToFirst()){
            @SuppressLint("Range") double money = cursor.getDouble(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }
    /*
    * 统计某月份支出或者收入情况有多少条  支出-0 收入-1
     */
    public static int getCountItemOneMonth(int year,int month,int kind){
        int total = 0;
        String sql = "select count(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year+"",month+"",kind+""});
        if (cursor.moveToNext()) {
            @SuppressLint("Range") int count = cursor.getInt(cursor.getColumnIndex("count(money)"));
            total = count;
        }
        return total;
    }

    /*
     * 获取某一年的支出或者收入的总金额   kind： 支出-0  收入-1
     */
    public static double getSumMoneyOneYear(int year,int kind) {
        double total = 0.0;
        String sql = "select sum(money) from accounttb where year=? and kind=?";
        Cursor cursor =  db.rawQuery(sql,new String[]{year+"",kind+""});
        //遍历
        if(cursor.moveToFirst()){
            @SuppressLint("Range") double money = cursor.getDouble(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }

    /*
    * 获取数据库中全部条例的总金额
     */
    public static double getSumMoneyAll(){
        double total = 0.0;
        String sql = "select sum(money) from accounttb";
        Cursor cursor = db.rawQuery(sql,null);
        //遍历
        if(cursor.moveToFirst()){
            @SuppressLint("Range") double money = cursor.getDouble(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }

    /*
    * 查询记账的表当中有几个年份信息
     */
    public static List<Integer> getYearListFromAccounttb(){
        List<Integer> list = new ArrayList<>();
        String sql = "select distinct(year) from accounttb order by year asc";
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            list.add(year);
        }
        return list;
    }

    /*
    * 删除accounttb表格当中的所有数据
     */
    public static void deleteAllAccount(){
        String sql = "delete from accounttb";
        db.execSQL(sql);
    }

    /*
    * 查询指定年份和月份收入或者支出每一种类型的总钱数
     */
    public static List<ChartItemBean> getChartListFromAccounttb(int year, int month, int kind){
        List<ChartItemBean> list = new ArrayList<>();
        double sumMoneyOneMonth = getSumMoneyOneMonth(year,month,kind);    //求出支出/收入总钱数
        String sql = "select typename,imageid,sum(money)as total from accounttb where year=? and month=? and kind=? group by typename order by total desc";
        Cursor cursor = db.rawQuery(sql,new String[]{year+"",month+"",kind+""});
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("imageid"));
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") double total = cursor.getDouble(cursor.getColumnIndex("total"));
            //计算所占百分比  total /sumMonth
            double ratio = DoubleUtils.div(total,sumMoneyOneMonth);
            ChartItemBean bean = new ChartItemBean(imageId, typename, ratio, total);
            list.add(bean);
        }
        return list;
    }

    /** 根据指定月份每一日收入或者支出的总钱数的集合*/
    public static List<BarChartItemBean>getSumMoneyOneDayInMonth(int year,int month,int kind){
        String sql = "select day,sum(money) from accounttb where year=? and month=? and kind=? group by day";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        List<BarChartItemBean>list = new ArrayList<>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex("day"));
            @SuppressLint("Range") float smoney = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            BarChartItemBean itemBean = new BarChartItemBean(year, month, day, smoney);
            list.add(itemBean);
        }
        return list;
    }

    /**
     * 获取这个月当中某一天收入支出最大的金额，金额是多少
     * */
    public static float getMaxMoneyOneDayInMonth(int year,int month,int kind){
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=? group by day order by sum(money) desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            return money;
        }
        return 0;
    }



}
