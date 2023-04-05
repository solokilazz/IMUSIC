package com.example.da1;

import static com.example.da1.NotifiApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.da1.Activities.PlayActivity;
import com.example.da1.DAO.SingersDAO;
import com.example.da1.Models.Song;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class MyService extends Service {

    private MediaPlayer mediaPlayer;
    private TimeSongViewModel timeSongViewModel;
    private ArrayList<Song> listSong = new ArrayList<>();
    private int position = 0;
    private boolean shuffle = false;
    private boolean repeat = false;
    private boolean next = false;
    private int temp=0;

    public static boolean isPlaying = false;
    public static final int ACTION_PAUSE = 1;
    public static final int ACTION_RESUME = 2;
    public static final int ACTION_NEXT = 3;
    public static final int ACTION_PREV = 4;
    public static final int ACTION_REPEAT = 5;
    public static final int ACTION_SHUFFLE = 6;
    public static final int ACTION_START = 7;

    @Override
    public void onCreate() {
        super.onCreate();
//        Toast.makeText(this,"serviceOnCreate",Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle!=null && temp==0){
            listSong = (ArrayList<Song>) bundle.getSerializable("list_song");
            position = (int) bundle.getSerializable("position");
//            Toast.makeText(this,"runserviceOnService",Toast.LENGTH_LONG).show();
            startMusic();
            temp++;
        }

        int action =  intent.getIntExtra("action_music_service",0);
        if (action == MyService.ACTION_START){
            position = intent.getIntExtra("position",position);
        }
        handleActionMusic(action);

        if (intent.hasExtra("time_music_service")){
            int time =  intent.getIntExtra("time_music_service",0);
            updateTimeFromSeekbar(time);
        }
        return START_NOT_STICKY;
    }

    private void updateTimeFromSeekbar(int time) {
        mediaPlayer.seekTo(time);

    }




    private void handleActionMusic(int action){
        switch (action){
            case ACTION_PAUSE:
                pauseMusic();
                break;
            case ACTION_RESUME:
                resumeMusic();
                break;
            case ACTION_NEXT:
                nextMusic();
                break;
            case ACTION_PREV:
                prevMusic();
                break;
            case ACTION_REPEAT:
                repeatMusic();
                break;
            case ACTION_SHUFFLE:
                shuffleMusic();
                break;
            case ACTION_START:
                startMusic();
                break;
        }
    }

    private void shuffleMusic() {
        if(shuffle == false){
            if (repeat == true){
                repeat = false;
            }
            shuffle = true;
        }else {
            shuffle = false;
        }
    }

    private void repeatMusic() {
        if(repeat == false){
            if (shuffle == true){
                shuffle = false;
            }
            repeat = true;
        }else {
            repeat = false;
        }
    }

    private void prevMusic() {
        if (listSong.size()>0){
            if (mediaPlayer.isPlaying() || mediaPlayer!=null){
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (position < listSong.size()){
                position--;
                if (repeat == true){
                    position++;
                }
                if (shuffle == true){
                    Random random = new Random();
                    int index = random.nextInt(listSong.size());
                    while (index == position+1){
                        index = random.nextInt(listSong.size());
                    }
                    position = index;
                }
                if (position < 0){
                    position = listSong.size()-1;
                }
                new playMusic().execute(listSong.get(position).getLink());
            }
            isPlaying = true;
            sendNotificationMedia(listSong,position);
            sendActionToPlayActivity(ACTION_PREV);
        }
    }

    private void nextMusic() {
        if (listSong.size()>0){
            if (mediaPlayer.isPlaying() || mediaPlayer!=null){
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (position < listSong.size()){
                position++;
                if (repeat == true){
                    position--;
                }
                if (shuffle == true){
                    Random random = new Random();
                    int index = random.nextInt(listSong.size());
                    while (index == position-1){
                        index = random.nextInt(listSong.size());
                    }
                    position = index;
                }
                if (position>listSong.size()-1){
                    position = 0;
                }
                new playMusic().execute(listSong.get(position).getLink());
            }
            isPlaying = true;
            sendNotificationMedia(listSong,position);
            sendActionToPlayActivity(ACTION_NEXT);
        }
    }

    private void pauseMusic(){
        if (mediaPlayer!=null && isPlaying){
            mediaPlayer.pause();
            isPlaying = false;
            sendNotificationMedia(listSong,position);
            sendActionToPlayActivity(ACTION_PAUSE);
        }
    }

    private void resumeMusic(){
        if (mediaPlayer!=null && !isPlaying){
            mediaPlayer.start();
            isPlaying = true;
            sendNotificationMedia(listSong,position);
            sendActionToPlayActivity(ACTION_RESUME);
        }
    }

    private void startMusic(){
        if (listSong!=null){
            if (isPlaying && mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            new playMusic().execute(listSong.get(position).getLink());
            isPlaying = true;
            sendNotificationMedia(listSong,position);
            sendActionToPlayActivity(ACTION_START);
        }
    }



    private void sendNotificationMedia(ArrayList<Song> listSong, int position) {
        Song song = listSong.get(position);
        String nameSinger = (new SingersDAO(this)).get(listSong.get(position).getSingerId()).getName();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),song.getImage());

        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this,"tag");

        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, PlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ListSong",listSong);
        bundle.putSerializable("Song",listSong.get(position));
        bundle.putSerializable("isplaying",isPlaying);
        resultIntent.putExtra("bundle",bundle);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(1,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        //PendingIntent
        PendingIntent pausePendingIntent = getPendingIntent(this,ACTION_PAUSE);
        PendingIntent resumePendingIntent = getPendingIntent(this,ACTION_RESUME);
        PendingIntent nextPendingIntent = getPendingIntent(this,ACTION_NEXT);
        PendingIntent prevPendingIntent = getPendingIntent(this,ACTION_PREV);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                // Show controls on lock screen even when user hides sensitive content.
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.iconfloatingactionbutton)
                // Apply the media style template
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0,1,2 /* #1: pause button */)
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setContentTitle(song.getName())
                .setContentText(nameSinger)
                .setContentIntent(resultPendingIntent)
                .setLargeIcon(bitmap);
        if (isPlaying){
            notificationBuilder
                    // Add media control buttons that invoke intents in your media service
                    .addAction(R.drawable.ic_prev, "Previous", prevPendingIntent) // #0 prevPendingIntent
                    .addAction(R.drawable.ic_pause, "Pause", pausePendingIntent)  // #1 pausePendingIntent
                    .addAction(R.drawable.ic_next, "Next", nextPendingIntent);   // #2 nextPendingIntent
        }else {
            notificationBuilder
                    // Add media control buttons that invoke intents in your media service
                    .addAction(R.drawable.ic_prev, "Previous", prevPendingIntent) // #0 prevPendingIntent
                    .addAction(R.drawable.ic_play, "Resume", resumePendingIntent)  // #1 resumePendingIntent
                    .addAction(R.drawable.ic_next, "Next", nextPendingIntent);   // #2 nextPendingIntent
        }

        Notification notification = notificationBuilder.build();

