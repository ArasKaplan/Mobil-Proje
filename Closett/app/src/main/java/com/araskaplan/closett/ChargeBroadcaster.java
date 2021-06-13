package com.araskaplan.closett;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ChargeBroadcaster extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
            Toast.makeText(context,"Backup in progress",Toast.LENGTH_LONG).show();

        }else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
            Toast.makeText(context,"Backup halted",Toast.LENGTH_LONG);
        }
    }
}
