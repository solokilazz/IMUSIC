package com.example.da1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int action = intent.getIntExtra("action_music",0);
        Intent intentService = new Intent(context,MyService.class);
        intentService.putExtra("action_music_service",action);
        context.startService(intentService);
    }
}
