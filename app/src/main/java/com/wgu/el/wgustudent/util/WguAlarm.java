package com.wgu.el.wgustudent.util;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WguAlarm extends BroadcastReceiver {

    /**
     * https://stackoverflow.com/questions/4459058/alarm-manager-example
     * https://gist.github.com/BrandonSmith/6679223
     * @param context
     * @param intent
     */

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
//        wl.acquire();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);

//        wl.release();
    }

//    public void setAlarm(Context context) {
//        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent i = new Intent(context, WguAlarm.class);
//        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 10, pi); // Millisec * Second * Minute
//    }
//
//    public void cancelAlarm(Context context) {
//        Intent intent = new Intent(context, WguAlarm.class);
//        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(sender);
//    }

}