package com.example.jf.testsqlist.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL = "create table tab(" + "id integer primary key autoincrement" + ",chi text" + ",eng text)";
        db.execSQL(SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insert(SQLiteDatabase db,String table,String ch,String en){
        ContentValues values = new ContentValues();
        values.put("chi", ch);
        values.put("eng", en);
        db.insert(table, null, values);
    }
}
