package lxkj.train.com.view;


import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import lxkj.train.com.R;


public class LoadingDialog {
	private View loadingRing;
	private Dialog dialog;
	private Activity ownerActivity;
	public LoadingDialog(Activity ownerActivity) {
		this.ownerActivity=ownerActivity;
		View view = LayoutInflater.from(ownerActivity).inflate(R.layout.dialog_loading,null);
		loadingRing = view.findViewById(R.id.iv_loading_ring);
		dialog = new Dialog(ownerActivity, R.style.RemindDialog);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.getWindow().setContentView(view);
	}
	public void show() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (ownerActivity.isFinishing()||ownerActivity.isDestroyed()) {
                return;
            }
		}else {
			if (ownerActivity.isFinishing()) {
				return;
			}
		}
		if (dialog != null && !dialog.isShowing()) {
			RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			rotate.setDuration(1000);
			rotate.setRepeatCount(Animation.INFINITE);
			rotate.setRepeatMode(Animation.RESTART);
			rotate.setInterpolator(new LinearInterpolator());
			loadingRing.startAnimation(rotate);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				if (ownerActivity.isFinishing()||ownerActivity.isDestroyed()) {
					return;
				}
			}else {
				if (ownerActivity.isFinishing()) {
					return;
				}
			}
			if (dialog != null&&!dialog.isShowing()) {
				dialog.show();
			}

		}
	}

	public void dismiss() {
		if (dialog != null && dialog.isShowing()) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				if (ownerActivity.isFinishing()||ownerActivity.isDestroyed()) {
					return;
				}
			}else {
				if (ownerActivity.isFinishing()) {
					return;
				}
			}
			dialog.dismiss();
		}
	}
}
