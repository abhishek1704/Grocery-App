package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase myDatabase;
    private GroceryAdapter myAdapter;
    private int amount=0;
    private EditText nameEdiText;
    private TextView amntTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GroceryDBHelper dbHelper = new GroceryDBHelper(this);
        myDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new GroceryAdapter(this, getAllItems());
        recyclerView.setAdapter(myAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());

            }
        }).attachToRecyclerView(recyclerView);

        nameEdiText = findViewById(R.id.nameTextView);
        amntTextView = findViewById(R.id.amntTextView);
        Button incBtn = findViewById(R.id.incBtn);
        Button decBtn = findViewById(R.id.decBtn);

        Button addBtn = findViewById(R.id.addBtn);

        incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });

        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        amount = 0;

    }

    private void increment() {
        amount++;
        amntTextView.setText(String.valueOf(amount));
    }

    private void decrement() {
        if (amount > 0) {
            amount--;
            amntTextView.setText(String.valueOf(amount));
        }
    }

    private void addItem() {
        if (nameEdiText.getText().toString().trim().length()==0 || amount == 0) {
            return;
        }

        String name = nameEdiText.getText().toString();
        ContentValues cv = new ContentValues();

        cv.put(GroceryContract.GroceryEntry.NAME_COLUMN, name);
        cv.put(GroceryContract.GroceryEntry.AMOUNT_COLUMN, amount);

        //myDatabase.insert(GroceryContract.GroceryEntry.TABLE_NAME,null,cv);
        long rowInserted = myDatabase.insert(GroceryContract.GroceryEntry.TABLE_NAME,null,cv);
        if(rowInserted != -1)
            Toast.makeText(this, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
        myAdapter.swapCursor(getAllItems());
        nameEdiText.getText().clear();
        amntTextView.setText(String.valueOf(0));
    }

    private void removeItem(long id) {
        myDatabase.delete(GroceryContract.GroceryEntry.TABLE_NAME,
                GroceryContract.GroceryEntry._ID + "=" + id, null);
        myAdapter.swapCursor(getAllItems());
    }
    private Cursor getAllItems() {
        return myDatabase.query(
                GroceryContract.GroceryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                GroceryContract.GroceryEntry.TIMESTAMP_COLUMN + " DESC"
        );
    }

}
