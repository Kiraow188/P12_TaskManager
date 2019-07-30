package sg.edu.rp.c347.p06_taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationReceiver extends BroadcastReceiver {
    int requestCode = 888;

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("Data");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("default", "DEFAULT Channel", NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent1 = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "This is an Action", pendingIntent).build();

        Intent intentreply = new Intent(context, ReplyActivity.class);
        PendingIntent pendingIntentReply = PendingIntent.getActivity(context, 0, intentreply, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput ri = null;
        ri = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String[] {"Completed", "Not yet"})
                .build();

        NotificationCompat.Action action2 = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Reply", pendingIntentReply)
                .addRemoteInput(ri)
                .build();

        NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender();
        extender.addAction(action);
        extender.addAction(action2);


        Intent i = new Intent(context, MainActivity.class);

        PendingIntent pIntent = PendingIntent.getBroadcast(context, requestCode, i, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setContentTitle("Task Manager Reminder");
        builder.setContentText(data);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);
        builder.extend(extender);

        long[] v = {500,1000};
        builder.setVibrate(v);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);
        builder.setLights(Color.BLUE, 2000, 1000);
        builder.setPriority(Notification.PRIORITY_HIGH);

        Notification n = builder.build();
        notificationManager.notify(requestCode, n);

    }
}
