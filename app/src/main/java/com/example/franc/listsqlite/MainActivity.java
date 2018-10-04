package com.example.franc.listsqlite;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FeedReaderDBHelper database;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    private RecyclerView recyclerView;
    private List<Item> lstItem = new ArrayList<>();
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adapter = new RecyclerViewAdapter(this, lstItem);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        database = new FeedReaderDBHelper(this, DATABASE_NAME, null, DATABASE_VERSION);

        database.queryData("CREATE TABLE IF NOT EXISTS Movie(Id INTEGER PRIMARY KEY AUTOINCREMENT,Name VARCHAR(200))");

        //database.queryData("INSERT INTO Movie VALUES(null,'TOM AND JERRY')");

        GetData();

    }

    private void GetData() {
        Cursor data = database.getData("SELECT * FROM Movie");
        lstItem.clear();
        while (data.moveToNext()) {
            String name = data.getString(1);
            int id = data.getInt(0);
            lstItem.add(new Item(id, name));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_add:
                showDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("INSERT");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_insert, null);
        final EditText editMovie = view.findViewById(R.id.edtMoive);

        builder.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String movie = editMovie.getText().toString();
                if (TextUtils.isEmpty(movie)) {
                    Toast.makeText(MainActivity.this, "Please comeback to me i will be waiting for you !", Toast.LENGTH_SHORT).show();
                    return;
                }

                database.queryData("INSERT INTO Movie VALUES(null,'" + movie + "')");
                Toast.makeText(MainActivity.this, "Insert Successfully", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
                GetData();

            }
        });

        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setView(view);
        builder.show();
    }

    public void dialogEdit(String name ,final int id) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("EDIT");
        View viewEdit = LayoutInflater.from(this).inflate(R.layout.dialog_edit, null);
        final EditText editText = viewEdit.findViewById(R.id.edtMoive);
        editText.setText(name);

        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String movie = editText.getText().toString().trim();
                database.queryData("UPDATE Movie SET Name = '" + movie + "' WHERE Id = '" + id + "'");
                Toast.makeText(MainActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
                GetData();
            }
        });

        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setView(viewEdit);
        builder.show();
    }
    public void dialogDelete(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DELETE");
        builder.setMessage("Do you want delete the item !");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.queryData("DELETE FROM Movie WHERE Id = '"+id+"'");
                Toast.makeText(MainActivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
                GetData();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }
}
