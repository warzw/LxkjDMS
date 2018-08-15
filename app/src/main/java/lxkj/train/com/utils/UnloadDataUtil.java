package lxkj.train.com.utils;

import io.realm.Realm;
import lxkj.train.com.entity.realm.AnnouncementRealm;
import lxkj.train.com.entity.realm.UnloadRealm;
import lxkj.train.com.interfaces.DataBaseRealmInterface;

/**
 * Created by dell on 2018/8/15.
 */

public class UnloadDataUtil implements DataBaseRealmInterface {
    String datas[];
    public UnloadDataUtil(String ... datas){
        this.datas = datas;
        DataBaseUtil dataBaseUtil = DataBaseUtil.getInstanc();
        dataBaseUtil.addData(this);  //从新添加数据库
    }
    @Override
    public void getRealm(Realm realm) {
        UnloadRealm unloadRealm = new UnloadRealm();
        unloadRealm.setNumber(datas[0]);
        unloadRealm.setFileName(datas[1]);
        unloadRealm.setCreatTime(datas[2]);
        unloadRealm.setFileZise(datas[3]);
        unloadRealm.setStaus("未转储");
        realm.copyToRealm(unloadRealm);
    }
}
