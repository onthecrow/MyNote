package com.onthecrow.mynotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;


public class SolventViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView title;
    private TextView description;
    private Note currentNote;
    private Context context;

    public SolventViewHolders(final View itemView, final List<Note> itemList) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Управление заметкой");
                builder1.setCancelable(true);

                builder1.setNegativeButton(
                        "Изменить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(context,CreateNote.class);

                                intent.putExtra("Title",currentNote.getTitle());
                                intent.putExtra("Description",currentNote.getDescription());
                                intent.putExtra("Date",currentNote.getDate());
                                intent.putExtra("IsChanging", "change");

                                context.startActivity(intent);
                            }
                        });
                builder1.setPositiveButton(
                        "Удалить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(Note.deleteNote(currentNote) == 1);
                                    itemList.remove(currentNote);
                                MainActivity.getInstance().getAdapter().notifyDataSetChanged();
                                //MainActivity.getInstance().getDataFromDb();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;
            }
        });
        title = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.description);
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getDescription() {
        return description;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCurrentNote(Note currentNote) {
        this.currentNote = currentNote;
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(context,CreateNote.class);

        intent.putExtra("Title",currentNote.getTitle());
        intent.putExtra("Description",currentNote.getDescription());
        intent.putExtra("Date",currentNote.getDate());

        context.startActivity(intent);
    }
}