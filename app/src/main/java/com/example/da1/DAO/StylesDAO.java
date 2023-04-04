package com.example.da1.DAO;

import static com.example.da1.Utilities.Constants.TABLE_SINGERS;
import static com.example.da1.Utilities.Constants.TABLE_STYLES;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1.Database.SQLiteManager;
import com.example.da1.Models.Singer;
import com.example.da1.Models.Style;

import java.util.ArrayList;

public class StylesDAO implements IStyles{

    private SQLiteDatabase db;
    private SQLiteManager mdb;

    public StylesDAO(Context context) {
        mdb = new SQLiteManager(context);
        db = mdb.getWritableDatabase();
    }

    @Override
    public Style get(String id) {
        return null;
    }

    @Override
    public ArrayList<Style> getAll() {
        db = mdb.getReadableDatabase();
        ArrayList<Style> list = new ArrayList<>();
        Cursor c = db.rawQuery("Select * from " + TABLE_STYLES,null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            int id = c.getInt(0);
            String name = c.getString(1);
            int image = c.getInt(2);
            Style style = new Style(id,name,image);
            list.add(style);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    @Override
    public boolean insert(Style style) {
        ContentValues values = new ContentValues();
        values.put("name",style.getName());
        values.put("image",style.getImage());
        long ok = db.insert(TABLE_STYLES,null,values);
        return ok==1;
    }

    @Override
    public boolean update(Style style) {
        return false;
    }


    @Override
    public boolean delete(String id) {
        return false;
    }
}
