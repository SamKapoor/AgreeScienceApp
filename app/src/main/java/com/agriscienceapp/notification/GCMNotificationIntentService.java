package com.agriscienceapp.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.agriscienceapp.R;
import com.agriscienceapp.activity.HomeActivity;
import com.agriscienceapp.activity.NavigationDrawerActivity;
import com.agriscienceapp.common.Consts;
import com.agriscienceapp.fragments.SamacharFragment;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMNotificationIntentService extends IntentService {
    public static final int notifyID = 9001;
    NotificationCompat.Builder builder;
    private String type = "1";

    public GCMNotificationIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();


        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification("Deleted messages on server: "
                        + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {
                type = extras.getString("type");
                sendNotification(extras.getString("message"));
//                sendNotification("Message Received from Google GCM Server: "
//                        + extras.get(Consts.MSG_STORYID_KEY));

                Log.i("MSG_NEWSID_KEY key", "MSG_NEWSID_KEY  key:::" + extras.get(Consts.MSG_NEWSID_KEY));

//                Log.i("MSG_STORYID_KEY ", "MSG_STORYID_KEY  key:::" + extras.get(Consts.MSG_STORYID_KEY));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        SamacharFragment mSamacharFragment = new SamacharFragment();

        Intent resultIntent = new Intent(this, NavigationDrawerActivity.class);
        if (type.equalsIgnoreCase("n"))
            resultIntent.putExtra("Value", 1);
        else if (type.equalsIgnoreCase("v"))
            resultIntent.putExtra("Value", 5);
        else
            resultIntent.putExtra("Value", 4);

        resultIntent.putExtra("result", msg);


        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mNotifyBuilder;
        NotificationManager mNotificationManager;

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int drawable;
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = R.drawable.app_logo;
        } else {
            drawable = R.drawable.app_logo_prelollipop;
        }

        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(msg)
                .setSmallIcon(drawable);
        mNotifyBuilder.setContentIntent(resultPendingIntent);


        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;

        mNotifyBuilder.setDefaults(defaults);
        mNotifyBuilder.setContentText(msg);
        mNotifyBuilder.setAutoCancel(true);
        mNotificationManager.notify(notifyID, mNotifyBuilder.build());
    }
}