package com.onthecrow.mynotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by the-crow on 22.03.2017.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final int dbVersion = 1;
    private static final String dbName = "MyNotes";
    private static final String tableName = "main";
    private static final String title = "title";
    private static final String description = "description";
    private static final String date = "date";
    private static final String createTable = "CREATE TABLE " + tableName + " (id INTEGER PRIMARY KEY, " +
            title + " TEXT, " +
            description + " TEXT, " +
            date + " TEXT)";

    public DbHelper(Context context){
        super(context, dbName, null, dbVersion);
    }

    public static String getTableName() {
        return tableName;
    }

    public static String getTitle() {
        return title;
    }

    public static String getDescription() {
        return description;
    }

    public static String getDate() {
        return date;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
