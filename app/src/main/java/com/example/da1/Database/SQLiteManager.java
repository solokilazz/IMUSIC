package com.example.da1.Database;



import static com.example.da1.Utilities.Constants.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.da1.MainActivity;

public class SQLiteManager extends SQLiteOpenHelper {
    public SQLiteManager(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = " CREATE TABLE IF NOT EXISTS " + TABLE_USERS +" ("
                + COLUMN_USERNAME +" TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_STATUS + " BOOLEAN)";
        db.execSQL(query);

        query = " CREATE TABLE IF NOT EXISTS " + TABLE_SINGERS +"("
                + COLUMN_SINGER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SINGER_NAME + " TEXT,"
                + COLUMN_SINGER_IMAGE +" INTEGER)";
        db.execSQL(query);

        query = " CREATE TABLE IF NOT EXISTS " + TABLE_STYLES +"( "
                + COLUMN_STYLE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_STYLE_NAME + " TEXT,"
                + COLUMN_STYLE_IMAGE +" INTEGER)";
        db.execSQL(query);

        query = " CREATE TABLE IF NOT EXISTS " + TABLE_COMMERCIALS +"( "
                + COLUMN_COMMERCIAL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_COMMERCIAL_TITLE + " TEXT,"
                + COLUMN_COMMERCIAL_IMAGE + " INTEGER,"
                + COLUMN_COMMERCIAL_SONG_ID +" TEXT)";
        db.execSQL(query);

        query = " CREATE TABLE IF NOT EXISTS " + TABLE_SONGS +"( "
                + COLUMN_SONG_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SONG_NAME + " TEXT,"
                + COLUMN_SONG_IMAGE + " INTEGER,"
                + COLUMN_SONG_LINK + " TEXT,"
                + COLUMN_SONG_STYLE_ID + " TEXT,"
                + COLUMN_SONG_SINGER_ID + " TEXT,"
                + COLUMN_SONG_COUNT + " INTEGER,"
                + COLUMN_SONG_STATUS + " INTEGER)";
        db.execSQL(query);

        query = " INSERT INTO " + TABLE_USERS + " VALUES('admin','123',true)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TABLE_USERS);
        db.execSQL("Drop table if exists " + TABLE_SINGERS);
        db.execSQL("Drop table if exists " + TABLE_STYLES);
        db.execSQL("Drop table if exists " + TABLE_COMMERCIALS);
        db.execSQL("Drop table if exists " + TABLE_SONGS);
        onCreate(db);
    }
}
