package com.example.jf.testsqlist.UI;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jf.testsqlist.DBUtil.DBHelper;
import com.example.jf.testsqlist.R;

public class MainActivity extends AppCompatActivity {
    private Button insert, search, update, delete;
    private EditText chinese, english, word;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private ListView date_view;
    final String TABLE_NAME = "tab";
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        dbHelper = new DBHelper(this, "testDemo.db", null, 1);
        insert = (Button) findViewById(R.id.insert_button);
        search = (Button) findViewById(R.id.search_button);
        update = (Button) findViewById(R.id.update_button);
        delete = (Button) findViewById(R.id.delete_button);
        chinese = (EditText) findViewById(R.id.wordC);
        english = (EditText) findViewById(R.id.wordE);
        word = (EditText) findViewById(R.id.search_edit);
        date_view = (ListView) findViewById(R.id.list_item);
        db = dbHelper.getWritableDatabase();
        insert.setOnClickListener((V) -> {
            ContentValues values = new ContentValues();
            values.put("chi", chinese.getText().toString());
            values.put("eng", english.getText().toString());
            String showToast;
            if(id != -1){
                db.update(TABLE_NAME,values,"id = ?", new String []{id + ""});
                showToast = "update success";
            }else{
                db.insert(TABLE_NAME, null, values);
                showToast = "insert success";
            }
            english.setHint(english.getText().toString());
            chinese.setHint(chinese.getText().toString());
            Toast.makeText(this, showToast, Toast.LENGTH_SHORT).show();
        });
        search.setOnClickListener((V) -> {
            Cursor cursor = db.query(TABLE_NAME,null,
                    "chi like ? or eng like ?",
                    new String[]{"%" + word.getText().toString() + "%",
                            "%" + word.getText().toString() + "%"},
                    null,null,null);
            if (!cursor.moveToNext()) {
                Toast.makeText(this, "查无此词", Toast.LENGTH_SHORT).show();
            }
            while (cursor.moveToNext()){
                Toast.makeText(this, cursor.getString(1) + ";"+cursor.getString(2), Toast.LENGTH_SHORT).show();
            }
        });
        update.setOnClickListener((V) -> {
            Cursor cursor = db.query(TABLE_NAME,null,
                    "chi like ? or eng like ?",
                    new String[]{"%" + word.getText().toString() + "%",
                            "%" + word.getText().toString() + "%"},
                    null,null,null);
            cursor.moveToFirst();
            chinese.setText(cursor.getString(1));
            english.setText(cursor.getString(2));
            id = cursor.getInt(0);
        });
        delete.setOnClickListener((V) -> {
            if (word.getText().toString().equals(chinese.getText().toString())) {
                db.delete(TABLE_NAME,"chi like ? or eng like ?",
                        new String[]{"%" + word.getText().toString() + "%",
                                "%" + word.getText().toString() + "%",});
                Toast.makeText(this, "该条数据已删除", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}
