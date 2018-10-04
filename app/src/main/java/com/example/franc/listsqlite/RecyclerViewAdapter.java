package com.example.franc.listsqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private MainActivity context;
    private List<Item> lstItem;

    public RecyclerViewAdapter(MainActivity context, List<Item> lstItem) {
        this.context = context;
        this.lstItem = lstItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Item item = lstItem.get(position);
        holder.txtName.setText(item.name);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.dialogDelete(item.id);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.dialogEdit(item.name,item.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstItem.size();
    }
}
