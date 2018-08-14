package lxkj.train.com.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import lxkj.train.com.overall.OverallData;



/**
 * Created by Administrator on 2016/5/18.
 * 描述：sharedperfence工具类
 */

public class SharedPreferencesUtil {
	private static String CONFIG = "lxkj";
	/**
	 *
	 */

	private static SharedPreferences sharedPreferences;

	// 存
	public static void saveBooleanData(Context context, String key, boolean value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}

		sharedPreferences.edit().putBoolean(key, value).commit();
	}

	// 取
	public static boolean getBooleanData(Context context, String key,
                                         boolean defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		return sharedPreferences.getBoolean(key, defValue);
	}
	//存
	public static void saveStringData(Context context, String key, String value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CONFIG,
					Context.MODE_PRIVATE);
		}

		sharedPreferences.edit().putString(key, value).commit();
	}
	//自定义文件名取
	public static void saveStringDataForName(Context context, String fileName, String key, String value){
		Log.i("zzz", "saveStringDataForName: "+fileName);
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(fileName,
					Context.MODE_PRIVATE);
		}

		sharedPreferences.edit().putString(key, value).commit();
	}

	// 取
	public static String getStringData(Context context, String key, String defValue) {
		Log.i("zzz", "getStringData: "+CONFIG);
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		return sharedPreferences.getString(key, defValue);
	}
	//自定义文件名
	public static String getStringDataForName(Context context, String fileName, String key, String defValue){
		Log.i("zzz", "getStringData: "+fileName);
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		}
		return sharedPreferences.getString(key, defValue);
	}

	/**
	 * 序列化对象
	 *
	 * @param person
	 * @return
	 * @throws IOException
	 */
	private static String serialize(Object person) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);
		objectOutputStream.writeObject(person);
		String serStr = byteArrayOutputStream.toString("ISO-8859-1");
		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		objectOutputStream.close();
		byteArrayOutputStream.close();
		Log.d("serial", "serialize str =" + serStr);
		return serStr;
	}

	/**
	 * 反序列化对象
	 *
	 * @param str
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static Object deSerialization(String str) throws IOException,
            ClassNotFoundException {
		String redStr = java.net.URLDecoder.decode(str, "UTF-8");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				redStr.getBytes("ISO-8859-1"));
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream);
		Object person = objectInputStream.readObject();
		objectInputStream.close();
		byteArrayInputStream.close();
		return person;
	}

	public static void  saveObject(String key, Object object) throws IOException {
		if (TextUtils.isEmpty(key)) {
			return;
		}
		if (sharedPreferences == null) {
			sharedPreferences = OverallData.app.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		SharedPreferences.Editor edit = sharedPreferences.edit();
		edit.putString(key, serialize(object));
		edit.commit();
	}

	public static Object getObject(String key) throws IOException, ClassNotFoundException {
		if (TextUtils.isEmpty(key)) {
			return "";
		}
		if (sharedPreferences == null) {
			sharedPreferences = OverallData.app.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		if (TextUtils.isEmpty(sharedPreferences.getString(key, ""))) {
			return null;
		}
		return deSerialization(sharedPreferences.getString(key, ""));
	}


}
