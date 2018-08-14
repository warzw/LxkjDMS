package lxkj.train.com.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lxkj.train.com.R;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.utils.FileUtil;
import lxkj.train.com.utils.LogUtils;

/**
 * Created by dell on 2018/6/26.
 */

public class TapingDialog implements View.OnClickListener, DialogInterface.OnDismissListener {
    private MediaRecorder recorder;
    private String voicePath = "";
    private long time;
    long m = 0;
    private TextView tv_content;
    private Timer timer;
    private ImageView iv_content;
    private Context context;
    private boolean state = false;
    private  TextView tv_start;
    private List<String> listPath = new ArrayList<>();
    private Handler hd = new Handler();
    //缓存目录
    private String str = OverallData.sdkPath + "/taping/";
    private long geWei;
    private long shiWei;
    private long baiWei;
    private  Dialog dialog5;
    private NotificationManager notificationManager;

    public TapingDialog(Context context) {
        if (context == null) {
            return;
        }
        this.context = context;
        dialog5 = new Dialog(context, R.style.selectorDialog);
        final View view = LayoutInflater.from(context).inflate(R.layout.taping_dialog, null);
        tv_start = view.findViewById(R.id.tv_start);
        TextView tv_save =  view.findViewById(R.id.tv_save);
        TextView tv_delete =  view.findViewById(R.id.tv_delete);
        iv_content = view.findViewById(R.id.iv_content);
        tv_content =  view.findViewById(R.id.tv_time);
        tv_start.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        dialog5.setContentView(view);
        dialog5.setOnDismissListener(this);
        if (((Activity) context).isFinishing() || ((Activity) context).isDestroyed()) {
            return;
        }
        dialog5.show();
    }
    public void show(){
        dialog5.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_start) { //开始
            if ("开始".equals(tv_start.getText().toString().trim())) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        TapingDialog.this.start();
                    }
                }.start();
                tv_start.setText("暂停");
            }else {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        TapingDialog.this.end();
                    }
                }.start();
                tv_start.setText("开始");
            }
        } else if (v.getId() == R.id.tv_save) {//保存
            if (listPath.size()==0) {
                Toast.makeText(context, "请开始录音", Toast.LENGTH_SHORT).show();
                return;
            }
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    end();
                    getInputCollection();
                }
            }.start();
            tv_content.setText("时间：00：00");
            tv_start.setText("开始");
            state = false;
        }else if (v.getId() == R.id.tv_delete) {//删除
            if (listPath.size()==0) {
                Toast.makeText(context, "你还没有录音", Toast.LENGTH_SHORT).show();
                return;
            }
            end();
            for (int i = 0; i < listPath.size(); i++) {
                if (!TextUtils.isEmpty(listPath.get(i))&&new File(listPath.get(i)).isFile()) {
                    new File(listPath.get(i)).delete();
                }
            }
            voicePath = "";
            listPath.clear();
            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
            tv_content.setText("时间：00：00");
            tv_start.setText("开始");
            state = false;
            m = 0;
        }
    }

    /**
     * 开始录音
     */

    public void start() {
        if (timer != null) {
            return;
        }
        if (recorder != null) {
            //不等于空的时候让他变闲置
            recorder.reset();
        } else {
            recorder = new MediaRecorder();
        }
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //输出格式
        recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        //设置音频编码器
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //设置文件名
        voicePath = str + "/lxkj" + System.currentTimeMillis() + ".amr";
        //检查该文件是否存在  否则创建
        FileUtil.createMkdirFile(str, "/lxkj" + System.currentTimeMillis()+ ".amr");
        //设置录音的输出路径
        recorder.setOutputFile(voicePath);
        listPath.add(voicePath);
        try {
            recorder.prepare();
            recorder.start();
            startAnimation();
            state = true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtils.i("recorderIOException", e.getMessage());
        }
        time = System.currentTimeMillis();
        setTime();
    }



    /**
     * 结束语音
     */
    public void end() {
        if (recorder == null || m == 0) {
            return;
        }
        if (timer != null) {
            timer.cancel();
            // 一定设置为null，否则定时器不会被回收
            timer = null;
        }
        long end = System.currentTimeMillis() - time;
        if (end < 1000) {//判断，如果录音时间小于一秒，则删除文件,提示过短
            File file = new File(voicePath);
            if (file.exists()) {//判断文件是否存在，如果存在删除文件
                file.delete();//删除文件
                Toast.makeText(OverallData.app, "录音时间过短", Toast.LENGTH_SHORT).show();
            }
        }
        //重置
        if (recorder != null) {
            try {
                recorder.stop();
                hd.post(new Runnable() {
                    @Override
                    public void run() {
                        iv_content.clearAnimation();
                    }
                });
                state = false;
            } catch (Exception e) {
                LogUtils.i("recorderstopException", "" + e.getMessage());
                recorder = null;
                recorder = new MediaRecorder();
            }
            recorder.release();
            recorder = null;
            System.gc();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (state) {  //如果是在录音，开启通知栏
            createNotification();
        }else { //如果暂停录音，关闭通知栏
            if (notificationManager != null) {
                notificationManager.cancelAll();
            }
        }
    }

    private void startAnimation() {
        hd.post(new Runnable() {
            @Override
            public void run() {
                Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.rotate_anim);
                LinearInterpolator lin = new LinearInterpolator();
                operatingAnim.setInterpolator(lin);
                iv_content.startAnimation(operatingAnim);
            }
        });

    }
    private void setTime() {
        // 初始化定时器
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                m++;
                long wanWei = m / 777600000;
                long qianWei = m % 12960000 / 216000;
                baiWei = m % 216000 / 3600;
                shiWei = m % 3600 / 60;
                geWei = m % 60;
                hd.post(new Runnable() {
                    @Override
                    public void run() {
                        if (geWei < 10) {
                            tv_content.setText("时间: " + baiWei + "" + shiWei + " : 0" + geWei);
                        } else {
                            tv_content.setText("时间: " + baiWei + "" + shiWei + " : " + geWei);
                        }
                    }
                });

            }
        }, 0, 1000);
    }
    //第一个参数就是list集合，第二个参数就是合并后的文件名
    public void getInputCollection() {

        // 创建音频文件,合并的文件放这里
        File file1 = new File(str, "/lxkj" + System.currentTimeMillis()+".amr" );
        FileOutputStream fileOutputStream = null;

        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            fileOutputStream = new FileOutputStream(file1);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //list里面为暂停录音 所产生的 几段录音文件的名字，中间几段文件的减去前面的6个字节头文件
        for (int i = 0; i < listPath.size(); i++) {
            File file = new File((String) listPath.get(i));
            Log.d("list的长度", listPath.size() + "");
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] myByte = new byte[fileInputStream.available()];
                //文件长度
                int length = myByte.length;

                //头文件
                if (i == 0) {
                    while (fileInputStream.read(myByte) != -1) {
                        fileOutputStream.write(myByte, 0, length);
                    }
                }

                //之后的文件，去掉头文件就可以了
                else {
                    while (fileInputStream.read(myByte) != -1) {

                        fileOutputStream.write(myByte, 6, length - 6);
                    }
                }

                fileOutputStream.flush();
                fileInputStream.close();
                System.out.println("合成文件长度：" + file1.length());

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
        //结束后关闭流
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        hd.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
            }
        });
        //千万记住这里需要把集合给清空不然，下次录的时候会把上次录的给带上了
        deleteListRecord();
        m = 0;
    }

    /**
     * 合成一个文件后，删除之前暂停录音所保存的零碎合成文件
     */
    private void deleteListRecord() {
        for (int i = 0; i < listPath.size(); i++) {
            File file = new File(listPath.get(i));
            if (file.exists()) {
                file.delete();
            }
        }
        listPath.clear();
    }
    public void createNotification(){
        String id = " ";
        String name=" ";
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(context)
                    .setChannelId(id)
                    .setContentTitle(" ")
                    .setContentText(" ")
                    .setSmallIcon(R.drawable.taping_notification)
                    .build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle(" ")
                    .setContentText(" ")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setOngoing(true)
                    .setChannelId(id);//无效
            notification = notificationBuilder.build();
        }
        notificationManager.notify(1, notification);
    }
}
