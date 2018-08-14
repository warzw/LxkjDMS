package lxkj.train.com.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.ResourceUtil;

import java.util.ArrayList;
import java.util.Locale;

import lxkj.train.com.interfaces.TTSInterface;
import lxkj.train.com.mvp.view.activity.AnnouncementActivity;
import lxkj.train.com.mvp.view.activity.BasicInfoActivity;
import lxkj.train.com.mvp.view.activity.CallTheWatchActivity;
import lxkj.train.com.mvp.view.activity.ShowFileActivity;
import lxkj.train.com.mvp.view.activity.TrainNumberActivity;
import lxkj.train.com.mvp.view.activity.TrainQueryActivity;
import lxkj.train.com.mvp.view.activity.base.BaseActivity;
import lxkj.train.com.overall.OverallData;

import static com.iflytek.cloud.SpeechConstant.TYPE_LOCAL;

/**
 * Created by dell on 2018/6/28.
 */
@SuppressLint("NewApi")
public class TTSUtil implements SynthesizerListener, InitListener {
    private Context mContext;
    private static TTSUtil singleton;
    private TextToSpeech textToSpeech; // 系统语音播报类
    private boolean isSuccess = true;
    private SpeechSynthesizer mTts;
    private Intent intent;

    public TTSUtil(Context var1) {
        mContext = var1;
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(var1, this);
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        //设置合成
        //设置使用本地引擎
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
        //设置发音人资源路径
        mTts.setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath());
        //设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH,"50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");

        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }
    //获取发音人资源路径
    private String getResourcePath(){
        StringBuffer tempBuffer = new StringBuffer();
        //合成通用资源
        tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "tts/common.jet"));
        tempBuffer.append(";");
        //发音人资源
        tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "tts/"+"xiaoyan"+".jet"));
        return tempBuffer.toString();
    }
    public void startSpeaking(String txt) {
        if (mTts != null) {
            int code =mTts.startSpeaking(txt, this);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(mContext, "语音合成失败,错误码: " + code, Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void stopSpeaking() {
        if (mTts != null) {
            mTts.stopSpeaking();
        }

    }

    @Override
    public void onSpeakBegin() {

    }

    @Override
    public void onBufferProgress(int i, int i1, int i2, String s) {

    }

    @Override
    public void onSpeakPaused() {

    }

    @Override
    public void onSpeakResumed() {

    }

    @Override
    public void onSpeakProgress(int i, int i1, int i2) {

    }

    @Override
    public void onCompleted(SpeechError speechError) {

    }

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {

    }

    @Override
    public void onInit(int i) {

    }
    private String environmentPath = Environment.getExternalStorageDirectory().getPath() + "/" + "lxkj" + "/";
    public void initSpeech(final BaseActivity activity) {
        //1、初始化窗口
        RecognizerDialog dialog = new RecognizerDialog(activity, null);
        //2、设置听写参数，详见官方文档
        //识别中文听写可设置为"zh_cn",此处为设置英文听写
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //3、开始听写
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                if (!b) {
                    String result = parseVoice(recognizerResult.getResultString());
                    LogUtils.i("RecognizerDialog", "" + result);
                    Toast.makeText(OverallData.app, "你说的话是：" + result, Toast.LENGTH_SHORT).show();
                    if (result.contains("规章资料")||result.contains("资料")||result.contains("规章")||result.contains("治疗")) {
                        intent = new Intent(activity, ShowFileActivity.class);
                        intent.putExtra("environmentPath", environmentPath);
                        intent.putExtra("title", "规章资料");
                        activity.startActivity(intent);
                    }else if (result.contains("列车查询")||result.contains("列车")||result.contains("查询")) {
                        intent = new Intent(activity, TrainQueryActivity.class);
                        activity.startActivity(intent);
                    }else if (result.contains("公告查看")||result.contains("公告")||result.contains("查看")||result.contains("广告")) {
                        intent = new Intent(activity, AnnouncementActivity.class);
                        activity.startActivity(intent);
                    }else if (result.contains("出勤叫班")||result.contains("出勤")||result.contains("叫班")) {
                        intent = new Intent(activity, CallTheWatchActivity.class);
                        activity.startActivity(intent);
                    }else if (result.contains("查看车次")||result.contains("查看")||result.contains("车次")) {
                        intent = new Intent(activity, TrainNumberActivity.class);
                        activity.startActivity(intent);
                    }else if (result.contains("基本信息")||result.contains("基本")||result.contains("信息")) {
                        intent = new Intent(activity, BasicInfoActivity.class);
                        activity.startActivity(intent);
                    }
                }
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        dialog.show();
    }

    //解析Gson对象
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class);

        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }

    /**
     * 语音对象封装
     */
    public class Voice {

        public ArrayList<WSBean> ws;

        public class WSBean {
            public ArrayList<CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
    }
}
