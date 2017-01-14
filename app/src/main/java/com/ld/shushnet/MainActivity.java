package com.ld.shushnet;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Method;
import java.sql.Time;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    Method dataConnSwitchmethod_ON;
    Method dataConnSwitchmethod_OFF;
    Class telephonyManagerClass;
    Object ITelephonyStub;
    Class ITelephonyClass;



    Intent intent;
    View view;

    String Full;
    String shour;
    String smin;
    static Switch Switchwifi;
    static Switch Switchblue;
    static Switch SwitchRing;
    static AudioManager am2;


    WifiManager wifiManager;
    static BluetoothAdapter mBluetoothAdapter ;
    static BluetoothManager bluetoothManager ;

    static ToggleButton sr_toggle_btmn;
    TimePicker timePicker;
    CustomScrollView  activity_main2;
    CardView CardView1;
    CardView CardView2;

    Context context;
    Calendar  calendar;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    BluetoothDevice mmDevice;
    BluetoothDevice device ;


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
       am2 = (AudioManager) getSystemService(getApplicationContext().AUDIO_SERVICE);

// RingReceiver

        BroadcastReceiver receiver=new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

                if(am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL){
                    SwitchRing.setChecked(false);



                }
                if(am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE){
                    SwitchRing.setChecked(true);


                }
                if(am.getRingerMode() == AudioManager.RINGER_MODE_SILENT){
                    SwitchRing.setChecked(true);

                }
            }
        };
        IntentFilter filter=new IntentFilter(
                AudioManager.RINGER_MODE_CHANGED_ACTION);
        registerReceiver(receiver,filter);





        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //  mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //mBluetoothAdapter = ((BluetoothManager) getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mBluetoothAdapter = ((BluetoothManager) getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        } else {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        calendar = Calendar.getInstance();
        this.context=this;
        Switchwifi = (Switch) findViewById(R.id.Switchwifi);
        Switchblue = (Switch) findViewById(R.id.SwitchBlue);
        SwitchRing = (Switch) findViewById(R.id.SwitchRing);
        sr_toggle_btmn = (ToggleButton) findViewById(R.id.sr_toggle_btmn);
        timePicker= (TimePicker) findViewById(R.id.timePicker);
        CardView1= (CardView) findViewById(R.id.CardView1);
        CardView2= (CardView) findViewById(R.id.CardView2);
        activity_main2= (CustomScrollView) findViewById(R.id.myScroll);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                activity_main2.setEnableScrolling(false);
            }
        });
        CardView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                activity_main2.setEnableScrolling(true);

                return false;
            }
        });
        CardView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                activity_main2.setEnableScrolling(true);

                return false;
            }
        });


                 Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,PendingIntent.FLAG_UPDATE_CURRENT);





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_WIFI_STATE}, 1);
            requestPermissions(new String[]{android.Manifest.permission.CHANGE_WIFI_STATE}, 1);
            requestPermissions(new String[]{android.Manifest.permission.BLUETOOTH_ADMIN}, 1);
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_NETWORK_STATE}, 1);

            requestPermissions(new String[]{android.Manifest.permission.BLUETOOTH}, 1);
            requestPermissions(new String[]{android.Manifest.permission.RECEIVE_BOOT_COMPLETED}, 1);
            requestPermissions(new String[]{android.Manifest.permission.VIBRATE}, 1);

        }

        int chour = new Time(System.currentTimeMillis()).getHours();
        int cmin = new Time(System.currentTimeMillis()).getMinutes();


        timePicker.setHour(chour);
        timePicker.setMinute(cmin);

        final int timemilli=(timePicker.getMinute()*60000);



        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);


        //Switchwifi.setChecked(true);
        // Switchblue.setChecked(false);
        sr_toggle_btmn.setChecked(false);
        if(wifiManager.isScanAlwaysAvailable()){
            Switchwifi.setChecked(true);

        }
        else{
            Switchwifi.setChecked(true);

        }

        if(SRBluetoothAdapterisEnabled()==true){
            Switchblue.setChecked(true);

        }else{
            Switchblue.setChecked(false);

        }



        if(!Switchwifi.isChecked()&&!Switchblue.isChecked()){

            sr_toggle_btmn.setEnabled(false);
            sr_toggle_btmn.setBackgroundColor(Color.GRAY);

        }
        Switchwifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sr_toggle_btmn.setEnabled(true);
                sr_toggle_btmn.setBackgroundResource(R.color.colorAccent);
            }
        });
        Switchblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sr_toggle_btmn.setEnabled(true);
                sr_toggle_btmn.setBackgroundResource(R.color.colorAccent);
            }
        });



        sr_toggle_btmn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {

                    calendar.set(calendar.HOUR_OF_DAY,timePicker.getHour());
                    calendar.set(calendar.MINUTE,timePicker.getMinute());

                    int hour=timePicker.getHour();
                    int min=timePicker.getMinute();

                    String shour=String.valueOf(hour);
                    String   smin=String.valueOf(min);

                    if(hour>12){


                        String Nshour=String.valueOf(hour-12);
                        String   Nsmin=String.valueOf(min);
                        if(Switchwifi.isChecked()){
                            if ( min<10){
                                Full="Restore Wi-Fi At: "+Nshour+":"+"0"+smin+" PM";

                            }
                            else {
                                Full="Restore Wi-Fi At: "+Nshour+":" +smin+" PM";

                            }

                        }
                        if(Switchblue.isChecked()){
                            if ( min<10){
                                Full="Restore Bluetooth At: "+Nshour+":"+"0"+smin+" PM";

                            }
                            else {
                                Full="Restore Bluetooth At: "+Nshour+":"+smin+" PM";

                            }

                        }
                        if(SwitchRing.isChecked()){
                            if ( min<10){
                                Full="Restore Ring Mode To Normal At: "+Nshour+":"+"0"+smin+" PM";

                            }
                            else {
                                Full="Restore Ring Mode To Normal At: "+Nshour+":"+smin+" PM";

                            }

                        }
                        if(Switchwifi.isChecked()&&Switchblue.isChecked()&&SwitchRing.isChecked()){
                            if ( min<10){
                                Full="Restore Wi-Fi , Bluetooth & Ring Mode At: "+Nshour+":"+"0"+smin+" PM";

                            }
                            else {
                                Full="Restore Wi-Fi , Bluetooth & Ring Mode At: "+Nshour+":" +smin+" PM";

                            }
                        }

                    }
                    else {


                        if(Switchwifi.isChecked()){
                            if ( min<10){
                                Full="Restore Wi-Fi At: "+shour+":"+"0"+smin+" AM";

                            }
                            else {
                                Full="Restore Wi-Fi At: "+shour+":" +smin+" AM";

                            }

                        }
                        if(Switchblue.isChecked()){
                            if ( min<10){
                                Full="Restore Bluetooth At: "+shour+":"+"0"+smin+" AM";

                            }
                            else {
                                Full="Restore Bluetooth At: "+shour+":"+smin+" AM";

                            }

                        }
                        if(SwitchRing.isChecked()){
                            if ( min<10){
                                Full="Restore Ring Mode To Normal At: "+shour+":"+"0"+smin+" AM";

                            }
                            else {
                                Full="Restore Ring Mode To Normal At: "+shour+":"+smin+" AM";

                            }

                        }
                        if(Switchwifi.isChecked()&&Switchblue.isChecked()&&SwitchRing.isChecked()){
                            if ( min<10){
                                Full="Restore Wi-Fi , Bluetooth & Ring Mode At: "+shour+":"+"0"+smin+" AM";

                            }
                            else {
                                Full="Restore Wi-Fi , Bluetooth & Ring Mode At: "+shour+":" +smin+" AM";

                            }
                        }

                        if(!Switchwifi.isChecked()&&!Switchblue.isChecked()&&!SwitchRing.isChecked()){

                            Full="No Choice To Restore "+getString(R.string.app_name)+" Stoped ";

                            AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                            alarmManager.cancel(pendingIntent);
                        }
                    }
                    Toast.makeText(getApplicationContext(),getString(R.string.app_name)+" "+Full,Toast.LENGTH_LONG).show();

                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


                } else {
                    Toast.makeText(getApplicationContext(),getString(R.string.app_name)+" Stoped , Thank You",Toast.LENGTH_SHORT).show();
                    AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                    alarmManager.cancel(pendingIntent);

                }

            }
        });

        //set the switch to ON
        //attach a listener to check for changes in state
        Switchwifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                   // am2.setRingerMode(AudioManager.RINGER_MODE_NORMAL);



                } else {
                    //am2.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);


                    // wifiManager.setWifiEnabled(false);
                }

            }
        });
        Switchblue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {

                } else {
                    // mBluetoothAdapter.disable();
                }

            }
        });


    }

    public boolean SRBluetoothAdapterisEnabled(){
      if (mBluetoothAdapter == null) {
            //handle the case where device doesn't support Bluetooth
                    Switchblue.setEnabled(false);
        }else {
            if(mBluetoothAdapter.isEnabled() ){
                return true;

            }else{
                return false;

            }
        }


        return false;


    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        enableWifiBroadcastReceiver(view);

        if(wifiManager.isScanAlwaysAvailable()){
            Switchwifi.setChecked(false);
        }


        else{
            Switchwifi.setChecked(true);


        }
        if(!wifiManager.isWifiEnabled()){
            Switchwifi.setChecked(true);

        }


        if(SRBluetoothAdapterisEnabled()==true){
            Switchblue.setChecked(true);

        }else{
            Switchblue.setChecked(false);

        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        enableWifiBroadcastReceiver(view);
        enableBlueBroadcastReceiver(view);

        if(wifiManager.isScanAlwaysAvailable()){
            Switchwifi.setChecked(false);
        }


        else{
            Switchwifi.setChecked(true);


        }
        if(!wifiManager.isWifiEnabled()){
            Switchwifi.setChecked(true);

        }


        if(SRBluetoothAdapterisEnabled()==true){
            Switchblue.setChecked(true);
            enableBlueBroadcastReceiver(view);

        }else{
            Switchblue.setChecked(false);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(wifiManager.isScanAlwaysAvailable()){
            Switchwifi.setChecked(false);
        }


        else{
            Switchwifi.setChecked(true);


        }
        if(!wifiManager.isWifiEnabled()){
            Switchwifi.setChecked(true);


        }
            else {

        }


        if(SRBluetoothAdapterisEnabled()==true){
            Switchblue.setChecked(true);
            enableBlueBroadcastReceiver(view);

        }else{
            Switchblue.setChecked(false);

        }
    }

    static void bludis(){


        mBluetoothAdapter.disable();
    }

    public void enableWifiBroadcastReceiver(View view)
    {

        ComponentName receiver = new ComponentName(this, WifiReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(getApplicationContext(),getString(R.string.app_name)+" Wake Up",Toast.LENGTH_SHORT).show();
    }
    public void enableBlueBroadcastReceiver(View view)
    {

        ComponentName receiver = new ComponentName(this, BlueReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
     }


    private void GetDataConnectionAPI() {
        this.getApplicationContext();
        TelephonyManager telephonyManager =
                (TelephonyManager) this.getApplicationContext().
                        getSystemService(Context.TELEPHONY_SERVICE);

        try {
            telephonyManagerClass = Class.forName(telephonyManager.getClass().getName());
            Method getITelephonyMethod = telephonyManagerClass.getDeclaredMethod("getITelephony");
            getITelephonyMethod.setAccessible(true);
            ITelephonyStub = getITelephonyMethod.invoke(telephonyManager);
            ITelephonyClass = Class.forName(ITelephonyStub.getClass().getName());

            dataConnSwitchmethod_OFF =
                    ITelephonyClass.getDeclaredMethod("disableDataConnectivity");
            dataConnSwitchmethod_ON = ITelephonyClass.getDeclaredMethod("enableDataConnectivity");
        } catch (Exception e) { // ugly but works for me
            e.printStackTrace();
        }

    }




}