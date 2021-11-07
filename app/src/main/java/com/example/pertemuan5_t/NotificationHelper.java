package com.example.pertemuan5_t;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    private static  final String channelId ="ChanneId";
    private static  final String channelName ="Channel 1";
    private NotificationManager notificationManager;

    public NotificationHelper (Context base){
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    private void createChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(channelId,channelName,notificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);

        notificationChannel.setLightColor(R.color.teal_200);
        getManager().createNotificationChannel(notificationChannel);
    }

   public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return  notificationManager;
    }
    public NotificationCompat.Builder getChannel_Notification() {
        Intent notifyIntent = new Intent(this,MainActivity.class);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder( getApplicationContext(),channelId)
                .setContentTitle("Alarm!")
                .setContentText("SEGERA BANGUN")
                .setSmallIcon(R.drawable.ic_alarm_black_24dp).setContentIntent(notifyPendingIntent);
    }
}
