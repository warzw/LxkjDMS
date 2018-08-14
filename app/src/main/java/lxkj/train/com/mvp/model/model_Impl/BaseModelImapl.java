package lxkj.train.com.mvp.model.model_Impl;

import android.text.TextUtils;
import android.widget.Toast;


import com.example.tutorial.MReqFileDownProto;
import com.google.protobuf.ByteString;

import java.util.Map;

import lxkj.train.com.interfaces.HttpResponse;
import lxkj.train.com.mvp.model.model_interface.DataModel;
import lxkj.train.com.mvp.presenter.presenter_impl.BasePresenter;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.overall.TextUtil;
import lxkj.train.com.utils.LogUtils;
import lxkj.train.com.utils.ProtoUtil;
import lxkj.train.com.utils.http.HttpUtils;

/**
 * Created by dhc on 2017/6/28.
 */

public class BaseModelImapl implements DataModel,HttpResponse {
    private BaseActivity activity;
    private BasePresenter businessViewModel;
    public BaseModelImapl(BaseActivity activity, BasePresenter businessViewModel){
        this.activity=activity;
        this.businessViewModel=businessViewModel;
    }
    @Override
    public <T>  void getData(Object data) {
        HttpUtils.getIncetanc().request(activity,data,this);
    }
    @Override
    public void response(byte[] txnCode, Object dataEntity) {
        if (txnCode.length>0) {
            if (OverallData.exceptionSwitch) {
                try {
                    byte[] data1=new byte[txnCode.length-12];
                    for (int i = 12; i < txnCode.length; i++) {
                        data1[i-12] = txnCode[i];
                    }
                    MReqFileDownProto.SucceedFileDown succeedFileDown =  ProtoUtil.getSucceedFileDown(data1);
                    if (succeedFileDown.getComm() != null && succeedFileDown.getComm().getCode() == 0) { //成功
                        LogUtils.i("succeedData", txnCode[5]+"---"+succeedFileDown.getSubtype() + "---"+txnCode.length);
                        businessViewModel.succeed(txnCode[5],succeedFileDown.getSubtype(),succeedFileDown.getContent());
                    }else {
                        LogUtils.i("succeedData", succeedFileDown.getComm().getCode()+"----" + txnCode.length);
                    }

                } catch (Exception e) {
                    LogUtils.i("NullPointerException",e.toString());
                }
            }else {

                byte[] data1=new byte[txnCode.length-12];
                for (int i = 12; i < txnCode.length; i++) {
                    data1[i-12] = txnCode[i];
                }
                MReqFileDownProto.SucceedFileDown succeedFileDown =  ProtoUtil.getSucceedFileDown(data1);

                if (succeedFileDown.getComm() != null && succeedFileDown.getComm().getCode() == 0) { //成功
                    LogUtils.i("succeedData", txnCode[5]+"---"+succeedFileDown.getSubtype() + "---"+ txnCode.length);
                    businessViewModel.succeed(txnCode[5],succeedFileDown.getSubtype(),succeedFileDown.getContent());
                }else {
                    //Toast.makeText(activity, "没有获取到信息", Toast.LENGTH_SHORT).show();
                    LogUtils.i("succeedData", succeedFileDown.getComm().getCode()+"----" + txnCode.length+"---"+txnCode[5]+","+succeedFileDown.getSubtype());
                }
            }
        }else {
            LogUtils.i("succeedData", "没有返回数据");
            //Toast.makeText(activity, "没有返回数据", Toast.LENGTH_SHORT).show();
        }
    }
}
