package com.tj.uimading.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/12.
 */
public class MySqliteHelper extends SQLiteOpenHelper {
    public MySqliteHelper(Context context) {
        super(context, "userdb.db", null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(id integer primary key ,name text,pwd text)");

        String sql = "insert into users values (0,0,0)"; //id 自增加
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
