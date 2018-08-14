package lxkj.train.com.view;


import lxkj.train.com.interfaces.PayPassWordInterface;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;

/**
 * Created by dhc on 2017/12/1.
 */

public class PayPasswordUtil  {
    private  PayFragment fragment;
    public PayPasswordUtil(BaseActivity context, PayPwdView.InputCallBack inputCallBack) {
        if (fragment == null) {
            fragment = new PayFragment();
            fragment.setPaySuccessCallBack(inputCallBack);
        }
        fragment.show(context.getSupportFragmentManager(), "Pay");
    }
}
