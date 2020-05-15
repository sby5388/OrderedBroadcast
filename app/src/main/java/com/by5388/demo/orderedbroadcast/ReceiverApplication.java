package com.by5388.demo.orderedbroadcast;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import java.util.Objects;

import androidx.core.app.NotificationManagerCompat;

/**
 * @author Administrator  on 2020/3/3.
 */
public class ReceiverApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 2020/3/3 需要在应用启动时就像设备注册广播权限
        registerChannelId();
    }


    /**
     * TODO
     * {@link NotificationManagerCompat managerCompat}
     * TODO 创建渠道并设置优先级
     * 在Android8.0之后想发送通知事件，必须向createNotificationChannel() 传递 NotificationChannel 的实例,
     * 以便在系统中注册应用的通知渠道。
     * 由于您必须先创建通知渠道，然后才能在 Android 8.0 及更高版本上发布任何通知，因此应在应用启动时立即执行这段代码。
     * 反复调用这段代码是安全的，因为创建现有通知渠道不会执行任何操作。
     * <p>
     * https://developer.android.google.cn/training/notify-user/build-notification#java
     */
    private void registerChannelId() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        final CharSequence name = "有序广播";
        final String description ="有序广播详情";
        final int importance = NotificationManager.IMPORTANCE_DEFAULT;
        final NotificationChannel channel = new NotificationChannel(BuildConfig.APPLICATION_ID, name, importance);
        channel.setDescription(description);
        // TODO  请注意，NotificationChannel 构造函数需要一个 importance，
        //  它使用 NotificationManager 类中的其中一个常量。
        //  该参数确定出现任何属于该渠道的通知时如何打断用户，
        //  但您还必须使用 setPriority() 设置优先级以支持 Android 7.1 和更低版本。

        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Objects.requireNonNull(manager).createNotificationChannel(channel);
    }
}
