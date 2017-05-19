package com.RSPL.POS;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Service_slaveurl extends Service {
    public Service_slaveurl() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);

        slaveurlScript();

        Toast.makeText(this, "Script send called", Toast.LENGTH_LONG).show();


    }


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

    }



    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private  void slaveurlScript(){

       /*created on sat 15:55 20-08-2016 */
        /*shell script execute for upload remaining data on the back end*/
        /*call shell script function from the ShellExecuter class*/

        Process p = null;
        try {
            p = Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataOutputStream os = new DataOutputStream(p.getOutputStream());
        InputStream is = p.getInputStream();



        Process p1 = null;
        try {
            p1 = Runtime.getRuntime().exec("sh /sdcard/99script/send1.sh");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
