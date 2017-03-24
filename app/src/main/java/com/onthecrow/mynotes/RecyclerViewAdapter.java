package com.onthecrow.mynotes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<SolventViewHolders> {

    private List<Note> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Note> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.solvent_list, null);
        SolventViewHolders rcv = new SolventViewHolders(layoutView, itemList);
        return rcv;
    }

    @Override
    public void onBindViewHolder(SolventViewHolders holder, int position) {
        holder.getTitle().setText(itemList.get(position).getTitle());
        holder.getDescription().setText(itemList.get(position).getDescription());
        holder.setCurrentNote(itemList.get(position));
        holder.setContext(context);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}