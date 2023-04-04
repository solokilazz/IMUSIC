package com.example.da1.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.da1.Adapter.SongsAdapter;
import com.example.da1.DAO.SongsDAO;
import com.example.da1.MainActivity;
import com.example.da1.Models.Song;
import com.example.da1.R;

import java.util.ArrayList;

public class FilterListActivity extends AppCompatActivity {

    private RecyclerView rvListSong;
    private ArrayList<Song> list = new ArrayList<>();
    private SongsDAO songsDAO;
    private SongsAdapter songsAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_list);

        rvListSong = findViewById(R.id.listSongFilter);
        toolbar = findViewById(R.id.toolBarFilterActivity);

        songsDAO = new SongsDAO(this);

        //toolbar
        setToolBar();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListSong.setLayoutManager(linearLayoutManager);

        fillList();
    }

    private void setToolBar() {
        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        Intent intent = getIntent();
        if (intent.hasExtra("name_singer")){
            getSupportActionBar().setTitle(intent.getStringExtra("name_singer"));
        }else if (intent.hasExtra("name_style")){
            getSupportActionBar().setTitle(intent.getStringExtra("name_style"));
        }
    }


    public void fillList(){
        Intent intent = getIntent();
        if (intent.hasExtra("id_style")){
            String idStyle = intent.getStringExtra("id_style");
            list = songsDAO.getByStyleId(idStyle);
        }else if (intent.hasExtra("id_singer")){
            String idSinger = intent.getStringExtra("id_singer");
            list = songsDAO.getBySingerId(idSinger);
        }
        songsAdapter = new SongsAdapter(this,list);
        rvListSong.setAdapter(songsAdapter);
    }
}