package com.bung.bungapp.core;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bung.bungapp.MainActivity;
import com.bung.bungapp.R;
import com.bung.bungapp.model.Party;
import com.bung.bungapp.model.Post;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by rok on 2015. 11. 14..
 */
public class GcmIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    public interface onNewPushMessageListener {
        void onNewPost(Post post);
        void onNewParty(Party party);
    }

    public static Collection<onNewPushMessageListener> mListeners = new HashSet<>();

    public static void addOnNewGcmMessageListener(onNewPushMessageListener listener) {
        mListeners.add(listener);
    }

    public static void removeOnNewGcmMessageListener(onNewPushMessageListener listener) {
        mListeners.remove(listener);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                // Post notification of received message.
                Gson gson = new Gson();
                Post post = gson.fromJson(extras.getString("msg"), Post.class);

                sendNotification(post);
                Log.i(this.getClass().getSimpleName(), "Received: " + extras.toString());

                if (post != null) {
                    // Invoke listener
                    for (onNewPushMessageListener listener : mListeners) {
                        listener.onNewPost(post);
                    }

                }
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                //sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                //sendNotification("Deleted messages on server: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(Post post) {

        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.PARAM_REDIRECT_FRAGMENT, MainActivity.TAG_FRAGMENT_NEARBY);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String msg = "New guest just visited to your home!";

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("New Party")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setTicker("New party created!")
                .setContentText(msg)
                .setContentInfo(getString(R.string.app_name))
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent);

        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}