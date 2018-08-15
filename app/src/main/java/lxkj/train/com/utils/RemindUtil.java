package lxkj.train.com.utils;

import lxkj.train.com.mvp.model.model_interface.DataModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by dell on 2018/8/14.
 */

public class RemindUtil implements Observable.OnSubscribe<String>, Observer<String> {
    private DataModel dataModel;
    public  RemindUtil(DataModel dataModel){
        this.dataModel = dataModel;
        new RxJavaUtil<>(this,this);
    }

    @Override
    public void call(Subscriber<? super String> subscriber) {
        //司机叫班查询
        while (true){
            dataModel.getData(RequestUtil.requestByteData((byte) 3, (short) 2, ProtoUtil.getMReqQuery(2,
                    (int) (System.currentTimeMillis() / 1000), ProtoUtil.getMReqCurDriverAttnd(13769185))));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(String s) {
    }
}
