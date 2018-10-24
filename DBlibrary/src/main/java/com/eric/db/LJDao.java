package com.eric.db;

import android.app.Application;

/**
 * Created by czl on 2016/12/4.
 * 数据库初始化入口
 */
public final class LJDao {
    /**
     * 数据库配置
     */
    private static DbManager.DaoConfig daoConfig;

    /**
     * app入口对象
     */
    private static Application application;

    /**
     * @param application app入口对象
     * @param isDebug     是否开启debug模式，debug模式自动打印执行的sql语句
     * @param daoConfig   数据库配置
     */
    public static void init(Application application, boolean isDebug, DbManager.DaoConfig daoConfig) {
        DbLoader.Ext.init(application);
        DbLoader.Ext.setDebug(isDebug);
        LJDao.application = application;
        LJDao.daoConfig = daoConfig;
    }

    private static class LazyHolder {
        private static final DbManager INSTANCE = DbLoader.getDb(daoConfig);
    }

    /**
     * 获得数据库操作句柄
     *
     * @return
     */
    public static DbManager getDbManager() {
        if (application == null) {
            throw new RuntimeException("please invoke LJDao.init() on Application#onCreate()"
                    + " and register your Application in manifest.");
        }
        return LazyHolder.INSTANCE;
    }
}
