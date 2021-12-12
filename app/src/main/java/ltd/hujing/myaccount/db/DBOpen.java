package ltd.hujing.myaccount.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import ltd.hujing.myaccount.R;

public class DBOpen extends SQLiteOpenHelper {

    public DBOpen(@Nullable Context context) {
        super(context,"MyAccount.db",null,1);
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表示类型的表
        String sql = "create table typetb (id integer primary key autoincrement,typename varchar(10)" +
                ",imageId integer,sImageId integer,kind integer)";
        db.execSQL(sql);
        insertType(db);
        //创建记账的表
        sql = "create table accounttb(id integer primary key autoincrement, typename varchar(10), " +
                "imageid integer, description varchar(100),money double,time varchar(60), year integer," +
                "month integer, day integer, kind integer)";
        db.execSQL(sql);
    }

    private void insertType(SQLiteDatabase db){
        //向typetb表中插入元素
        String sql = "insert into typetb (typename,imageId,sImageId,kind) values (?,?,?,?)";
        db.execSQL(sql,new Object[]{"其他", R.mipmap.other,R.mipmap.other_1,0});
        db.execSQL(sql,new Object[]{"吃喝饮食", R.mipmap.food,R.mipmap.food_1,0});
        db.execSQL(sql,new Object[]{"交通出行", R.mipmap.vehicle,R.mipmap.vehicle_1,0});
        db.execSQL(sql,new Object[]{"通讯费用", R.mipmap.telmoney,R.mipmap.telmoney_1,0});
        db.execSQL(sql,new Object[]{"欠债借款", R.mipmap.borrowing2,R.mipmap.borrowing2_1,0});
        db.execSQL(sql,new Object[]{"人情礼物", R.mipmap.gift,R.mipmap.gift_1,0});
        db.execSQL(sql,new Object[]{"房租水电", R.mipmap.home,R.mipmap.home_1,0});
        db.execSQL(sql,new Object[]{"医疗费用", R.mipmap.medical,R.mipmap.medical_1,0});
        db.execSQL(sql,new Object[]{"社交红包", R.mipmap.redpacket,R.mipmap.redpacket_1,0});
        db.execSQL(sql,new Object[]{"逛街购物", R.mipmap.shopping,R.mipmap.shopping_1,0});
        db.execSQL(sql,new Object[]{"运动健身", R.mipmap.sports,R.mipmap.sports_1,0});
        db.execSQL(sql,new Object[]{"日常用品", R.mipmap.daily,R.mipmap.daily_1,0});
        db.execSQL(sql,new Object[]{"聚会聚餐", R.mipmap.gathering,R.mipmap.gathering_1,0});
        db.execSQL(sql,new Object[]{"基金理财", R.mipmap.fund,R.mipmap.fund_1,0});
        db.execSQL(sql,new Object[]{"旅行旅游", R.mipmap.travel,R.mipmap.travel_1,0});

        db.execSQL(sql,new Object[]{"其他", R.mipmap.other,R.mipmap.other_1,1});
        db.execSQL(sql,new Object[]{"工资薪水", R.mipmap.salary,R.mipmap.salary_1,1});
        db.execSQL(sql,new Object[]{"奖金", R.mipmap.redpacket,R.mipmap.redpacket_1,1});
        db.execSQL(sql,new Object[]{"转账借入", R.mipmap.transfer,R.mipmap.transfer_1,1});
        db.execSQL(sql,new Object[]{"基金理财", R.mipmap.fund,R.mipmap.fund_1,1});

    }
    //数据库版本发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
