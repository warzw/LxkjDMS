package lxkj.train.com.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.jeromq.ZMQ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import lxkj.train.com.overall.OverallData;
import lxkj.train.com.utils.http.NetworkUtils;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static zmq.ZMQ.ZMQ_SNDMORE;

/**
 * Created by dell on 2018/6/14.
 */

public class ZeroMQMessageUtil implements Observable.OnSubscribe<String>{
    private String message = "";
    private String stringMessage = "";
    private boolean register;
    private BufferedReader mReader;
    private OutputStream outputStream;
    Socket socket;
    private Handler handler = new Handler();
    private InputStream inputStream;

    public ZeroMQMessageUtil(Observer<String> observer) {

        new RxJavaUtil<>(this, observer);
    }

    @Override
    public void call(Subscriber<? super String> subscriber) {
        LogUtils.i("长连接socket", "开始执行");
        if ("无网络连接".equals(NetworkUtils.getNetworkType())) {
            LogUtils.i("长连接socket", "" + "无网络连接");
            return;
        }
        // 创建Socket对象 & 指定服务端的IP及端口号
        try {
            socket = new Socket("192.168.0.15", 9909);
            // 判断客户端和服务器是否连接成功
            if (socket.isConnected()) { //连接成功
                Log.i("BufferedReader2", "连接成功");
                mReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                inputStream = socket.getInputStream();
                BufferedWriter mWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
                outputStream = socket.getOutputStream();
                sendData(); //定时注册请求发送
                Thread.sleep(1000);
                LogUtils.i("BufferedReader2","请求之后");
                byte[] datas = new byte[1024*40];
                int len = inputStream.read(datas);
                byte[] datas2 = new byte[len];
                for (int i = 0; i < datas2.length; i++) {
                    datas2[i] = datas[i];
                }
                inputStream.close();
                //handler发送消息，在handleMessage()方法中接收
                LogUtils.i("BufferedReader2", "--"+datas2.length + "---" +"--"+"");
                if (new String(datas2).contains("$RCFGETDATA$$$")) {
                    register = true;
                    Log.i("BufferedReader2", "注册成功");
                    getATPData(datas2);  //解析ATP数据，并分析项点
                }
            }else {
                Log.i("BufferedReader2", "连接失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        subscriber.onNext(stringMessage);
    }

    // 向车载系统发送注册请求消息
    public byte[] sendRegisteMsg() throws IOException {
        List<Byte> lists = new ArrayList<>();
        String regg = "$REGGETDATA$$$";
        byte[] bytes1 = regg.getBytes("GBK");
        LogUtils.i("requestData2", "$REGGETDATA$$$".getBytes().length + "----" + bytes1.length);
        for (int i = 0; i < bytes1.length; i++) {
            lists.add(bytes1[i]);
        }
        String lists2 = "01234567890123456789012345678901";
        byte[] bytes4 = lists2.getBytes("GBK");
        LogUtils.i("requestData3", "" + bytes4.length);
        for (int i = 0; i < bytes4.length; i++) {
            lists.add(bytes4[i]);
        }
        short reserve = 2;  // 数据内容部分长度
        byte[] bytes2 = ByteTransformUtil.shortToByte(reserve);
        LogUtils.i("requestData4", "" + bytes2.length);
        for (int i = 0; i < bytes2.length; i++) {
            lists.add(bytes2[i]);
        }
        byte[] bytes3 = ByteTransformUtil.shortToByte((short) 1); // 数据内容
        LogUtils.i("requestData5", "" + bytes3.length);
        for (int i = 0; i < bytes3.length; i++) {
            lists.add(bytes3[i]);
        }
        byte[] bytes5 = new byte[lists.size()];
        LogUtils.i("requestData6", "" + bytes5.length);
        for (int i = 0; i < lists.size(); i++) {
            bytes5[i] = lists.get(i);
        }
        return bytes5;

        // 发送byte[]消息

    }

    //获取设备唯一标识UUID
    public String GetDeviceUUid() {
        final TelephonyManager tm = (TelephonyManager) OverallData.app.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice = "", tmSerial, tmPhone, androidId;
        if (ActivityCompat.checkSelfPermission(OverallData.app, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //  here to request the missing permissions, and then overriding
            //  public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults)
            //  to handle the case where the user grants the permission. See the documentation
            //  for ActivityCompat#requestPermissions for more details.
            tmDevice = "" + tm.getDeviceId();
        }
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(OverallData.app.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }
    private void sendData(){ //异步处理注册请求,一分钟请求一次
        new Thread(new Runnable(){
            @Override
            public void run() {
                while (!register){
                    LogUtils.i("请求一次", register + "  请求一次  " + socket.isConnected());
                    try {
                        outputStream.write(sendRegisteMsg());
                        outputStream.flush();
                        Thread.sleep(100000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private void getATPData(byte[] data){ //异步获取数据
        byte[] type = new byte[2];  //业务类型
        type[0] = data[54];
        type[1] = data[55];
        LogUtils.i("BufferedReader2业务类型",""+ByteTransformUtil.getShort(type));
        byte[] childData = new byte[data.length-62]; //具体数据内容
        for (int i = 58; i < data.length-4; i++) {
            childData[i-58] = data[i];
        }
        if (1==ByteTransformUtil.getShort(type)) { //ATP数据
            byte[] infoLeng = new byte[2]; //具体数据长度
            infoLeng[0] = childData[2];
            infoLeng[1] = childData[3];
            byte[] data2 = new byte[2];//数据标识
            data2[0] = childData[6];
            data2[1] = childData[7];
            LogUtils.i("BufferedReader2ATP数据标示",""+ByteTransformUtil.getShort(infoLeng));
            byte[] content = new byte[ByteTransformUtil.byteArrayToInt(infoLeng)]; //最后数据内容

            for (int i = 0; i <content.length ; i++) {
                content[i] = childData[i+16];
            }
            byte[] time = new byte[8];  //最后时间
            for (int i = 0; i <time.length ; i++) {
                time[i] =  content[i+1];
            }
            double timelong =ByteTransformUtil.bytes2Double(time);
            LogUtils.i("BufferedReader2",timelong+"---");
            String currentTime_ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(Math.round(timelong)));
            LogUtils.i("BufferedReader2",timelong+"---"+currentTime_ymd);

        }else if (2==ByteTransformUtil.getShort(type)) { //DCMS数据

        }else if (3==ByteTransformUtil.getShort(type)) { // WTD数据

        }else if (4==ByteTransformUtil.getShort(type)) { //司机室视频

        }else if (5==ByteTransformUtil.getShort(type)) { // 司机室音频

        }else if (6==ByteTransformUtil.getShort(type)) {  //日志信息

        }else if (7==ByteTransformUtil.getShort(type)) {  //CIR数据

        }else if (8==ByteTransformUtil.getShort(type)) {  //CIR音频数据

        }else if (8==ByteTransformUtil.getShort(type)) {  //前置摄像头数据

        }
        LogUtils.i("BufferedReader2",""+ByteTransformUtil.getShort(type));

    }
}
