package com.example.advosoft.vocab365.fcm;///**

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.advosoft.vocab365.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String contract_id;
    private static final String TAG = "MyFirebaseMsgService";
    private int count = 0;
  //  int badgeCount = 1;
    public static Boolean eventb=false;


    @Override
    public void onCreate() {
        super.onCreate();


    }
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // TODO(developer): Handle FCM messages here.
        String message = remoteMessage.getData().get("message");
        Log.d(TAG, "message: " + message);
        Log.d(TAG, "NotificationFragment Message Body: " + remoteMessage.getData().toString());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().toString());
            sendNotification(remoteMessage.getData().get("text"),remoteMessage.getData().get("title"));
            //  contract_id=remoteMessage.getData().get("contractRequestId");

          //  String chatGroupId=remoteMessage.getData().get("chatGroupId");

        }
       // if (remoteMessage.getNotification() != null && remoteMessage.getNotification().getBody() != null)
            //sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());

        String message_from = "", competition_id = "", type = "", title = "", badgecount = "", body = "";
//        Map<String, String> data = remoteMessage.getData();
//        if (data != null) {
//            message_from = data.get("message_from");
//            competition_id = data.get("competition_id");
//            type = data.get("type");
//            title = data.get("title");
//            badgecount = data.get("badge_count");
//            body = data.get("body");
//        }
       int badge_count = 0;
//
    /* if (remoteMessage.getData().get("badge") != null && !remoteMessage.getData().get("badge").equalsIgnoreCase("")) {
         badge_count = Integer.parseInt(remoteMessage.getData().get("badge"));
         ShortcutBadger.applyCount(getApplicationContext(), badge_count);
        // Utils.setBadge(getApplicationContext(), badge_count);
     }*/
//            ApplicationUtils.setBadgecount(badge_count);
//        }
////        String messagetype = remoteMessage.getMessageType();
//
//        if (type != null && !type.equalsIgnoreCase("CHAT")) {
//            showNotification(body, 0, getResources().getString(R.string.app_name));
//            try {
//                Intent bIntent = new Intent("custom-event-name");
//                bIntent.putExtra("type", type);
////            bIntent.putExtra("badge_count", badge_count);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(bIntent);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            if (title == null)
//                title = getResources().getString(R.string.app_name);
//            if (competition_id == null) {
//                competition_id = "1";
//            } else if (competition_id.equalsIgnoreCase("")) {
//                competition_id = "1";
//            }
//            try {
//                Intent bIntent = new Intent("custom-event-name");
//                bIntent.putExtra("type", type);
//                bIntent.putExtra("message_from", message_from);
//                bIntent.putExtra("username", title);
//                bIntent.putExtra("message", body);
////            bIntent.putExtra("badge_count", badge_count);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(bIntent);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (!message_from.equalsIgnoreCase(ApplicationUtils.getInstance().getUser_id()))
//                showNotification(body, Integer.parseInt(competition_id), title);
//        }
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     * <p>
     * //     * @param messageBody FCM message body received.
     */





    private void sendNotification(String message,String title) {
        Bitmap remote_picture = null;

        //  String arr[] = message.split("###");

        NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
//            notiStyle.setSummaryText(arr[0]);

        NotificationManager notificationManager = null;
        Notification notification = null;
        try {
            notificationManager = (NotificationManager) this .getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent contentIntent = null;

            Intent gotoIntent = new Intent();
            gotoIntent.setClassName(this, "com.example.advosoft.boulo.activities.MainActivity");//Start activity when user taps on notification.

            contentIntent = PendingIntent.getActivity(this,
                    (int) (Math.random() * 100), gotoIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    this);
            notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setContentIntent(contentIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentText(message)
                    .build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            count++;
            notificationManager.notify(count, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        if (arr.length == 2) {
//            try {


            new generatePictureStyleNotification(this, getString(R.string.app_name), arr[0], arr[1]).execute();
//                remote_picture = BitmapFactory.decodeStream((InputStream) new URL(arr[1]).getContent());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        } else {
//            NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
//            notiStyle.setSummaryText(arr[0]);

            NotificationManager notificationManager = null;
            Notification notification = null;
            try {
                notificationManager = (NotificationManager) this .getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent contentIntent = null;

                Intent gotoIntent = new Intent();
                gotoIntent.setClassName(this, "com.advocosoft.dothatapp.activities.MainActivity");//Start activity when user taps on notification.

                contentIntent = PendingIntent.getActivity(this,
                        (int) (Math.random() * 100), gotoIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        this);
                notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(arr[0]).setWhen(0)
                        .setAutoCancel(true)
                        .setContentTitle(arr[0])
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(arr[0]))
                        .setContentIntent(contentIntent)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentText(arr[1])
                        .build();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                count++;
                notificationManager.notify(count, notification);
            } catch (Exception e) {
                e.printStackTrace();
            }


            //This will generate seperate notification each time server sends.
        }*/
    }

}
