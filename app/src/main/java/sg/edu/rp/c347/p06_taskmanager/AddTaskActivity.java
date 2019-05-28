package sg.edu.rp.c347.p06_taskmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    EditText etName, etDesc, etTime;
    Button btnAdd, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etDesc = findViewById(R.id.etDesc);
        etName = findViewById(R.id.etName);
        etTime = findViewById(R.id.etReminder);

        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                DBHelper dbh = new DBHelper(AddTaskActivity.this);
                long row_affected = dbh.insertTask(name, desc);
                dbh.close();
                if (row_affected != -1) {
                    showNotification(Integer.parseInt(etTime.getText().toString()));
                    Toast.makeText(AddTaskActivity.this, "Added successfully",
                            Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etDesc.setText("");
                    etTime.setText("");
                    Intent i = new Intent();
                    setResult(RESULT_OK, i);

                    finish();
                }
            }
        });
    }

    private void showNotification(int time){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, time);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("default", "DEFAULT Channel", NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
        int requestCode = 888;
        PendingIntent pIntent = PendingIntent.getActivity(AddTaskActivity.this, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(AddTaskActivity.this, "default");
        builder.setContentTitle("Task Manager Reminder");
        builder.setContentText(etName.getText().toString());
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);
        long[] v = {500,1000};
        builder.setVibrate(v);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);
        builder.setLights(Color.BLUE, 2000, 1000);

        builder.setPriority(Notification.PRIORITY_HIGH);

        Notification n = builder.build();

        notificationManager.notify(requestCode, n);
        finish();
    }
}
