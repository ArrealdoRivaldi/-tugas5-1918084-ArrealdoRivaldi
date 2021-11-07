package com.example.pertemuan5_t;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private TextView txtTime;
    private Button btnOpen, btnCancel;
    private Calendar calendar;
    int jam , menit;
    private Object NotificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTime = findViewById(R.id.txt_time);
        btnOpen = findViewById(R.id.btn_buka);
        btnCancel = findViewById(R.id.btn_batal);

        NotificationHelper = new NotificationHelper(this);

        calendar = Calendar.getInstance();
        jam = calendar.get(Calendar.HOUR_OF_DAY);
        menit = calendar.get(Calendar.MINUTE);

        btnOpen.setOnClickListener(this::onClick);
        btnCancel.setOnClickListener(this::onClick);
    }
    //menangkap inputan jam kalian lalu memulai alarm
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);
        updateTimeText(c);
        startAlarm(c);
    }
    //mengganti text view
    private void updateTimeText(Calendar c){
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        txtTime.setText(timeText);
    }
    //memulai alarm
    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);
        Intent a = new Intent(this,AlertReceiver.class);
        PendingIntent b = PendingIntent.getBroadcast(this,1,a,0);
        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE,1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),b);
    }
    //menggagalkan alarm
    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);
        Intent a = new Intent(this,AlertReceiver.class);
        PendingIntent b =
                PendingIntent.getBroadcast(this,1,a,0);
        alarmManager.cancel(b);
        txtTime.setText("Alarm Canceled");
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_buka:
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"Time Picker");
                break;
            case R.id.btn_batal:
                cancelAlarm();
                break;

        }
    }
}