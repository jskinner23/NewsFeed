package com.example.jonathanskinner.newsfeed.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jonathanskinner.newsfeed.R;
import com.example.jonathanskinner.newsfeed.util.BroadcastUtil;

public class AppBroadcastReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(this).registerReceiver(noNetworkAvailableBroadcastReceiver, new IntentFilter(BroadcastUtil.ACTION_NO_NETWORK_AVAILABLE));
    }

    private BroadcastReceiver noNetworkAvailableBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), R.string.no_network_available, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(noNetworkAvailableBroadcastReceiver);

        super.onDestroy();
    }
}
