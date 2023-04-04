package com.example.da1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1.Adapter.ViewPagerPlayMusicAdapter;
import com.example.da1.Fragments.DiskFragment;
import com.example.da1.Fragments.PlayListFragment;
import com.example.da1.ItemViewModel;
import com.example.da1.MainActivity;
import com.example.da1.Models.Song;
import com.example.da1.MyService;
import com.example.da1.R;
import com.example.da1.TimeSongViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager2 viewPager2;
    ViewPagerPlayMusicAdapter viewPagerPlayMusicAdapter;
    TextView tvTimeSong, tvTotalTimeSong;
    SeekBar seekBarSong;
    ImageButton imbtnShuffle, imbtnPrev, imbtnPlay, imbtnNext, imbtnRepeat;
    DiskFragment diskFragment;
    PlayListFragment playListFragment;
    private int position = 0;
    private boolean repeat = false;
    private boolean shuffle = false;
    private boolean next = false;
    private boolean isPlaying;
    private ItemViewModel viewModel;
    private int currentPosition;
    private int timeDuration;

    public static ArrayList<Song> listSong = new ArrayList<>();

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null){
                return;
            }
            isPlaying = intent.getBooleanExtra("status_player",false);
            position = intent.getIntExtra("index_song",0);
            int actionMusic = intent.getIntExtra("action_music",0);
            handlePlayMp3(actionMusic);
        }
    };

    private BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null){
                return;
            }
            currentPosition = intent.getIntExtra("time_music",0);
            timeDuration = intent.getIntExtra("time_total_music",0);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //Anh xa
        toolbar = findViewById(R.id.toolbar );
        viewPager2 = findViewById(R.id.viewPager2 );
        tvTimeSong = findViewById(R.id.tvTimeSong );
        tvTotalTimeSong = findViewById(R.id.tvTotalTimeSong );
        seekBarSong = findViewById(R.id.seekbarSong );
        imbtnShuffle = findViewById(R.id.imbtnShuffle );
        imbtnPrev = findViewById(R.id.imbtnPrev );
        imbtnPlay = findViewById(R.id.imbtnPlay );
        imbtnNext = findViewById(R.id.imbtnNext );
        imbtnRepeat = findViewById(R.id.imbtnRepeat );

        setToolBar();


        //dang ly broadcastReceiver
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter("send_data_to_activity"));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver2,
                new IntentFilter("send_time_to_activity"));

        playListFragment = new PlayListFragment();
        diskFragment = new DiskFragment();
        viewPagerPlayMusicAdapter = new ViewPagerPlayMusicAdapter(
                PlayActivity.this,playListFragment,diskFragment);
        viewPager2.setAdapter(viewPagerPlayMusicAdapter);
        viewPager2.setCurrentItem(1);

        //play nhac
        diskFragment = (DiskFragment) viewPagerPlayMusicAdapter.createFragment(1);
        getDataFromIntent();
        if (listSong.size()>0){
            getSupportActionBar().setTitle(listSong.get(position).getName());
//            new playMusic().execute(listSong.get(position).getLink());
//            imbtnPlay.setImageResource(R.drawable.iconpause);
        }

        //Service
        runService();

        //start music
        startMusic();

        //truyen du lieu tu fragment (live data)(chon bai hat tren list)
        clickOnList();

        updateTimeForSeekbar();

        //su kien click may nghe nhac
        eventClick1();
    }



    private void runService() {
        Intent intent = new Intent(this,MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list_song",listSong);
        bundle.putSerializable("position",position);
        intent.putExtra("bundle",bundle);
        startService(intent);
    }

    private void handlePlayMp3(int actionMusic) {
        switch (actionMusic){
            case MyService.ACTION_PAUSE:
                pauseOrResumeMusic();
                break;
            case MyService.ACTION_RESUME:
                pauseOrResumeMusic();
                break;
            case MyService.ACTION_NEXT:
                nextMusic();
                break;
            case MyService.ACTION_PREV:
                prevMusic();
                break;
            case MyService.ACTION_REPEAT:
                break;
            case MyService.ACTION_SHUFFLE:
                break;
        }
    }

    private void prevMusic() {
        diskFragment.playMusic(listSong.get(position).getImage());
        diskFragment.run();
        getSupportActionBar().setTitle(listSong.get(position).getName());
        imbtnPlay.setImageResource(R.drawable.iconpause);
    }

    private void nextMusic() {
        diskFragment.playMusic(listSong.get(position).getImage());
        diskFragment.run();
        getSupportActionBar().setTitle(listSong.get(position).getName());
        imbtnPlay.setImageResource(R.drawable.iconpause);

    }


    private void pauseOrResumeMusic(){
        if (isPlaying){
            imbtnPlay.setImageResource(R.drawable.iconpause);
            if (viewPagerPlayMusicAdapter.createFragment(1)!=null) {
                diskFragment.run();
            }
        }else{
            imbtnPlay.setImageResource(R.drawable.iconplay);
            if (viewPagerPlayMusicAdapter.createFragment(1)!=null) {
                diskFragment.pause();
            }
        }

    }

    private void startMusic() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewPagerPlayMusicAdapter.createFragment(1)!=null){
                    if (listSong.size()>0){
                        diskFragment.playMusic(listSong.get(position).getImage());
                        if (!isPlaying){
                            diskFragment.pause();
                        }
                        handler.removeCallbacks(this);
                    }else {
                        handler.postDelayed(this,300);
                    }
                }

            }
        },500);
    }

    private void eventClick1(){
        seekBarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sendTimeToService(seekBar.getProgress());
            }
        });
        imbtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying){
                    sendActionToService(MyService.ACTION_PAUSE);
                }else {
                    sendActionToService(MyService.ACTION_RESUME);
                }
            }
        });

        imbtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imbtnPlay.setImageResource(R.drawable.iconpause);
                diskFragment.run();
                sendActionToService(MyService.ACTION_NEXT);
            }
        });

        imbtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imbtnPlay.setImageResource(R.drawable.iconpause);
                diskFragment.run();
                sendActionToService(MyService.ACTION_PREV);
            }
        });

        imbtnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shuffle == false){
                    if (repeat == true){
                        repeat = false;
                        imbtnRepeat.setImageResource(R.drawable.iconrepeat);
                    }
                    imbtnShuffle.setImageResource(R.drawable.iconshuffled);
                    shuffle = true;
                }else {
                    imbtnShuffle.setImageResource(R.drawable.iconsuffle);
                    shuffle = false;
                }
                sendActionToService(MyService.ACTION_SHUFFLE);
            }
        });

        imbtnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repeat == false){
                    if (shuffle == true){
                        shuffle = false;
                        imbtnShuffle.setImageResource(R.drawable.iconsuffle);
                    }
                    imbtnRepeat.setImageResource(R.drawable.iconsyned);
                    repeat = true;
                }else {
                    imbtnRepeat.setImageResource(R.drawable.iconrepeat);
                    repeat = false;
                }
                sendActionToService(MyService.ACTION_REPEAT);
            }
        });
    }
    private void clickOnList() {
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getSelectedItem().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                position = integer;
                if (listSong.size()>0) {
                    sendActionToService(MyService.ACTION_START);
                    diskFragment.playMusic(listSong.get(position).getImage());
                    diskFragment.run();
                    getSupportActionBar().setTitle(listSong.get(position).getName());
                    imbtnPlay.setImageResource(R.drawable.iconpause);
                }
            }
        });
    }



    private void getDataFromIntent() {
        //lay danh sach bai hat
        Intent intent = getIntent();
        listSong.clear();
        if (intent!= null){
            if (intent.hasExtra("bundle")){
                Bundle bundle = intent.getBundleExtra("bundle");
                isPlaying = (boolean) bundle.getSerializable("isplaying");
                if (isPlaying){
                    imbtnPlay.setImageResource(R.drawable.iconpause);
                }else{
                    imbtnPlay.setImageResource(R.drawable.iconplay);
                }
                Song songChose = (Song) bundle.getSerializable("Song");
                ArrayList<Song> list = (ArrayList<Song>) bundle.getSerializable("ListSong");
                listSong = list;
                for (int i = 0;i<list.size();i++){
                    if (list.get(i).get_id() == songChose.get_id()){
                        position = i;
                        break;
                    }
                }
//                Toast.makeText(this,songChose.get_id()+"",Toast.LENGTH_LONG).show();
            }
        }

    }

    private void setToolBar() {
        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(intent);
//                listSong.clear();
//                finish();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Play Music");
    }


    private void updateTimeForSeekbar() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                seekBarSong.setMax(timeDuration);
                tvTotalTimeSong.setText(simpleDateFormat.format(timeDuration));
                seekBarSong.setProgress(currentPosition);
                tvTimeSong.setText(simpleDateFormat.format(currentPosition));
                handler.postDelayed(this,300);
            }
        },300);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void sendActionToService(int action){
        Intent intent = new Intent(this,MyService.class);
        intent.putExtra("action_music_service",action);
        intent.putExtra("position",position);
        startService(intent);
    }
    private void sendTimeToService(int time){
        Intent intent = new Intent(this,MyService.class);
        intent.putExtra("time_music_service",time);
        startService(intent);
    }
}