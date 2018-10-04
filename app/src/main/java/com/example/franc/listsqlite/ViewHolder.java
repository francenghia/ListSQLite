package com.example.franc.listsqlite;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView txtName;
    public ImageButton btnDelete, btnEdit ;
    public ViewHolder(View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txtName);
        btnDelete = itemView.findViewById(R.id.btnDelete);
        btnEdit = itemView.findViewById(R.id.btnEdit);
    }
}
