package com.RSPL.POS; /**
 * Created by Dhananjay on 11/25/2016.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    public static Bundle dd = new Bundle();


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {



       /* if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                       sendPushNotification(json);
            } catch (JSONException e) {

                Log.e(TAG, "Exception: " + e.getMessage());


            }
        }*/



        String title=remoteMessage.getNotification().getTitle();
        String body=remoteMessage.getNotification().getBody();
        NotificationCompat.Builder noti= new NotificationCompat.Builder(this);


        noti.setContentTitle(title);
        noti.setContentText(body);

        Intent i = new Intent(this, ActivityStore.class);
        Bundle bundle = new Bundle();
        //Add your data from getFactualResults method to bundle
        bundle.putString("BODY", body);
        //Add the bundle to the intent
        i.putExtras(bundle);


        com.RSPL.POS.MyFirebaseMessagingService.dd.putString("BODY", body);


    /*    NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

        Intent resultIntent = new Intent(this, validate_Activity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );*/



      /*  mBuilder.setContentIntent(resultPendingIntent);
// Sets an ID for the notification
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
*/




    }

    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
//            JSONObject data = json.getJSONObject("data");
////
////            //parsing json data
//            String message = data.getString("message");
//            String title = data.getString("title");
//
//            String imageUrl = data.getString("image");
            String message = json.getString("message");
            String title = json.getString("title");

            String imageUrl = json.getString("image");

            //creating com.RSPL.POS.MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
          //  Intent intent = new Intent(getApplicationContext(), Main2Activity.class);

            //if there is no image
            if(imageUrl.equals("null")){
                //displaying small notification
              //  mNotificationManager.showSmallNotification(title, message, intent);
            }else{
                //if there is an image
                //displaying a big notification
             //   mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }

         //   Notification("ttt","6768");
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    private void Notification(String message,
                              String title) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        android.app.Notification notification = new android.app.Notification(
                R.drawable.ic_customers, "Message from Dipak Keshariya! (Android Developer)",
                System.currentTimeMillis());

        Intent notificationIntent = new Intent(this, validate_Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        //  notification.setLatestEventInfo(MyFirebaseMessagingService.this,
      //  notificationTitle, notificationMessage, pendingIntent);
        notificationManager.notify(10001, notification);
    }


}