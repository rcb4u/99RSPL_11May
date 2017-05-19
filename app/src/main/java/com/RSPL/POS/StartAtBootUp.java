package com.RSPL.POS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by rspl-aman on 12/11/16.
 */
public class StartAtBootUp extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       /* if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, login.class);
            context.startService(serviceIntent);
        }*/

        Intent i = new Intent(context, login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
