package com.lipad.lipad;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "weatherNotifications";
    public static final String channelName = "Weather Notifications";
    static NotificationCompat.Builder nCompat;
    MiscDatabaseHelper miscDatabaseHelper;
    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        miscDatabaseHelper = new MiscDatabaseHelper(this);
        Cursor data = miscDatabaseHelper.getTomorrowData();
        data.moveToFirst();
        MainActivity.tomorrowHigh = data.getString(0);
        MainActivity.tomorrowLow = data.getString(1);

        Intent intentx = new Intent(this, MainActivity.class);
        PendingIntent pendingIntentx = PendingIntent.getActivity(this, 0, intentx, 0);

        if (MainActivity.notificationType.equals("alarm")) {
            nCompat = new NotificationCompat.Builder(getApplicationContext(), channelID)
                    .setContentTitle("Good morning!")
                    .setContentText("Today's high will be " + MainActivity.tomorrowHigh + " while the low will be " + MainActivity.tomorrowLow + ".")
                    .setSmallIcon(R.drawable.notif)
                    .setContentIntent(pendingIntentx)
                    .addAction(R.drawable.ic_add, "OPEN APP", pendingIntentx)
                    .setAutoCancel(true);
        } else if (MainActivity.notificationType.equals("alert")) {
            if (MainActivity.nextState.equals("hot")) {
                nCompat = new NotificationCompat.Builder(getApplicationContext(), channelID)
                        .setContentTitle("It's quite hot outside!")
                        .setContentText("Right now, it's " + MainActivity.nextParameter + ". We advise you not to use any insecticides until temperatures cool down.")
                        .setSmallIcon(R.drawable.notif)
                        .setContentIntent(pendingIntentx)
                        .addAction(R.drawable.ic_add, "OPEN APP", pendingIntentx)
                        .setAutoCancel(true);
            } else if (MainActivity.nextState.equals("fog")) {
                nCompat = new NotificationCompat.Builder(getApplicationContext(), channelID)
                        .setContentTitle("It's a little foggy outside!")
                        .setContentText("We advise you not to use any type of fertilizer while it is foggy.")
                        .setSmallIcon(R.drawable.notif)
                        .setContentIntent(pendingIntentx)
                        .addAction(R.drawable.ic_add, "OPEN APP", pendingIntentx)
                        .setAutoCancel(true);
            }
        }
        return nCompat;
    }
}