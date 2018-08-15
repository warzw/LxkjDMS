package lxkj.train.com.utils;


import android.text.TextUtils;

import com.example.tutorial.MReqFileDownProto;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;

import lxkj.train.com.overall.TextUtil;

/**
 * Created by dell on 2018/6/20.
 */

public class ProtoUtil{
    public static  byte[] getMReqFileDown( int subtype, int reqtime, int uuid, ByteString content){
        MReqFileDownProto.MReqFileDown.Builder builder = MReqFileDownProto.MReqFileDown.newBuilder();
        builder.setSubtype(subtype);
        builder.setReqtime(reqtime);
        builder.setUuid(uuid);
        builder.setContent(content);
        // 生成product对象
        MReqFileDownProto.MReqFileDown product = builder.build();
        // 转换为Byte
        return product.toByteArray();
    }
    public static MReqFileDownProto.SucceedFileDown getSucceedFileDown( byte[] bytes){
        // 反解
        try
        {
            return MReqFileDownProto.SucceedFileDown.parseFrom(bytes);
        }
        catch (InvalidProtocolBufferException e)
        {
            e.printStackTrace();
            LogUtils.i("InvalidProtocolBufferException",e.getMessage());
            return null;
        }
    }
    public static ByteString getMReqBasicData(int baseType,ByteString md5 ){
        MReqFileDownProto.MReqBasicData.Builder builder = MReqFileDownProto.MReqBasicData.newBuilder();
        // 转换为Byte
        return builder.setMd5(md5).
                setTime((int) (System.currentTimeMillis()/1000)).
                setBasetype(baseType).build().toByteString();
    }
    public static MReqFileDownProto.MRespBasicData getMRespBasicData(ByteString bytes){
        // 反解
        try
        {
            return MReqFileDownProto.MRespBasicData.parseFrom(bytes);
        }
        catch (InvalidProtocolBufferException e)
        {
            e.printStackTrace();
            LogUtils.i("InvalidProtocolBufferException",e.getMessage());
            return null;
        }
    }
    public static MReqFileDownProto.MRespBizFileData getMRespBizFileData(ByteString bytes){
        // 反解
        try
        {
            return MReqFileDownProto.MRespBizFileData.parseFrom(bytes);
        }
        catch (InvalidProtocolBufferException e)
        {
            e.printStackTrace();
            LogUtils.i("InvalidProtocolBufferException",e.getMessage());
            return null;
        }
    }
    public static ByteString getMReqBizFileData(ByteString md5 ,ByteString filePath){  //业务数据
        MReqFileDownProto.MReqBizFileData.Builder builder = MReqFileDownProto.MReqBizFileData                                                                                               .newBuilder();
        builder.setMd5(md5);
        builder.setFilepath(filePath);
        // 生成product对象
        MReqFileDownProto.MReqBizFileData product = builder.build();
        LogUtils.i("Subtype",""+product.getMd5());
        // 转换为Byte
        return product.toByteString();
    }
    public static ByteString getMReqNotice(int driverid){
        MReqFileDownProto.MReqNotice.Builder builder = MReqFileDownProto.MReqNotice.newBuilder();
        builder.setDriverid(driverid);
        // 生成product对象
        MReqFileDownProto.MReqNotice product = builder.build();
        LogUtils.i("Subtype",""+product.getDriverid());
        // 转换为Byte
        return product.toByteString();
    }
    public static MReqFileDownProto.MReqNotice getMReqNotice(ByteString bytes){
        // 反解
        try
        {
            return MReqFileDownProto.MReqNotice.parseFrom(bytes);
        }
        catch (InvalidProtocolBufferException e)
        {
            e.printStackTrace();
            LogUtils.i("InvalidProtocolBufferException",e.getMessage());
            return null;
        }
    }
    public static MReqFileDownProto.MRespNoticeList getMRespNoticeList(ByteString bytes){
        // 反解
        try
        {
            return MReqFileDownProto.MRespNoticeList.parseFrom(bytes);
        }
        catch (InvalidProtocolBufferException e)
        {
            e.printStackTrace();
            LogUtils.i("InvalidProtocolBufferException",e.getMessage());
            return null;
        }
    }
    public static byte[] getMReqQuery(int subtype,int reqtime,ByteString content){  //公告查询请求
        MReqFileDownProto.MReqQuery.Builder builder = MReqFileDownProto.MReqQuery.newBuilder();
        builder.setSubtype(subtype);
        builder.setReqtime(reqtime);
        builder.setContent(content);
        // 生成product对象
        MReqFileDownProto.MReqQuery product = builder.build();
        LogUtils.i("Subtype",""+product.getSubtype()+"---"+product.getReqtime());
        // 转换为Byte
        return product.toByteArray();
    }
    public static ByteString getMReqAttndPlan(int driverid,int timeBegin,int timeEnd){
        MReqFileDownProto.MReqAttndPlan.Builder builder = MReqFileDownProto.MReqAttndPlan.newBuilder();
        // 生成product对象
        builder.setDriverid(driverid);
        builder.setTimebegin(timeBegin);
        builder.setTimeend(timeEnd);
        MReqFileDownProto.MReqAttndPlan product = builder.build();
        // 转换为Byte
        return product.toByteString();
    }
    public static MReqFileDownProto.MRespAttndPlan getMRespAttndPlan(ByteString bytes){ //司机出勤计划
        // 反解
        try
        {
            return MReqFileDownProto.MRespAttndPlan.parseFrom(bytes);
        }
        catch (InvalidProtocolBufferException e)
        {
            e.printStackTrace();
            LogUtils.i("InvalidProtocolBufferException",e.getMessage());
            return null;
        }
    }
    public static ByteString getMReqTrainPos(String trainnum){
        MReqFileDownProto.MReqTrainPos.Builder builder = MReqFileDownProto.MReqTrainPos.newBuilder();
        // 生成product对象
        if (TextUtils.isEmpty(trainnum)||trainnum.length()==1) {
            builder.setTrainnum("G123");
        }else {
            builder.setTrainnum(trainnum);
        }
        builder.setTrainid(2);
        MReqFileDownProto.MReqTrainPos product = builder.build();

        // 转换为Byte
        return product.toByteString();
    }
    public static MReqFileDownProto.MRespTrainPos getMRespTrainPos(ByteString bytes){ //司机出勤计划
        // 反解
        try
        {
            return MReqFileDownProto.MRespTrainPos.parseFrom(bytes);
        }
        catch (InvalidProtocolBufferException e)
        {
            e.printStackTrace();
            LogUtils.i("InvalidProtocolBufferException",e.getMessage());
            return null;
        }
    }
    //查询司机叫班，请求 pb
    public static ByteString getMReqCurDriverAttnd(int driverid){
        MReqFileDownProto.MReqCurDriverAttnd.Builder builder = MReqFileDownProto.MReqCurDriverAttnd.newBuilder();
        // 生成product对象
        builder.setDriverid(driverid);
        MReqFileDownProto.MReqCurDriverAttnd product = builder.build();
        // 转换为Byte
        return product.toByteString();
    }
    //司机叫班 响应 pb
    public static MReqFileDownProto.MRespCurDriverAttnd getMRespCurDriverAttnd(ByteString bytes){ //司机出勤计划
        // 反解
        try
        {
            return MReqFileDownProto.MRespCurDriverAttnd.parseFrom(bytes);
        }
        catch (InvalidProtocolBufferException e)
        {
            e.printStackTrace();
            LogUtils.i("InvalidProtocolBufferException",e.getMessage());
            return null;
        }
    }
}
