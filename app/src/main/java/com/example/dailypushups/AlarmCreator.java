package com.example.dailypushups;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.RequiresApi;

import static android.content.Context.ALARM_SERVICE;

public class AlarmCreator {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void setAlarm(Context context, long minutes){

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+1000 * 60 * minutes, pendingIntent);
        Log.d("tag", "ALARM SET2  :d");


    }


}
