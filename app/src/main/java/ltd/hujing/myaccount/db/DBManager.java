package ltd.hujing.myaccount.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
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

}
