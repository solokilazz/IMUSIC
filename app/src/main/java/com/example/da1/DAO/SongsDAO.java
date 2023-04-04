package com.example.da1.DAO;

import static android.content.ContentValues.TAG;
import static com.example.da1.Utilities.Constants.COLUMN_SONG_COUNT;
import static com.example.da1.Utilities.Constants.COLUMN_SONG_IMAGE;
import static com.example.da1.Utilities.Constants.COLUMN_SONG_LINK;
import static com.example.da1.Utilities.Constants.COLUMN_SONG_NAME;
import static com.example.da1.Utilities.Constants.COLUMN_SONG_SINGER_ID;
import static com.example.da1.Utilities.Constants.COLUMN_SONG_STATUS;
import static com.example.da1.Utilities.Constants.COLUMN_SONG_STYLE_ID;
import static com.example.da1.Utilities.Constants.TABLE_SONGS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.da1.Database.SQLiteManager;
import com.example.da1.Models.Song;

import java.util.ArrayList;
import java.util.List;

public class SongsDAO implements ISongs{

    private SQLiteDatabase db;
    private SQLiteManager mdb;

    public SongsDAO(Context context) {
        mdb = new SQLiteManager(context);
        db = mdb.getWritableDatabase();
    }

    @Override
    public ArrayList<Song> getBySingerId(String id) {
        db = mdb.getReadableDatabase();
        ArrayList<Song> list = new ArrayList<>();
        Cursor c = db.rawQuery("Select * from "+TABLE_SONGS+ " where "
                +COLUMN_SONG_SINGER_ID +" = ? ",new String[]{id});
        c.moveToFirst();
        while (!c.isAfterLast()){
            int _id = c.getInt(0);
            String name = c.getString(1);
            int image = c.getInt(2);
            String link = c.getString(3);
            String styleId = c.getString(4);
            String singerId = c.getString(5);
            int count = c.getInt(6);
            boolean isStatus;
            if (c.getInt(7)==1){
                isStatus = true;
            }else
                isStatus = false;
            Song newSong = new Song(_id,name,image,Integer.parseInt(link),styleId,singerId,count,isStatus);
            list.add(newSong);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    @Override
    public ArrayList<Song> getByStyleId(String id) {
        db = mdb.getReadableDatabase();
        ArrayList<Song> list = new ArrayList<>();
        Cursor c = db.rawQuery("Select * from "+TABLE_SONGS+ " where "
                +COLUMN_SONG_STYLE_ID +" = ? ",new String[]{id});
        c.moveToFirst();
        while (!c.isAfterLast()){
            int _id = c.getInt(0);
            String name = c.getString(1);
            int image = c.getInt(2);
            String link = c.getString(3);
            String styleId = c.getString(4);
            String singerId = c.getString(5);
            int count = c.getInt(6);
            boolean isStatus;
            if (c.getInt(7)==1){
                isStatus = true;
            }else
                isStatus = false;
            Song newSong = new Song(_id,name,image,Integer.parseInt(link),styleId,singerId,count,isStatus);
            list.add(newSong);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    @Override
    public ArrayList<Song> getAll() {
        db = mdb.getReadableDatabase();
        ArrayList<Song> list = new ArrayList<>();
        Cursor c = db.rawQuery("Select * from " + TABLE_SONGS,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int _id = c.getInt(0);
            String name = c.getString(1);
            int image = c.getInt(2);
            String link = c.getString(3);
            String styleId = c.getString(4);
            String singerId = c.getString(5);
            int count = c.getInt(6);
            boolean isStatus;
            if (c.getInt(7)==1){
                isStatus = true;
            }else
                isStatus = false;
            Song newSong = new Song(_id,name,image,Integer.parseInt(link),styleId,singerId,count,isStatus);
            list.add(newSong);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    @Override
    public Song get(String id) {
        return null;
    }

    @Override
    public boolean insert(Song song) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG_NAME,song.getName());
        values.put(COLUMN_SONG_IMAGE,song.getImage());
        values.put(COLUMN_SONG_LINK,song.getLink());
        values.put(COLUMN_SONG_STYLE_ID,song.getStyleId());
        values.put(COLUMN_SONG_SINGER_ID,song.getSingerId());
        values.put(COLUMN_SONG_COUNT,song.getCount());
        if (song.isStatus()){
            values.put(COLUMN_SONG_STATUS,1);
        }else
            values.put(COLUMN_SONG_STATUS,2);
        long ok = db.insert(TABLE_SONGS,null,values);
        return ok==1;
    }

    @Override
    public boolean update(Song song) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

}
