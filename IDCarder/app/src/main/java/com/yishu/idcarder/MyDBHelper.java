package com.yishu.idcarder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/1/22.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE users " +
                "(userId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username VARCHAR(20)," +
                "password VARCHAR(20)," +
                "phone_number VARCHAR(11)," +
                "money DOUBLE DEFAULT 0.0," +
                "tag VARCHAR(1)," +           // 0 for enterprise users, 1 for personal users, 2 for enterprise added member users
                "enterprise_name VARCHAR(100)," +
                "affiliate VARCHAR(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        db.execSQL("ALTER TABLE users ADD phone VARCHAR(20) NULL");
//        db.execSQL("ALTER TABLE users ADD money DOUBLE NULL");
        Log.e("======","SQLite has been upgraded....");
    }
}
