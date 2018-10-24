package com.eric.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.eric.db.sqlite.SqlInfo;
import com.eric.db.sqlite.WhereBuilder;
import com.eric.db.table.DbModel;
import com.eric.db.table.TableEntity;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 数据库访问接口
 */
public interface DbManager extends Closeable {

    DaoConfig getDaoConfig();

    SQLiteDatabase getDatabase();

    /**
     * 保存实体类或实体类的List到数据库,
     * 如果该类型的id是自动生成的, 则保存完后会给id赋值.
     *
     * @param entity
     * @return
     * @throws DbException
     */
    boolean saveBindingId(Object entity) throws DbException;

    /**
     * 保存或更新实体类或实体类的List到数据库, 根据id对应的数据是否存在.
     *
     * @param entity
     * @throws DbException
     */
    void saveOrUpdate(Object entity) throws DbException;

    /**
     * 保存实体类或实体类的List到数据库
     *
     * @param entity
     * @throws DbException
     */
    void save(Object entity) throws DbException;

    /**
     * 保存或更新实体类或实体类的List到数据库, 根据id和其他唯一索引判断数据是否存在.
     *
     * @param entity
     * @throws DbException
     */
    void replace(Object entity) throws DbException;

    /**
     * delete操作
     *
     * @param entityType 数据库表对应实体的字节码类型
     * @param idValue    ID主键
     * @throws DbException
     */
    void deleteById(Class<?> entityType, Object idValue) throws DbException;

    /**
     * delete操作
     *
     * @param entity 数据库表对应实体
     * @throws DbException
     */
    void delete(Object entity) throws DbException;

    /**
     * delete操作
     *
     * @param entityType 数据库表对应实体的字节码类型
     * @throws DbException
     */
    void delete(Class<?> entityType) throws DbException;

    /**
     * delete操作
     *
     * @param entityType   数据库表对应实体的字节码类型
     * @param whereBuilder where条件语句
     * @throws DbException
     */
    int delete(Class<?> entityType, WhereBuilder whereBuilder) throws DbException;

    /**
     * update
     *
     * @param entity            数据库表对应实体
     * @param updateColumnNames 更新列名
     * @throws DbException
     */
    void update(Object entity, String... updateColumnNames) throws DbException;

    /**
     * update
     *
     * @param entityType     数据库表对应实体的字节码类型
     * @param whereBuilder   where语句条件
     * @param nameValuePairs 列名
     * @throws DbException
     */
    int update(Class<?> entityType, WhereBuilder whereBuilder, KeyValue... nameValuePairs) throws DbException;

    /**
     * 根据ID主键查询
     *
     * @param entityType 数据库表对应实体的字节码类型
     * @param idValue    ID主键
     * @throws DbException
     */
    <T> T findById(Class<T> entityType, Object idValue) throws DbException;

    /**
     * 查询表第一条记录
     *
     * @param entityType 数据库表对应实体的字节码类型
     * @throws DbException
     */
    <T> T findFirst(Class<T> entityType) throws DbException;

    /**
     * 查询表所有记录
     *
     * @param entityType 数据库表对应实体的字节码类型
     * @param <T>
     * @return
     * @throws DbException
     */
    <T> List<T> findAll(Class<T> entityType) throws DbException;

    /**
     * 获取链式Selector对象
     *
     * @param entityType 数据库表对应实体的字节码类型
     * @param <T>
     * @return
     * @throws DbException
     */
    <T> Selector<T> selector(Class<T> entityType) throws DbException;

    /**
     * 根据sql语句查询表对应记录
     *
     * @param sqlInfo 数据库sql语句实体
     * @return
     * @throws DbException
     */
    DbModel findDbModelFirst(SqlInfo sqlInfo) throws DbException;

    /**
     * 根据sql语句查询表所有记录
     *
     * @param sqlInfo 数据库sql语句实体
     * @return
     * @throws DbException
     */
    List<DbModel> findDbModelAll(SqlInfo sqlInfo) throws DbException;

    ///////////// table

    /**
     * 获取表信息
     *
     * @param entityType
     * @param <T>
     * @return
     * @throws DbException
     */
    <T> TableEntity<T> getTable(Class<T> entityType) throws DbException;

    /**
     * 删除表
     *
     * @param entityType
     * @throws DbException
     */
    void dropTable(Class<?> entityType) throws DbException;

    /**
     * 添加一列,
     * 新的entityType中必须定义了这个列的属性.
     *
     * @param entityType
     * @param column
     * @throws DbException
     */
    void addColumn(Class<?> entityType, String column) throws DbException;

    ///////////// db

    /**
     * 删除库
     *
     * @throws DbException
     */
    void dropDb() throws DbException;

    /**
     * 关闭数据库,
     * xUtils对同一个库的链接是单实例的, 一般不需要关闭它.
     *
     * @throws IOException
     */
    void close() throws IOException;

    /**
     * 根据sql语句执行更新或者删除
     *
     * @param sqlInfo sql语句
     */
    int executeUpdateDelete(SqlInfo sqlInfo) throws DbException;

