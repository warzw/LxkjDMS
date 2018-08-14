package lxkj.train.com.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/7/17.
 */

public class RequestUtil {
    public static String requestData(byte msgtypes,byte servicetypes,short reserves,byte[] data){
        List<Byte> lists = new ArrayList<>();
        int packetlen;  //数据总长度
        byte msgtype = msgtypes;  // 1是请求，2是返回 ,3是推送
        byte servicetype = servicetypes; //服务类型，具体的请求哪个接口
        short reserve = reserves;  // 预留字段
          //具体的protoBuf内容
        int datalen = data.length;  //protoBUf的长度
        packetlen = 12+ data.length;
        byte[] bytes1 = ByteTransformUtil.intToBytes2(packetlen);
        LogUtils.i("requestData",""+bytes1.length);
        for (int i = 0; i < bytes1.length; i++) {
            lists.add(bytes1[i]);
        }
        lists.add(msgtype);
        lists.add(servicetype);
        byte[] bytes2 = ByteTransformUtil.shortToByte(reserve);
        LogUtils.i("requestData",""+bytes2.length);
        for (int i = 0; i < bytes2.length; i++) {
            lists.add(bytes2[i]);
        }
        byte[] bytes3 = ByteTransformUtil.intToBytes2(datalen);
        LogUtils.i("requestData",""+bytes3.length);
        for (int i = 0; i < bytes3.length; i++) {
            lists.add(bytes3[i]);
        }
        LogUtils.i("requestData",""+data.length);
        for (int i = 0; i <data.length ; i++) {
            lists.add(data[i]);
        }
        byte[] bytes5 = new byte[lists.size()];
        LogUtils.i("requestData",""+bytes5.length);
        for (int i = 0; i < lists.size(); i++) {
            bytes5[i] = lists.get(i);
        }

        try {
            LogUtils.i("requestData",bytes5.length+"--"+new String(bytes5,"GBK").getBytes("GBK").length+"=="+data.length);
            return new String(bytes5,"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static byte[] requestByteData(byte servicetypes,short reserves,byte[] data){
        List<Byte> lists = new ArrayList<>();
        int packetlen;  //数据总长度
        byte msgtype = 1;  // 1是请求，2是返回 ,3是推送
        byte servicetype = servicetypes; //服务类型，具体的请求哪个接口
        short reserve = reserves;  // 预留字段
        //具体的protoBuf内容
        int datalen = data.length;  //protoBUf的长度
        packetlen = 12+ data.length;
        byte[] bytes1 = ByteTransformUtil.intToBytes2(packetlen);
        LogUtils.i("requestData",""+bytes1.length);
        for (int i = 0; i < bytes1.length; i++) {
            lists.add(bytes1[i]);
        }
        lists.add(msgtype);
        lists.add(servicetype);
        byte[] bytes2 = ByteTransformUtil.shortToByte(reserve);
        LogUtils.i("requestData",""+bytes2.length);
        for (int i = 0; i < bytes2.length; i++) {
            lists.add(bytes2[i]);
        }
        byte[] bytes3 = ByteTransformUtil.intToBytes2(datalen);
        LogUtils.i("requestData",""+bytes3.length);
        for (int i = 0; i < bytes3.length; i++) {
            lists.add(bytes3[i]);
        }
        LogUtils.i("requestData",""+data.length);
        for (int i = 0; i <data.length ; i++) {
            lists.add(data[i]);
        }
        byte[] bytes5 = new byte[lists.size()];
        LogUtils.i("requestData",""+bytes5.length);
        for (int i = 0; i < lists.size(); i++) {
            bytes5[i] = lists.get(i);
        }
        //AAAAFgECAgAAAAAKCAEQype62gUYAQ==
        LogUtils.i("requestData",packetlen+"--"+bytes5+"=="+data.length);
        return bytes5;
    }
}
