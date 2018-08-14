package lxkj.train.com.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import java.util.List;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import lxkj.train.com.interfaces.DataBaseRealmInterface;
import lxkj.train.com.interfaces.RealmDeleteInterface;
import lxkj.train.com.overall.OverallData;


/**
 * Created by dhc on 2017/8/28.
 */

public class DataBaseUtil implements Realm.Transaction {
    private RealmConfiguration realmConfig;
    private Realm realm;
    private static DataBaseUtil dataBaseUtil;
    private DataBaseRealmInterface dataBaseRealmInterface;
    private Handler hd=new Handler();
    private DataBaseUtil() {

    }

    public static DataBaseUtil getInstanc() {
        if (dataBaseUtil == null) {
            dataBaseUtil = new DataBaseUtil();
        }
        return dataBaseUtil;
    }

    /**
     * 增加数据的方法
     *
     * @param
     * @param dataBaseRealmInterface
     */
    public void addData(DataBaseRealmInterface dataBaseRealmInterface) {
        initRealm();
        this.dataBaseRealmInterface = dataBaseRealmInterface;
        realm.executeTransaction(this);

    }

    /**
     * 删除某的字段对应的所有数据的方法
     * @param //传入的参数跟查询需要的参数一样
     * @param
     */
    public <T extends RealmObject> void  deleteData(Context context, final Class<T> t, final String key, final String value) {
        if (context == null||t==null||key==null||value==null) {
            return;
        }
        initRealm();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<T> deleteDatas = realm.where(t).equalTo(key, value).findAll();
                if (deleteDatas != null && deleteDatas.size() > 0)
                    for (int i = 0; i <deleteDatas.size() ; i++) {
                        deleteDatas.get(i).deleteFromRealm();
                    }
            }
        });

    }
    /**
     * 删除一条数据的方法
     * @param  //例子：   List<TradeInfoEntity> datas=DataBaseUtil.getInstanc().getQuerySomeData(this, TradeInfoEntity.class,"userId",CommonParameterEntity.getIncetanc().mBaseBean.getUserId());
                         DataBaseUtil.getInstanc().deleteDataItem(this, TradeInfoEntity.class,"id",datas.get(0).getId());
     * @param    //  先获取到当前item对应的 id，是 long类型，然后根据 id 来删除
     */
    public <T extends RealmObject> void  deleteDataItem(Context context, final Class<T> t, final String key, final long value) {
        if (context == null||t==null||key==null||value==0) {
            return;
        }
        initRealm();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(t).equalTo(key, value).findAll().deleteAllFromRealm();
            }
        });
    }
    /**
     * 删除所有数据
     */
    public <T extends RealmObject> void  deleteAllData(Context context, final Class<T> t, final RealmDeleteInterface realmDeleteInterface) {
        if (context == null||t==null) {
            return;
        }
        initRealm();
        RealmResults<T> datas= realm.where(t).findAll();
        realm.beginTransaction();//开启事务
        datas.deleteAllFromRealm();
        realm.commitTransaction();//提交事务
        if (realmDeleteInterface != null) {
            realmDeleteInterface.deleteFinish();
        }


    }
    /**
     * 查询数据的方法
     *
     * @param
     * @param t
     * @param <T>
     * @return
     */
    public <T extends RealmObject> List<T> getQueryData(Class<T> t) {
        initRealm();
        List<T> datas = realm.where(t).findAll();
        return datas;
    }

    /**
     * 根据某个字段查询,传进来的类和参数一定要对应
     *
     * @param context
     * @param t
     * @param <T>
     * @return
     */
    public <T extends RealmObject> List<T> getQuerySomeData(Context context, Class<T> t, String key, String value) {
        initRealm();
        List<T> users = realm.where(t).equalTo(key, value).findAll();
        return users;
    }

    private void initRealm() {
        if (realmConfig == null || realm == null || realm.isClosed()) {
            realmConfig = new RealmConfiguration
                    .Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(realmConfig);

        }
    }

    @Override
    public void execute(Realm realm) {  //事务中执行对应操作
        if (dataBaseRealmInterface != null) {
            dataBaseRealmInterface.getRealm(realm);
        }

    }

    public void onDestroy() {
        if (dataBaseRealmInterface != null) {
            dataBaseRealmInterface = null;
        }
        if (realm != null) {
            realm.close();
        }
    }
}
