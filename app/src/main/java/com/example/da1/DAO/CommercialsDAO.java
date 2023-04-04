package com.example.da1.DAO;

import static com.example.da1.Utilities.Constants.TABLE_SINGERS;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1.Database.SQLiteManager;
import com.example.da1.Models.Commercial;
import com.example.da1.Models.Singer;

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
    public boolean insert(Commercial commercial) {
        ContentValues values = new ContentValues();
        values.put("title",commercial.getTitle());
        values.put("image",commercial.getImage());
        values.put("songId",commercial.getSongId());
        long ok = db.insert(TABLE_SINGERS,null,values);
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
