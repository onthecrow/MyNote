package com.onthecrow.mynotes;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by the-crow on 23.03.2017.
 */
public class Note {
    private String title;
    private String description;
    private String date;

    public Note(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static int deleteNote(Note note){
        DbHelper dbHelper = new DbHelper(MainActivity.getInstance());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(dbHelper.getTableName(),"date = " + DatabaseUtils.sqlEscapeString(note.date),null);
    }
}
