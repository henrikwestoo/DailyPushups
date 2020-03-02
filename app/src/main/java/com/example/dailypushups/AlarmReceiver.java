package com.example.dailypushups;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    //bestämmer vad som händer när alarmet i AlarmCreator går igång
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent){


        //skapa en notificationchannel
        NotificationChannel channel = new NotificationChannel("channel-id", "notification-channel", NotificationManager.IMPORTANCE_DEFAULT);

        //skapa en notificationbuilder där jag anger kanal-idt
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel-id");

        //obligatoriska för notifikationen
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle("DailyPushups");
        builder.setContentText("Have you done your pushups today?");

        //visa notifikationen
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        notificationManager.notify(1, builder.build());

    }

}