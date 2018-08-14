package lxkj.train.com.utils;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dhc on 2017/9/26.
 */

public class RxJavaUtil<T>{
    private Observable<T> observable;
    public RxJavaUtil(Observable.OnSubscribe<T> onSubscribe, Observer<T> observer){
        observable=Observable.create(onSubscribe);
        if (onSubscribe != null&&observer!=null) {
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
        }
    }
    public void unsubscribeOn(){
        if (observable != null) {
            observable.unsubscribeOn(Schedulers.io());
        }
    }
}
