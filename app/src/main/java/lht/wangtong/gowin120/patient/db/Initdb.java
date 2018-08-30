package lht.wangtong.gowin120.patient.db;


import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

/**
 * Created by sanmu on 2016/10/11 0011.
 */
public class Initdb {
    public static DbManager db;

    public Initdb(String dbName) {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName(dbName).
                setDbVersion(1).
                setAllowTransaction(true).  //设置是否开启事务,默认为false关闭事务
                setTableCreateListener(new DbManager.TableCreateListener() {
            @Override
            public void onTableCreated(DbManager db, TableEntity<?> table) {

            }

        }). //设置数据库创建时的Listener
                setDbOpenListener(new DbManager.DbOpenListener() {
            @Override
            public void onDbOpened(DbManager db) {
                // 开启WAL, 对写入加速提升巨大
                db.getDatabase().enableWriteAheadLogging();
            }
        }).
                setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        // db.addColumn(...);
                        // db.dropTable(...);
                    }
                });//设置数据库升级时的Listener,这里可以执行相关数据库表的相关修改,比如alter语句增加字段等
        //.setDbDir(null);//设置数据库.db文件存放的目录,默认为包名下databases目录下
        db = x.getDb(daoConfig);
    }

}
