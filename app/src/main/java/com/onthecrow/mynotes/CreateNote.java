package com.onthecrow.mynotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNote extends AppCompatActivity {
    private boolean isChanging;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        Intent intent = getIntent();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TextView dateView = (TextView)findViewById(R.id.dateView);
        dateView.setText(df.format(c.getTime()));

        String title = intent.getStringExtra("Title");
        String description = intent.getStringExtra("Description");
        String date = intent.getStringExtra("Date");
        if(intent.getStringExtra("IsChanging")!=null)
            isChanging = true;

        EditText editTitle = (EditText)findViewById(R.id.title);
        EditText editDescription = (EditText)findViewById(R.id.description);
        TextView viewDate = (TextView)findViewById(R.id.dateView);

        editTitle.setText(title);
        editDescription.setText(description);
        if(date != null){
            isChanging = true;
            this.setTitle("Изменение заметки");
            viewDate.setText(date);
        }
        else{
            this.setTitle("Создание заметки");
        }
    }
    public void saveNote(View view){
        DbHelper dbHelper = new DbHelper(CreateNote.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        EditText title = (EditText)findViewById(R.id.title);
        EditText description = (EditText)findViewById(R.id.description);
        TextView date = (TextView)findViewById(R.id.dateView);

        Note note = new Note(
                title.getText().toString(),
                description.getText().toString(),
                date.getText().toString());
        if(isChanging){
            dbHelper.updateNote(note);
            //MainActivity.getInstance().getDataFromDb();
        } else {
            dbHelper.insertNote(note);
        }
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
    }
}
