package com.by5388.demo.orderedbroadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class DefaultReceiver extends BroadcastReceiver {
    public static final String TAG = "DefaultReceiver";
    private static int id = 100;
    public static final String DEFAULT_ACTION = "com.by5388.demo.orderedbroadcast.default_action";

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Log.d(TAG, "onReceive: ");
        if (DEFAULT_ACTION.equals(action)) {
            final String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (!TextUtils.isEmpty(text)) {
                showNotification(context, text);
            }
        }
    }

    private void showNotification(@NonNull Context context, final String text) {
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            // TODO: 2020/5/15 需要换成Notification
            Toast.makeText(context, "收到消息：" + text, Toast.LENGTH_SHORT).show();
            return;
        }
        final Notification notification = createNotification(context, text);
        notificationManager.notify(id, notification);
        id++;
    }

    private Notification createNotification(Context context, final String text) {
        final String channelId = BuildConfig.APPLICATION_ID;
        final Notification notification;
        final int sdkInt = Build.VERSION.SDK_INT;

        final Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (sdkInt >= Build.VERSION_CODES.O) {
            final Notification.Builder builder = new Notification.Builder(context, channelId);
            builder.setContentTitle("收到新消息")
                    .setContentIntent(pendingIntent)
                    .setSubText(text)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true);
            notification = builder.build();
        } else {
            notification = new Notification();
            if (sdkInt >= Build.VERSION_CODES.JELLY_BEAN) {
                notification.priority = Notification.PRIORITY_DEFAULT;
            }
            notification.contentIntent = pendingIntent;
            notification.tickerText = "收到新消息" + text;
            notification.icon = R.drawable.ic_launcher_foreground;
        }
        return notification;

    }

}
