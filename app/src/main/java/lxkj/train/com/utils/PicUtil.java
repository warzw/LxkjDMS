package lxkj.train.com.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import lxkj.train.com.overall.OverallData;

public class PicUtil {

    public static int CODE_SELECT_PIC_MULTIPLE = 5560;
    public static int CODE_SELECT_PIC = 5561;
    public static int CODE_CAMERA_PIC = 5562;
    public static int CODE_CROP_PIC = 5563;
    public static String filePath = "";
    public static int CODE_CURRENT = 0;
    public static int CODE_REQUEST_TAG = 0;
    public static boolean isCompress = true;
    private static int subtract;

    /**
     * 开启单选图片相册
     *
     * @param context
     */
    public static void openPhoto(Activity context) {
        CODE_CURRENT = CODE_SELECT_PIC;
        CODE_REQUEST_TAG = CODE_SELECT_PIC;
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        context.startActivityForResult(intent, CODE_SELECT_PIC);
    }

    /**
     * 打开照相
     *
     * @param context
     * @return
     */
    public static void openCamera(final Activity context) {
        if (!isEnvironment(context)) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionsManager.get().checkPermissions(context, Manifest.permission.CAMERA, new PermissionsManager.CheckCallBack() {
                @Override
                public void onSuccess(String permission) {
                    takeCamera(context, CODE_CAMERA_PIC);
                }

                @Override
                public void onError(String permission) {

                }
            });
        } else {
            takeCamera(context, CODE_CAMERA_PIC);
        }
    }
    /**
     * 打开录制视频
     *
     * @param context
     * @return
     */
    public static void openVideo(final Activity context) {
        if (!isEnvironment(context)) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionsManager.get().checkPermissions(context, Manifest.permission.CAMERA, new PermissionsManager.CheckCallBack() {
                @Override
                public void onSuccess(String permission) {
                    recordVideo(context);
                }

                @Override
                public void onError(String permission) {

                }
            });
        } else {
            recordVideo(context);
        }
    }

    private static String takeCamera(Activity context, int reqCode) {  //拍照
        CODE_CURRENT = reqCode;
        CODE_REQUEST_TAG = CODE_CAMERA_PIC;
        ContentValues values = new ContentValues();
        long time = new Date(System.currentTimeMillis()).getTime();
        values.put(MediaStore.Images.Media.TITLE, new Date(System.currentTimeMillis()).getTime());
        File photoFile = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "DCIM"
                + "/" + time + ".jpg");
        filePath = photoFile.getPath();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        context.startActivityForResult(intent, reqCode);
        return filePath;
    }

    public static String recordVideo(Activity context) {  //录制视频
        CODE_CURRENT = 10;
        CODE_REQUEST_TAG = CODE_CAMERA_PIC;
        ContentValues values = new ContentValues();
        long time = new Date(System.currentTimeMillis()).getTime();
        values.put(MediaStore.Images.Media.TITLE, new Date(System.currentTimeMillis()).getTime());
        File photoFile = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "DCIM"
                + "/" + time + ".3gp");
        filePath = photoFile.getPath();
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri = Uri.fromFile(photoFile);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); //清晰度，0是不清晰，1是高清
        //intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);// 最大长度
        //intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024 * 1024);// 以字节为单位
        intent.putExtra(MediaStore.EXTRA_FULL_SCREEN, true);// 以字节为单位
        //intent.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);// 默认值为true,这意味着自动退出电影播放器活动电影完成后玩。
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        context.startActivityForResult(intent, 10);
        return filePath;
    }

    public static Object onActivityResult(Activity context, int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (CODE_REQUEST_TAG == CODE_SELECT_PIC && requestCode == CODE_CURRENT && data != null) {
                filePath = UriUtils.getImageAbsolutePath(context, data.getData());
                return filePath;
            } else if (CODE_REQUEST_TAG == CODE_CAMERA_PIC && requestCode == CODE_CURRENT) {
                LogUtils.i("拍照龙哥", filePath);
                //compressBitmap(filePath, new File(filePath));
                return filePath;
            } else if (CODE_REQUEST_TAG == CODE_SELECT_PIC_MULTIPLE && requestCode == CODE_CURRENT && data != null) {
                //compressBitmap(filePath, new File(filePath));
                return data.getStringArrayListExtra(OverallData.TAG);
            } else if (CODE_REQUEST_TAG == CODE_CROP_PIC && requestCode == CODE_CURRENT) {
                //compressBitmap(filePath, new File(filePath));
                return filePath;
            }else if (requestCode == 10&& data != null) {
                filePath = UriUtils.getImageAbsolutePath(context, data.getData());
                return filePath;
            }
        }
        return null;
    }

    // 判断时候有SD卡
    public static boolean isEnvironment(Context context) {

        String sdState = Environment.getExternalStorageState();
        if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "请检查SD卡是否安装", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取sd卡
     *
     * @return
     */
    public static String getSDCardPath() {
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
        BufferedInputStream in = null;
        BufferedReader inBr = null;
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            in = new BufferedInputStream(p.getInputStream());
            inBr = new BufferedReader(new InputStreamReader(in));

            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                // 获得命令执行后在控制台的输出信息
                if (lineStr.contains("sdcard")
                        && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray != null && strArray.length >= 5) {
                        String result = strArray[1].replace("/.android_secure",
                                "");
                        return result;
                    }
                }
                // 检查命令是否执行失败。
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    // p.exitValue()==0表示正常结束，1：非正常结束
                    Log.e("getSDCardPath", "命令执行失败!");
                }
            }
        } catch (Exception e) {
            Log.e("getSDCardPath", e.toString());
            return Environment.getExternalStorageDirectory().getPath();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inBr != null) {
                    inBr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Environment.getExternalStorageDirectory().getPath();
    }

    //设置图片的采样率，降低图片像素
    public static void compressBitmap(String filePath, File file) {
        Bitmap bitmap = getSmallBitmap(filePath);
        LogUtils.i("bitmap", "" + bitmap.toString());
        byte[] bytes = Bitmap2Bytes(bitmap);
        LogUtils.i("bytes", new String(bytes));
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 质量压缩方法
     *
     * @param
     * @return
     */
    public void compressImage(String filePath, int size) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        if (baos.toByteArray().length / 1024 <= size) {
        } else {
            if ((baos.toByteArray().length / 1024) / size >= 1 && (baos.toByteArray().length / 1024) / size < 5) {
                options = 80;
            } else if ((baos.toByteArray().length / 1024) / size >= 5 && (baos.toByteArray().length / 1024) / size < 8) {
                options = 60;
            } else if ((baos.toByteArray().length / 1024) / size >= 8 && (baos.toByteArray().length / 1024) / size < 13) {
                options = 40;
            } else {
                options = 20;
            }
        }
        LogUtils.i("options", baos.toByteArray().length / 1024 / size + "---" + options);
        while (options > 0 && options < 100 && baos.toByteArray().length / 1024 - 20 > size) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 4;
            LogUtils.i("options1", baos.toByteArray().length / 1024 + "---" + options);
        }
        while (options > 0 && baos.toByteArray().length / 1024 + 25 < size) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            options += 6;
            baos.reset(); // 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            LogUtils.i("options2", baos.toByteArray().length / 1024 + "---" + options);
        }
        try {
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