//        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
//        notificationManagerCompat.notify(1,notification);
        startForeground(1,notification);

    }

    private PendingIntent getPendingIntent(Context context, int action) {
        Intent intent = new Intent(this,MyReceiver.class);
        intent.putExtra("action_music",action);
        PendingIntent notifyPendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            notifyPendingIntent = PendingIntent.getBroadcast(
                    context.getApplicationContext(),
                    action,
                    intent,
                    PendingIntent.FLAG_MUTABLE
            );
        } else {
            notifyPendingIntent = PendingIntent.getBroadcast(
                    context.getApplicationContext(),
                    action,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
        }

        return notifyPendingIntent;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
//        Toast.makeText(this,"serviceDestroy",Toast.LENGTH_LONG).show();
    }

    class playMusic extends AsyncTask<Integer,Void,Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            return integers[0];
        }

        @Override
        protected void onPostExecute(Integer song) {
            super.onPostExecute(song);
            mediaPlayer = new MediaPlayer();
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });
            mediaPlayer = MediaPlayer.create(getApplicationContext(),song);
//            mediaPlayer.prepare();

            mediaPlayer.start();
            updateTime();
        }
    }



    private void updateTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    sendTimeToPlayActivity(mediaPlayer.getCurrentPosition(),mediaPlayer.getDuration());
                    handler.postDelayed(this,300);
                    mediaPlayer.setOnCompletionListener(mp -> {
                        next = true;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    });
                }
            }
        },300);

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next == true){
                    if (position < listSong.size()){
                        position++;
                        if (repeat == true){
                            position--;
                        }
                        if (shuffle == true){
                            Random random = new Random();
                            int index = random.nextInt(listSong.size());
                            while (index == position){
                                index = random.nextInt(listSong.size());
                            }
                            position = index;
                        }
                        if (position>listSong.size()-1){
                            position = 0;
                        }
                        new playMusic().execute(listSong.get(position).getLink());
                        isPlaying = true;
                        sendNotificationMedia(listSong,position);
                        sendActionToPlayActivity(ACTION_NEXT);
                    }
                    next = false;
                    handler1.removeCallbacks(this);
                }else {
                    handler1.postDelayed(this,1000);
                }
            }
        },1000);

    }

    private void sendActionToPlayActivity(int action){
        Intent intent = new Intent();
        intent.setAction("send_data_to_activity");
        intent.putExtra("index_song",position);
        intent.putExtra("status_player",isPlaying);
        intent.putExtra("action_music",action);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendTimeToPlayActivity(int time, int totalTime){
        Intent intent = new Intent();
        intent.setAction("send_time_to_activity");
        intent.putExtra("time_music",time);
        intent.putExtra("time_total_music",totalTime);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
