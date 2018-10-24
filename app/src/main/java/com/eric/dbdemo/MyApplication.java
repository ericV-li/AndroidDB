package com.eric.dbdemo;

import android.app.Application;
import android.util.Log;

import com.eric.db.DbManager;
import com.eric.db.LJDao;

/**
 * @author shangfu.li
 * @Package com.eric.dbdemo
 * @Title: MyApplication
 * @Description: Copyright (c)
 * Create DateTime: 2017/05/01
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbVersion(2)
                .setDbName("ljTest.db")
                .setAllowTransaction(true)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                    }
                }).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        Log.d("tag", "oldVersion:" + oldVersion + "---" + "newVersion:" + newVersion);
                    }
                });
        LJDao.init(this, true, daoConfig);
    }
}
