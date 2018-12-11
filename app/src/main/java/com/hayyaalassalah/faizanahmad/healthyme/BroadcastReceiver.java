package com.hayyaalassalah.faizanahmad.healthyme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Faizan Ahmad on 11/26/2016.
 */
public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = preferences.getString("Name", "");

        Intent startServiceIntent = new Intent(context, StepCounterService.class);
        context.startService(startServiceIntent);
        System.out.println("Broadcast receiver working");
    }
}
