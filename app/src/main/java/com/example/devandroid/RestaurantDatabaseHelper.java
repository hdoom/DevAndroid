package com.example.devandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RestaurantDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "restaurants.db";
    private static final int DATABASE_VERSION = 1;


    public static final String TABLE_RESTAURANT = "restaurant";
    public static final String COLUMN_NOM = "nom";
    public static final String COLUMN_DATE_HEURE = "date_heure";
    public static final String COLUMN_NOTE_DECORATION = "note_decoration";
    public static final String COLUMN_NOTE_NOURRITURE = "note_nourriture";
    public static final String COLUMN_NOTE_SERVICE = "note_service";
    public static final String COLUMN_DESCRIPTION = "description";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_RESTAURANT + " (" +
                    COLUMN_NOM + " TEXT, " +
                    COLUMN_DATE_HEURE + " TEXT, " +
                    COLUMN_NOTE_DECORATION + " REAL, " +
                    COLUMN_NOTE_NOURRITURE + " REAL, " +
                    COLUMN_NOTE_SERVICE + " REAL, " +
                    COLUMN_DESCRIPTION + " TEXT)";

    public RestaurantDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        onCreate(db);
    }
}

