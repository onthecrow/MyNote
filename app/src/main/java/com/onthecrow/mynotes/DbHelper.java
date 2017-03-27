package com.onthecrow.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    public List<Note> getNotes(){
        List<Note> itemList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String[] projection = {
                DbHelper.getTitle(),
                DbHelper.getDescription(),
                DbHelper.getDate()
        };

        String sortOrder = DbHelper.getDate() + " DESC";

        Cursor c = db.query(
                DbHelper.getTableName(),projection,null,null,null,null,sortOrder);
        if (c.getCount() != 0){
            c.moveToFirst();

            while(!c.isClosed()){
                Note note = new Note(
                        c.getString(c.getColumnIndex(DbHelper.getTitle())),
                        c.getString(c.getColumnIndex(DbHelper.getDescription())),
                        c.getString(c.getColumnIndex(DbHelper.getDate()))
                );
                itemList.add(note);
                if(c.isLast())
                    c.close();
                else
                    c.move(1);
            }
        }
        db.close();
        return itemList;
    }

    public int deleteNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(this.getTableName(),"date = " + DatabaseUtils.sqlEscapeString(note.getDate()),null);
        db.close();
        return result;
    }

    public int updateNote(Note note){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        cv.put(this.getTitle(), note.getTitle().toString());
        cv.put(this.getDescription(), note.getDescription().toString());
        int result = db.update(this.getTableName(), cv,
                "date = "
                        + DatabaseUtils.sqlEscapeString(note.getDate()),
                null);
        db.close();
        return result;
    }

    public long insertNote(Note note){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        cv.put(this.getTitle(), note.getTitle());
        cv.put(this.getDescription(), note.getDescription());
        cv.put(this.getDate(), formattedDate);
        long result = db.insert(this.getTableName(), null, cv);
        db.close();
        return result;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
