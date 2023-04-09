package com.example.da1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LoadImageInternet extends AsyncTask<String,Void, Bitmap> {

    private Context context;
    private ImageView imageView;
    public Drawable drawable;
    public Toolbar toolbar;


    public LoadImageInternet(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    public LoadImageInternet(Context context, Toolbar toolbar) {
        this.context = context;
        this.toolbar = toolbar;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap myBitmap = null;
        try {
            URL url = new URL(strings[0]);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (imageView==null){
            drawable = new BitmapDrawable(context.getResources(),bitmap);
            toolbar.setNavigationIcon(drawable);
        }else {
            imageView.setImageBitmap(bitmap);
        }

    }

}
