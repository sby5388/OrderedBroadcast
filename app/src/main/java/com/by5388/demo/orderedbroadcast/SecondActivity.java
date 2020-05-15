package com.by5388.demo.orderedbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView mShow;
    private IntentFilter mIntentFilter = new IntentFilter(DefaultReceiver.DEFAULT_ACTION);

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (DefaultReceiver.DEFAULT_ACTION.equals(action)) {
                final String text = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (!TextUtils.isEmpty(text)) {
                    mShow.setText(String.format("接收文本 ： %s", text));
                }
            }
            // TODO: 2020/5/15 阻断广播，不再继续下发
            abortBroadcast();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mShow = findViewById(R.id.text_show);
        final Intent intent = getIntent();
        final String stringExtra = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (!TextUtils.isEmpty(stringExtra)) {
            mShow.setText(stringExtra);
        }


        final EditText input = findViewById(R.id.text_input);
        findViewById(R.id.button_send_broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString().trim();
                if (TextUtils.isEmpty(text)) {
                    text = "empty";
                }
                SendBroadcastTools.sendBroadcast(SecondActivity.this, text);
            }
        });
        mIntentFilter.setPriority(100);
        try {
            mIntentFilter.addDataType("text/plain");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }
}
