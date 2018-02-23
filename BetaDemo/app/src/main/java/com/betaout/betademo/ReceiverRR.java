package com.betaout.betademo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by root on 24/1/18.
 */
public class ReceiverRR extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if(intent.getAction().equals("action.betaout.INBOX_UPDATED.<Project ID>")){
            Log.i("AppInfo", "Inbox got Updated");
        }else {
            if (intent.getExtras() == null || intent.getStringExtra("status") == null || intent.getStringExtra("status").length() == 0
                    || intent.getStringExtra("beta_out_push_custom_keys") == null ||
                    intent.getStringExtra("beta_out_push_custom_keys").length() == 0)
                throw new NullPointerException();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Broadcast Received\n" +
                            intent.getExtras().getString("beta_out_push_custom_keys") + "\n"
                            + intent.getExtras().getString("status") + "\n" +
                            (intent.hasExtra("inApp") ? "inApp" : "push"),Toast.LENGTH_LONG).show();
                }
            });
            Log.i("AppInfo", "Broadcast Received");
            Log.i("AppInfo", intent.getExtras().getString("beta_out_push_custom_keys"));
            Log.i("AppInfo", intent.getExtras().getString("status"));
            Log.i("AppInfo", intent.hasExtra("inApp") ? "inApp" : "push");
        }
    }
}
