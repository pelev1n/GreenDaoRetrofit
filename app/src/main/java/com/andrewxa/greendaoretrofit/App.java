package com.andrewxa.greendaoretrofit;

import android.app.Application;

import com.andrewxa.greendaoretrofit.bd.DaoMaster;
import com.andrewxa.greendaoretrofit.bd.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application {

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"greendao_demo.db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }


}
