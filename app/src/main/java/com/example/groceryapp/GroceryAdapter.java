package com.example.groceryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {
    private Context myContext;
    private Cursor myCursor;

    public GroceryAdapter(Context context, Cursor cursor) {
        myContext = context;
        myCursor = cursor;
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView countText;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText =  itemView.findViewById(R.id.name_item);
            countText = itemView.findViewById(R.id.amnt_item);
        }
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(R.layout.grocery_item, parent, false);

        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        if (!myCursor.moveToPosition(position)) {
            return;
        }

        String name = myCursor.getString(myCursor.getColumnIndex(GroceryContract.GroceryEntry.NAME_COLUMN));
        int amount = myCursor.getInt(myCursor.getColumnIndex(GroceryContract.GroceryEntry.AMOUNT_COLUMN));
        long id = myCursor.getLong(myCursor.getColumnIndex(GroceryContract.GroceryEntry._ID));

        holder.itemView.setTag(id);
        holder.nameText.setText(name);
        holder.countText.setText(String.valueOf(amount));

    }

    @Override
    public int getItemCount() {
        return myCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (myCursor!=null) {
            myCursor.close();
        }
        System.out.println(newCursor.getCount());
        myCursor = newCursor;

        if (newCursor!=null) {
            notifyDataSetChanged();
        }
    }
}
