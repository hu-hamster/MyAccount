package ltd.hujing.myaccount.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

}
