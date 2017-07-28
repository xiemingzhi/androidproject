package com.example.compositecalendarview;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by ming on 2017/7/25.
 */

public class Database extends SQLiteAssetHelper {
    private static final String DATABASE_NAMES = "reminder";
    private static final int DATABASE_VERSION = 3;
    public Database(Context context) {
        super(context, DATABASE_NAMES, null, DATABASE_VERSION);
    }
}
