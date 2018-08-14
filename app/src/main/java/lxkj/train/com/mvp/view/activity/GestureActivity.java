package lxkj.train.com.mvp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import lxkj.train.com.R;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.view.GestureLockView;
import lxkj.train.com.view.GestureLockViewGroup;

public class GestureActivity extends BaseActivity {


    private GestureLockViewGroup gestureLockView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gesture;
    }

    @Override
    protected void initData() {
        mTitle_bar.setCentreText("手势密码");
        gestureLockView = findViewById(R.id.gestureLockViewGroup);
        gestureLockView.setAnswer(new int[] { 1, 2, 3, 4,5 });
        //gestureLockView.setUnMatchExceedBoundary(5);  //最大错误次数
        gestureLockView.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {
                    @Override
                    public void onUnmatchedExceedBoundary() {

                        gestureLockView.setUnMatchExceedBoundary(5);
                    }
                    @Override
                    public void onGestureEvent(boolean matched) {
                        if (matched) {
                            Toast.makeText(app, "密码正确", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(app, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onBlockSelected(int cId)
                    {
                    }
                });

    }
}
