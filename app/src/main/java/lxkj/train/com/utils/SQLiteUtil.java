package lxkj.train.com.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.interfaces.SQLDataInterface;
import lxkj.train.com.mvp.presenter.presenter_impl.TrainNumberPresenter;
import lxkj.train.com.overall.OverallData;
import lxkj.train.com.overall.TextUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by dell on 2018/7/18.
 */

public class SQLiteUtil implements Observable.OnSubscribe<List<List<String>>>, Observer<List<List<String>>> {
    private SQLiteDatabase db;
    private SQLDataInterface sqlDataInterface;
    private String tableName;
    private String keyword;
    private int index;
    private int count;
    private String path;
    public SQLiteUtil(String path) {
        this.path = path;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
    }

    public void createTable(String statement) { //创建表
        if (db == null) {
            LogUtils.i("SQLiteUtil", "没有找到这个db文件");
            return;
        }
        //创建表SQL语句
        String stu_table = "create table usertable(_id integer primary key autoincrement,sname text,snumber text)";
        //执行SQL语句
        db.execSQL(statement);
    }

    public void insert(String statement) {  //插入数据库
        if (db == null) {
            LogUtils.i("SQLiteUtil", "没有找到这个db文件");
            return;
        }
        //插入数据SQL语句
        String stu_sql = "insert into stu_table(sname,snumber) values('xiaoming','01005')";
        //执行SQL语句
        db.execSQL(statement);
    }

    public void delete(String statement) { //删除数据库
        //删除SQL语句
        String sql = "delete from stu_table where _id = 6";
        //执行SQL语句
        db.execSQL(statement);
    }

    public void update(String statement) {  //修改数据库
        //修改SQL语句
        String sql = "update stu_table set snumber = 654321 where id = 1";
        //执行SQL
        db.execSQL(statement);
    }

    /**
     * @param tableName 表名
     * @param keyword   关键字
     * @param index     要查询的字段在表中的第几列
     * @param count     表中字段的总数
     * @return
     */
    private List<List<String>> query(String tableName, String keyword, int index, int count) {  //查询数据库
        List<List<String>> data = new ArrayList<>();
        //查询获得游标
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        LogUtils.i("开始查询",""+cursor.getCount());
        //判断游标是否为空
        if (cursor.moveToFirst()) {
            //遍历游标
            LogUtils.i("cursormoveToFirst", "" + cursor.getCount());
            for (int i = 0; i < cursor.getCount(); i++) {
                if (!TextUtils.isEmpty(keyword) && cursor.getString(index).equals(keyword)) {
                    List<String> list = new ArrayList<>();
                    for (int j = 0; j < count; j++) {
                        LogUtils.i("sqlquery", "" + cursor.getString(j));
                        list.add("" + cursor.getString(j));  //查询到的字段内容
                    }
                    data.add(list);

                } else if (TextUtils.isEmpty(keyword)) { //如果关键字是空，就查询所有
                    List<String> list = new ArrayList<>();
                    for (int j = 0; j < count; j++) {
                        try {
                            LogUtils.i("sqlquery", i + "---" + new String(cursor.getString(j).getBytes("UTF-8"),"UTF-8"));
                            list.add("" + cursor.getString(j));  //查询到的字段内容
                        } catch (Exception e) {
                            LogUtils.i("sqlqueryException", e.getMessage());
                        }

                    }
                    data.add(list);
                }
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
            return data;
        }

        return data;
    }

    public void sqlQuery(SQLDataInterface sqlDataInterface, String tableName, String keyword, int index, int count) {
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        this.sqlDataInterface = sqlDataInterface;
        this.tableName = tableName;
        this.keyword = keyword;
        this.index = index;
        this.count = count;
        new RxJavaUtil<>(this, this);
    }

    @Override
    public void call(Subscriber<? super List<List<String>>> subscriber) {
        LogUtils.i("sqlquery","开始查询2");
        try {
            subscriber.onNext(query(tableName,keyword,index,count));
        }catch (Exception e){
            LogUtils.i("sqlquery",""+e.getMessage());
            subscriber.onNext(new ArrayList<List<String>>());
        }

    }

    @Override
    public void onNext(List<List<String>> list) {
        if (sqlDataInterface != null) {
            sqlDataInterface.getSQLData(list,tableName);
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }


}
