package com.example.da1.DAO;

import static com.example.da1.Utilities.Constants.COLUMN_COMMERCIAL_IMAGE;
import static com.example.da1.Utilities.Constants.COLUMN_COMMERCIAL_SONG_ID;
import static com.example.da1.Utilities.Constants.COLUMN_COMMERCIAL_TITLE;
import static com.example.da1.Utilities.Constants.TABLE_COMMERCIALS;
import static com.example.da1.Utilities.Constants.TABLE_SINGERS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1.Database.SQLiteManager;
import com.example.da1.Models.Commercial;
import com.example.da1.Models.Singer;

import java.util.ArrayList;

public class CommercialsDAO implements ICommercials{

    private SQLiteDatabase db;
    private SQLiteManager mdb;

    public CommercialsDAO(Context context) {
        mdb = new SQLiteManager(context);
        db = mdb.getWritableDatabase();
    }

    @Override
    public Commercial get(String id) {
        return null;
    }

    @Override
    public ArrayList<Commercial> getAll() {
        db = mdb.getReadableDatabase();
        ArrayList<Commercial> list = new ArrayList<>();
        Cursor c = db.rawQuery("Select * from " + TABLE_COMMERCIALS,null);
        c.moveToFirst();
//      Commercial(int id, String title, int image, String songId)
        while (!c.isAfterLast()){
            int id = c.getInt(0);
            String title = c.getString(1);
            int image = c.getInt(2);
            String songId = c.getString(3);
            Commercial newCommercial = new Commercial(id,title,image,songId);
            list.add(newCommercial);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    @Override
    public boolean insert(Commercial commercial) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMERCIAL_TITLE,commercial.getTitle());
        values.put(COLUMN_COMMERCIAL_IMAGE,commercial.getImage());
        values.put(COLUMN_COMMERCIAL_SONG_ID,commercial.getSongId());
        long ok = db.insert(TABLE_COMMERCIALS,null,values);
        return ok==1;
    }

    @Override
    public boolean update(Commercial commercial) {
        return false;
    }


    @Override
    public boolean delete(String id) {
        return false;
    }
}
