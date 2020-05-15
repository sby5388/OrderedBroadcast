package com.by5388.demo.orderedbroadcast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author Administrator  on 2020/5/15.
 */
public class SendBroadcastTools {
    public static final String TAG = "SendBroadcastTools";

    private SendBroadcastTools() {

    }

    /**
     * TODO 在Android 8.0 之后 静态注册的广播接收者已经无法生效了，
     * TODO 参考 https://www.jianshu.com/p/5283ebc225d5?utm_source=oschina-app
     * {@link Intent#FLAG_RECEIVER_INCLUDE_BACKGROUND}
     *
     * @param context
     * @param text
     */
    @SuppressLint("WrongConstant")
    public static void sendBroadcast(Context context, String text) {
        final Intent intent = new Intent(DefaultReceiver.DEFAULT_ACTION);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        // TODO: 2020/5/15 如果通过设置了类名，则 动态注册的无法收到广播
//        intent.setClassName(BuildConfig.APPLICATION_ID, DefaultReceiver.class.getName());
        // TODO: 2020/5/15 这个值被hide了，是 Intent#FLAG_RECEIVER_INCLUDE_BACKGROUND
        int FLAG_RECEIVER_INCLUDE_BACKGROUND = 0x01000000;
//        intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        intent.addFlags(FLAG_RECEIVER_INCLUDE_BACKGROUND);
        context.sendOrderedBroadcast(intent, null);
        Log.d(TAG, "sendBroadcast: text = " + text);
    }
}
