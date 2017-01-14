package com.ld.shushnet;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    WifiManager wifiManager2;
    BluetoothAdapter mBluetoothAdapter2 ;

    MediaPlayer mp ;
    Vibrator v ;
    AudioManager audioManager;



    @Override
    public void onReceive(Context context, Intent intent) {


        if (MainActivity.Switchwifi.isChecked()) {

            try {
                turnOnWifi(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "Wifi is Restored", Toast.LENGTH_SHORT).show();



            mp = MediaPlayer.create(context, R.raw.shush);
            mp.setVolume(5000,5000);
            mp.start();

            MainActivity.sr_toggle_btmn.setChecked(false);

        }
        if (MainActivity.Switchblue.isChecked()) {

            try {
MainActivity.mBluetoothAdapter.disable();

            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(context, "Blutooth is Restored", Toast.LENGTH_SHORT).show();

            mp = MediaPlayer.create(context, R.raw.shush);
            mp.setVolume(5000,5000);
            mp.start();

            MainActivity.sr_toggle_btmn.setChecked(false);
        }
        if (MainActivity.SwitchRing.isChecked()) {

            try {
               MainActivity.am2.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                 //MainActivity.am2.setRingerMode(Integer.parseInt(AudioManager.RINGER_MODE_CHANGED_ACTION));

                Settings.Global.putString(context.getContentResolver(), "DO_NOT_DISTURB", "0");

            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(context, "Ring Mode Now Normal", Toast.LENGTH_SHORT).show();

            mp = MediaPlayer.create(context, R.raw.shush);
            mp.setVolume(5000,5000);
            mp.start();

            MainActivity.sr_toggle_btmn.setChecked(false);
        }

        if (MainActivity.Switchwifi.isChecked()&&MainActivity.Switchblue.isChecked()) {

            try {
                turnOnWifi(context);
            } catch (Exception e) {
                e.printStackTrace();
            }

            MainActivity.sr_toggle_btmn.setChecked(false);

            try {
                MainActivity.mBluetoothAdapter.disable();

            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(context, "Wifi and Blutooth is Restored", Toast.LENGTH_SHORT).show();

            mp = MediaPlayer.create(context, R.raw.shush);
            mp.setVolume(5000,5000);
            mp.start();

         }





    }
    public void turnOnWifi(Context context) throws Exception {
        wifiManager2 = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager2.setWifiEnabled(true);
        Thread.sleep(2000L);
    }

     public void turnOffblue(Context context) throws Exception {
        mBluetoothAdapter2 = (BluetoothAdapter) context.getSystemService(Context.BLUETOOTH_SERVICE);
         mBluetoothAdapter2.disable();
        Thread.sleep(2000L);
    }

};
