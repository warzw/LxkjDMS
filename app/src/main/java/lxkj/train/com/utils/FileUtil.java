package lxkj.train.com.utils;

import android.os.Environment;
import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import lxkj.train.com.overall.OverallData;
import lxkj.train.com.view.LoadingDialog;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dell on 2018/6/11.
 * 读取word的工具类
 */

public class FileUtil {
    private final static String TAG = "FileUtil";

    public static String getFileName(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return pathandname.substring(start + 1, end);
        } else {
            return "";
        }
    }

    public static String createFile(String dir_name, String file_name) {
        String sdcard_path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String dir_path = String.format("%s/Download/%s", sdcard_path, dir_name);
        String file_path = String.format("%s/%s", dir_path, file_name);
        try {
            File dirFile = new File(dir_path);
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
            File myFile = new File(file_path);
            myFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file_path;
    }
    //检查该文件是否存在  否则创建
    public static String createMkdirFile(String dir_name, String file_name) {
        try {
            File dirFile = new File(dir_name);
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
            File myFile = new File(dir_name+file_name);
            myFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.i("createFileException",e.getMessage());
        }
        return dir_name+file_name;
    }
    public  void saveFile(final String dir_name, final String file_name, final byte[] data, final LoadingDialog dialog) {
        if (dialog != null) {
            dialog.show();
        }
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                File file = new File(dir_name + file_name);
                if (file.exists()) {
                    file.delete();
                }
                String filePath = FileUtil.createMkdirFile(dir_name, file_name);
                try {
                    //文件输出流
                    FileOutputStream fos = new FileOutputStream(filePath);
                    //写数据
                    fos.write(data);
                    fos.flush();
                    //关闭文件流
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.i("succeedData", e.getMessage());
                }
                LogUtils.i("succeedData", new File(filePath).exists() + "---" + new File(filePath).getName());
                subscriber.onNext("完成");
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                dialog.dismiss();
            }
        });
    }

    public static ZipEntry getPicEntry(ZipFile docxFile, int pic_index) {
        String entry_jpg = "word/media/image" + pic_index + ".jpeg";
        String entry_png = "word/media/image" + pic_index + ".png";
        String entry_gif = "word/media/image" + pic_index + ".gif";
        String entry_wmf = "word/media/image" + pic_index + ".wmf";
        ZipEntry pic_entry = null;
        pic_entry = docxFile.getEntry(entry_jpg);
        // 以下为读取docx的图片 转化为流数组
        if (pic_entry == null) {
            pic_entry = docxFile.getEntry(entry_png);
        }
        if (pic_entry == null) {
            pic_entry = docxFile.getEntry(entry_gif);
        }
        if (pic_entry == null) {
            pic_entry = docxFile.getEntry(entry_wmf);
        }
        return pic_entry;
    }

    public static byte[] getPictureBytes(ZipFile docxFile, ZipEntry pic_entry) {
        byte[] pictureBytes = null;
        try {
            InputStream pictIS = docxFile.getInputStream(pic_entry);
            ByteArrayOutputStream pOut = new ByteArrayOutputStream();
            byte[] b = new byte[1000];
            int len = 0;
            while ((len = pictIS.read(b)) != -1) {
                pOut.write(b, 0, len);
            }
            pictIS.close();
            pOut.close();
            pictureBytes = pOut.toByteArray();
            Log.d(TAG, "pictureBytes.length=" + pictureBytes.length);
            if (pictIS != null) {
                pictIS.close();
            }
            if (pOut != null) {
                pOut.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pictureBytes;

    }

    public static void writePicture(String pic_path, byte[] pictureBytes) {
        File myPicture = new File(pic_path);
        try {
            FileOutputStream outputPicture = new FileOutputStream(myPicture);
            outputPicture.write(pictureBytes);
            outputPicture.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
