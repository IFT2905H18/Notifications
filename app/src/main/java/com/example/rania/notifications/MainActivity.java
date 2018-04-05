package com.example.rania.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    int notId = 0;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //simpleNotification();
        expendableNotification();
    }

    public void simpleNotification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setAutoCancel(true);
        //Anciennes versions : setPriority
        //Nouvelles versions : channelID

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ResultActivity.class);

        //Expliquer le back stack : https://developer.android.com/guide/components/activities/tasks-and-back-stack.html
        //Pas de backstack needed : https://developer.android.com/training/notify-user/navigation.html
        //Backstack needed : Définir parents dans manifest

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ResultActivity.class); //Dit que Main est parent
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0, //num privé pour accéder au stack
                        PendingIntent.FLAG_UPDATE_CURRENT //Replace if intent exists
                );

        //you can add arguments to Intent objects in the stack by calling TaskStackBuilder.editIntentAt()

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //NOTIFICATION_SERVICE
        //Use with getSystemService(String) to retrieve a NotificationManager for informing the user of background events.

        // mId allows you to update the notification later on.
        mNotificationManager.notify(notId, mBuilder.build());
    }

    public void expendableNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Event tracker")
            .setContentText("Events received")
                .setAutoCancel(true);

        /*        .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
          .addAction(R.drawable.ic_snooze, getString(R.string.snooze),
                snoozePendingIntent);
        */

        //https://developer.android.com/training/notify-user/expanded.html


        NotificationCompat.InboxStyle inboxStyle =
        new NotificationCompat.InboxStyle();
        String[] events = new String[6];
        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Event tracker details:");

        // Moves events into the expanded layout
        for (int i=0; i < events.length; i++) {
            inboxStyle.addLine("information "+((Integer)i).toString());
        }
        // Moves the expanded layout object into the notification object.
        mBuilder.setStyle(inboxStyle);

        // Issue the notification here.
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ResultActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ResultActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Nouvelles versions : NotificationManagerCompat.from(this)
        // mId allows you to update the notification later on.
        mNotificationManager.notify(notId, mBuilder.build());
    }
}
