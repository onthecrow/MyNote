package com.onthecrow.mynotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static volatile MainActivity instance;
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
        List<Note> itemList = dbHelper.getNotes();

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, itemList);
        recyclerView.setAdapter(rcAdapter);
    }

    @Override
    protected void onResume() {
        getDataFromDb();
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
