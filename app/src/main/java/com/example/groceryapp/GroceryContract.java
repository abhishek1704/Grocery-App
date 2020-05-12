package com.example.groceryapp;

import android.provider.BaseColumns;

public class GroceryContract {

    private GroceryContract() {}

    public final class GroceryEntry implements BaseColumns {
        public static final String TABLE_NAME = "groceryList";
        public static final String NAME_COLUMN = "name";
        public static final String AMOUNT_COLUMN = "amount";
        public static final String TIMESTAMP_COLUMN = "timeStamp";

    }

}
