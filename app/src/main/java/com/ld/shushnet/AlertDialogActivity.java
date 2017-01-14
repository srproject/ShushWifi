package com.ld.shushnet;

/**
 * Created by SR on 30/11/2016.
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;


// ALERT DIALOG
// Sources : http://techblogon.com/alert-dialog-with-edittext-in-android-example-with-source-code/

public class AlertDialogActivity extends Activity
{
    View view;

      MediaPlayer mp ;
    Vibrator v ;
   AudioManager audioManager;
  //   final int originalVolume= mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
      //  originalVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

          audioManager = (AudioManager)getSystemService(getApplicationContext().AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        v = (Vibrator) this.getSystemService(getApplicationContext().VIBRATOR_SERVICE);

        v.vibrate(500);

        mp = MediaPlayer.create(getApplicationContext(), R.raw.shush);
        mp.setVolume(5000,5000);
           mp.start();

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        String appname=getString(R.string.app_name);
        builder.setTitle(appname)
                .setMessage("Start "+appname+" ?")
                 .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent int2= new Intent(getApplication(),MainActivity.class);
                        startActivity(int2);
                        new CountDownTimer(3600000   , 1000) {

                            public void onTick(long millisUntilFinished) {
                                disableWifiBroadcastReceiver(view);
                                disableBlueBroadcastReceiver(view);

                                if(millisUntilFinished<10000){


                                    Toast.makeText(getApplicationContext(),getString(R.string.app_name)+" Wake Up After "+millisUntilFinished/1000,Toast.LENGTH_SHORT).show();

                                }
                            }

                            public void onFinish() {
                                enableBlueBroadcastReceiver(view);
                                enableWifiBroadcastReceiver(view);
                            }

                        }.start();
                        Toast.makeText(getApplicationContext(),getString(R.string.app_name)+" SLEEP "+3600000/60000+" Minutes",Toast.LENGTH_LONG).show();

                        mp.stop();
                       // mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);



                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                        finish();
                        new CountDownTimer(3600000   , 1000) {

                            public void onTick(long millisUntilFinished) {
                                disableWifiBroadcastReceiver(view);
                                disableBlueBroadcastReceiver(view);
                                disableRingBroadcastReceiver(view);
                                if(millisUntilFinished<10000){


                                    Toast.makeText(getApplicationContext(),getString(R.string.app_name)+" Wake Up After "+millisUntilFinished/1000,Toast.LENGTH_SHORT).show();

                                }
                            }

                            public void onFinish() {
                                enableBlueBroadcastReceiver(view);
                                enableWifiBroadcastReceiver(view);
                                enabletRingBroadcastReceiver(view);
                            }

                        }.start();
                        Toast.makeText(getApplicationContext(),getString(R.string.app_name)+" SLEEP "+3600000/60000+" Minutes",Toast.LENGTH_LONG).show();
                        mp.stop();
                     //   mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);



                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }

    public void enableWifiBroadcastReceiver(View view)
    {

        ComponentName receiver = new ComponentName(this, WifiReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(getApplicationContext(),getString(R.string.app_name)+" Wake Up From Sleep",Toast.LENGTH_SHORT).show();
    }
    public void enableBlueBroadcastReceiver(View view)
    {

        ComponentName receiver = new ComponentName(this, BlueReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
     }
    /**
     * This method disables the Broadcast receiver registered in the AndroidManifest file.
     * @param view
     */
    public void disableWifiBroadcastReceiver(View view){
        ComponentName receiver = new ComponentName(this, WifiReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void disableBlueBroadcastReceiver(View view){
        ComponentName receiver = new ComponentName(this, BlueReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
     }
    public void enabletRingBroadcastReceiver(View view)
    {

        ComponentName receiver = new ComponentName(this, RingReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void disableRingBroadcastReceiver(View view){
        ComponentName receiver = new ComponentName(this, RingReceiver.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

}