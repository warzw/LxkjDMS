package lxkj.train.com.mvp.presenter.presenter_impl;

import android.databinding.ViewDataBinding;

import com.google.protobuf.ByteString;

import java.util.List;

import io.realm.Realm;
import lxkj.train.com.databinding.ActivityBasicInfoBinding;
import lxkj.train.com.entity.realm.UserInfoRealm;
import lxkj.train.com.interfaces.DataBaseRealmInterface;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

/**
 * Created by dell on 2018/6/14.
 */

public class BasicInfoPresenter extends BasePresenter implements DataBaseRealmInterface {
    private ActivityBasicInfoBinding binding;
    public BasicInfoPresenter(BaseActivity activity, ActivityBasicInfoBinding binding) {
        super(activity, binding);
        this.binding = binding;
        requestData();
    }

    @Override
    public void requestData() {
        dataBaseUtil.addData(this); //操作realm数据库
    }

    @Override
    public void succeed(byte servicetype,int subtype,ByteString bytes) {

    }

    @Override
    public void getRealm(Realm realm) {
        UserInfoRealm userInfoRealm = realm.createObject(UserInfoRealm.class);
        userInfoRealm.setName("杜飞龙");
        userInfoRealm.setNumber("18810497522");
        List<UserInfoRealm> userInfoRealms = dataBaseUtil.getQueryData( UserInfoRealm.class);
        binding.mapLinerView1.setRightText(userInfoRealms.get(0).getName());
    }
}
