package com.ld.shushnet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

public class RingReceiver extends BroadcastReceiver {




    @Override
    public void onReceive(Context context, Intent intent) {

        AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        if(am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL){
            Log.i("**********RingReceiver","************************* N");

            MainActivity.SwitchRing.setChecked(false);

        }
        if(am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE){
            Log.i("**********RingReceiver","************************* V");

            MainActivity.SwitchRing.setChecked(true);


            Intent i = new Intent(context,  AlertDialogActivity.class);
            context.startActivity(i);
        }
        if(am.getRingerMode() == AudioManager.RINGER_MODE_SILENT){
            Log.i("**********RingReceiver","************************* S");


            MainActivity.SwitchRing.setChecked(true);


            Intent i = new Intent(context,  AlertDialogActivity.class);
            context.startActivity(i);
        }
    }




















};
