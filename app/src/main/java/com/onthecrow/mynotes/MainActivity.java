package com.onthecrow.mynotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static volatile MainActivity instance;
    private RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        this.setTitle("Мои заметки");
        getDataFromDb();
    }

    public void createNote(View view) {
        Intent intent = new Intent(this, CreateNote.class);
        startActivity(intent);
    }

    public void getDataFromDb() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(sglm);

        DbHelper dbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String[] projection = {
                dbHelper.getTitle(),
                dbHelper.getDescription(),
                dbHelper.getDate()
        };

        String sortOrder = dbHelper.getDate() + " DESC";

        Cursor c = db.query(
                dbHelper.getTableName(),projection,null,null,null,null,sortOrder);

        if (c.getCount() == 0)
            return;

        c.moveToFirst();

        List<Note> itemList = new ArrayList<>();

        while(!c.isClosed()){
            Note note = new Note(
                    c.getString(c.getColumnIndex(dbHelper.getTitle())),
                    c.getString(c.getColumnIndex(dbHelper.getDescription())),
                    c.getString(c.getColumnIndex(dbHelper.getDate()))
            );
            itemList.add(note);
            if(c.isLast())
                c.close();
            else
                c.move(1);
        }

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, itemList);
        recyclerView.setAdapter(rcAdapter);
        this.adapter = rcAdapter;
        db.close();
    }

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected void onResume() {
        getDataFromDb();
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    public static MainActivity getInstance() {
        MainActivity localInstance = instance;
        if (localInstance == null) {
            synchronized (MainActivity.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MainActivity();
                }
            }
        }
        return localInstance;
    }
}
