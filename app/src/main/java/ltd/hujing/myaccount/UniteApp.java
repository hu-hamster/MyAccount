package ltd.hujing.myaccount;

import android.app.Application;

import ltd.hujing.myaccount.db.DBManager;

/*表示全局的应用的类*/
public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        DBManager.initDB(getApplicationContext());
    }
}
