package com.ld.shushnet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class WifiReceiver  extends BroadcastReceiver {



    final String pressed = "false";

        @Override
        public void onReceive(Context context, Intent intent) {



            ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMan.getActiveNetworkInfo();



            if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                 Log.d("WifiReceiver", "Wifi Is CONNECT");


            }
            else {
                Intent i = new Intent(context,  AlertDialogActivity.class);
                 context.startActivity(i);


             }


             }




    };
