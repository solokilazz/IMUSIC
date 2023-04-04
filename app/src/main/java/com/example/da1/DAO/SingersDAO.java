package com.example.da1.DAO;

import static com.example.da1.Utilities.Constants.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1.Database.SQLiteManager;
import com.example.da1.Models.Singer;
import com.example.da1.Models.Song;

import java.util.ArrayList;
import java.util.List;

public class SingersDAO implements ISingers{

    private SQLiteDatabase db;
    private SQLiteManager mdb;

    public SingersDAO(Context context) {
        mdb = new SQLiteManager(context);
        db = mdb.getWritableDatabase();
    }

    @Override
    public Singer get(String id) {
        db = mdb.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_SINGERS +
                " Where "+ COLUMN_SINGER_ID + "=?",new String[]{id});
        c.moveToFirst();
        if(c != null){
            return new Singer(c.getString(1),c.getInt(2));
        }else
            return null;

    }

    @Override
    public ArrayList<Singer> getAll() {
        db = mdb.getReadableDatabase();
        ArrayList<Singer> list = new ArrayList<>();
        Cursor c = db.rawQuery("Select * from " + TABLE_SINGERS,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int id = c.getInt(0);
            String name = c.getString(1);
            int image = c.getInt(2);
            Singer singer = new Singer(id,name,image);
            list.add(singer);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    @Override
    public boolean insert(Singer singer) {
        ContentValues values = new ContentValues();
        values.put("name",singer.getName());
        values.put("image",singer.getImage());
        long ok = db.insert(TABLE_SINGERS,null,values);
        return ok==1;
    }

    @Override
    public boolean update(Singer singer) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
