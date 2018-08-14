package lxkj.train.com.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import lxkj.train.com.R;

/**
 * Created by dell on 2018/6/13.
 */

public class DialogView {

    //可以取消的dialog
    @SuppressLint("NewApi")
    public static void hintDialog(Context context, final DialogConfirm dialogConfirm, String title,String content, boolean ishow) {
        if (context == null) {
            return;
        }
        final Dialog dialog5 = new Dialog(context, R.style.selectorDialog);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_exit, null);
        TextView bt_ok = (TextView) view.findViewById(R.id.bt_confirm);
        TextView suanle = (TextView) view.findViewById(R.id.bt_suanle);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
        tv_content.setText(content);
        if (!ishow) {
            suanle.setVisibility(View.GONE);
        }
        suanle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog5.dismiss();
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog5.dismiss();
                if (dialogConfirm != null) {
                    dialogConfirm.confirm();
                }

            }
        });
        dialog5.setContentView(view);
        if (((Activity)context).isFinishing()||((Activity)context).isDestroyed()) {
            return;
        }
        dialog5.show();
    }
    //不可以取消的dialog
    @SuppressLint("NewApi")
    public static void showDialog(Context context, final DialogConfirm dialogConfirm, String title,String content, boolean ishow) {
        if (context == null) {
            return;
        }
        final Dialog dialog5 = new Dialog(context, R.style.selectorDialog);
        dialog5.setCancelable(false);
        dialog5.setCanceledOnTouchOutside(false);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_exit, null);
        TextView bt_ok = (TextView) view.findViewById(R.id.bt_confirm);
        TextView suanle = (TextView) view.findViewById(R.id.bt_suanle);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
        tv_content.setText(content);
        if (!ishow) {
            suanle.setVisibility(View.GONE);
        }
        suanle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog5.dismiss();
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog5.dismiss();
                if (dialogConfirm != null) {
                    dialogConfirm.confirm();
                }

            }
        });
        dialog5.setContentView(view);
        if (((Activity)context).isFinishing()||((Activity)context).isDestroyed()) {
            return;
        }
        dialog5.show();
    }
    //系统公告的 Dialog
    public static void systemNoticeDialog(Activity activity, String title, String content, final DialogCancel dialogCancel) {
        final Dialog systemNoticeDialog = new Dialog(activity, R.style.selectorDialog);
        View view = LayoutInflater.from(activity).inflate(R.layout.system_notice_layout, null);
        systemNoticeDialog.setContentView(view);
        systemNoticeDialog.show();
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_title.setText(title);
        tv_content.setText(content);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                systemNoticeDialog.dismiss();
                if (dialogCancel != null) {
                    dialogCancel.cancel();
                }
            }
        });
    }
    public interface DialogConfirm {
        void confirm();
    }

    public interface DialogCancel {
        void cancel();
    }
    public interface DialogExtra {
        void extra();
    }
    //菜单样式
    public static void shareBbenefitQueryDialog(Context context, final DialogConfirm dialogConfirm, final DialogCancel dialogCancel, final DialogExtra dialogExtra, String topString, String contentString, String bottomString) {
        if (context == null) {
            return;
        }

        final Dialog dialog5 = new Dialog(context, R.style.selectorDialog);
        final View view = LayoutInflater.from(context).inflate(R.layout.my_fenrun_dialog, null);
        TextView tv_1 = (TextView) view.findViewById(R.id.tv_1);
        TextView tv_2 = (TextView) view.findViewById(R.id.tv_2);
        TextView tv_3 = (TextView) view.findViewById(R.id.tv_3);
        if (!TextUtils.isEmpty(topString)) {
            tv_1.setVisibility(View.VISIBLE);
            tv_1.setText(topString);
        } else {
            tv_1.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(contentString)) {
            tv_2.setVisibility(View.VISIBLE);
            tv_2.setText(contentString);
        } else {
            tv_2.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(bottomString)) {
            tv_3.setVisibility(View.VISIBLE);
            tv_3.setText(bottomString);
        } else {
            tv_3.setVisibility(View.GONE);
        }
        tv_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog5.dismiss();
                if (dialogConfirm != null) {
                    dialogConfirm.confirm();
                }

            }
        });
        tv_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog5.dismiss();
                if (dialogCancel != null) {
                    dialogCancel.cancel();
                }

            }
        });
        tv_3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog5.dismiss();
                if (dialogExtra != null) {
                    dialogExtra.extra();
                }

            }
        });
        dialog5.setContentView(view);
         /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = dialog5.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);

        /*
         * lp.x与lp.y表示相对于原始位置的偏移.
         * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
         * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
         * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
         * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
         * 当参数值包含Gravity.CENTER_HORIZONTAL时
         * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
         * 当参数值包含Gravity.CENTER_VERTICAL时
         * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
         * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
         * Gravity.CENTER_VERTICAL.
         *
         * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
         * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了,
         * Gravity.LEFT, Gravity.TOP, Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
         */
        lp.x = 24; // 新位置X坐标
        lp.y = 120; // 新位置Y坐标
        dialogWindow.setAttributes(lp);
        dialog5.show();
    }
}
