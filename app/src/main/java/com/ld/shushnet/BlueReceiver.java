package com.ld.shushnet;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BlueReceiver extends BroadcastReceiver {



    final String pressed = "false";
    BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    AlertDialogActivity ff=new AlertDialogActivity( );
    MainActivity ma=new MainActivity();


    @Override
    public void onReceive(Context context, Intent intent) {


        String action = intent.getAction();

        // It means the user has changed his bluetooth state.

        action = intent.getAction();

        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch(state) {

                case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.i("**********BlueReceiver","OFF");


                     break;

                case BluetoothAdapter.STATE_TURNING_ON:
                        Log.i("**********BlueReceiver","ON");

                    Intent i = new Intent(context,  AlertDialogActivity.class);
                    context.startActivity(i);

                    break;
            }

        }
    }



















};