    /**
     * 根据sql语句执行更新或者删除
     *
     * @param sql sql语句
     */
    int executeUpdateDelete(String sql) throws DbException;

    /**
     * 执行非查询语句
     *
     * @param sqlInfo sql语句
     * @throws DbException
     */
    void execNonQuery(SqlInfo sqlInfo) throws DbException;

    /**
     * 执行非查询语句
     *
     * @param sql sql语句
     * @throws DbException
     */
    void execNonQuery(String sql) throws DbException;

    /**
     * 执行查询语句
     *
     * @param sqlInfo sql语句
     * @throws DbException
     */
    Cursor execQuery(SqlInfo sqlInfo) throws DbException;

    /**
     * 执行查询语句
     *
     * @param sql sql语句
     * @throws DbException
     */
    Cursor execQuery(String sql) throws DbException;

    /**
     * 数据库打开接口
     */
    public interface DbOpenListener {
        /**
         * 数据库打开回调方法
         *
         * @param db 数据库管理者对象
         */
        void onDbOpened(DbManager db);
    }

    /**
     * 数据库更新接口
     */
    public interface DbUpgradeListener {
        /**
         * 数据库更新回调接口
         *
         * @param db         数据库管理者对象
         * @param oldVersion 数据库老版本号
         * @param newVersion 数据库新版本号
         */
        void onUpgrade(DbManager db, int oldVersion, int newVersion);
    }

    /**
     * 数据库表创建接口
     */
    public interface TableCreateListener {
        /**
         * 数据库表创建回调方法
         *
         * @param db    数据库管理者对象
         * @param table 数据库表对应实体
         */
        void onTableCreated(DbManager db, TableEntity<?> table);
    }

    /**
     * 数据库配置类
     */
    public static class DaoConfig {
        private File dbDir;
        private String dbName = "ljDao.db"; // default db name
        private int dbVersion = 1;
        private boolean allowTransaction = true;
        private DbUpgradeListener dbUpgradeListener;
        private TableCreateListener tableCreateListener;
        private DbOpenListener dbOpenListener;

        public DaoConfig() {
        }

        /**
         * 设置数据库存储路径
         *
         * @param dbDir 数据库存储路径
         * @return
         */
        public DaoConfig setDbDir(File dbDir) {
            this.dbDir = dbDir;
            return this;
        }

        /**
         * 设置数据库名称
         *
         * @param dbName 数据库名称
         * @return
         */
        public DaoConfig setDbName(String dbName) {
            if (!TextUtils.isEmpty(dbName)) {
                this.dbName = dbName;
            }
            return this;
        }

        /**
         * 设置数据库版本号
         *
         * @param dbVersion 数据库版本号
         * @return
         */
        public DaoConfig setDbVersion(int dbVersion) {
            this.dbVersion = dbVersion;
            return this;
        }

        /**
         * 设置数据库事务是否开启
         *
         * @param allowTransaction 是否开启事务
         * @return
         */
        public DaoConfig setAllowTransaction(boolean allowTransaction) {
            this.allowTransaction = allowTransaction;
            return this;
        }

        /**
         * 设置数据库打开监听
         *
         * @param dbOpenListener 数据库打开监听
         * @return
         */
        public DaoConfig setDbOpenListener(DbOpenListener dbOpenListener) {
            this.dbOpenListener = dbOpenListener;
            return this;
        }

        /**
         * 设置数据库更新监听
         *
         * @param dbUpgradeListener 数据库更新监听
         * @return
         */
        public DaoConfig setDbUpgradeListener(DbUpgradeListener dbUpgradeListener) {
            this.dbUpgradeListener = dbUpgradeListener;
            return this;
        }

        /**
         * 设置数据库表创建监听
         *
         * @param tableCreateListener 数据库表创建监听
         * @return
         */
        public DaoConfig setTableCreateListener(TableCreateListener tableCreateListener) {
            this.tableCreateListener = tableCreateListener;
            return this;
        }

        public File getDbDir() {
            return dbDir;
        }

        public String getDbName() {
            return dbName;
        }

        public int getDbVersion() {
            return dbVersion;
        }

        public boolean isAllowTransaction() {
            return allowTransaction;
        }

        public DbOpenListener getDbOpenListener() {
            return dbOpenListener;
        }

        public DbUpgradeListener getDbUpgradeListener() {
            return dbUpgradeListener;
        }

        public TableCreateListener getTableCreateListener() {
            return tableCreateListener;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DaoConfig daoConfig = (DaoConfig) o;

            if (!dbName.equals(daoConfig.dbName)) return false;
            return dbDir == null ? daoConfig.dbDir == null : dbDir.equals(daoConfig.dbDir);
        }

        @Override
        public int hashCode() {
            int result = dbName.hashCode();
            result = 31 * result + (dbDir != null ? dbDir.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return String.valueOf(dbDir) + "/" + dbName;
        }
    }
}
