package com.example.alarm2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // 알람 시간
    private Calendar calendar;

    private TimePicker timePicker;

    private List<Calendar> alarmTimes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.calendar = Calendar.getInstance();
        // 현재 날짜 표시
        displayDate();

        this.timePicker = findViewById(R.id.timePicker);
        // 현재 시간 표시
        displayTime();

        //findViewById(R.id.btnCalendar).setOnClickListener(mClickListener);
        findViewById(R.id.btnAlarm).setOnClickListener(mClickListener);
    }

    /* 날짜 표시 */
    private void displayDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ((TextView) findViewById(R.id.txtDate)).setText(format.format(this.calendar.getTime()));
    }

    /* 시간 표시 */
    private void displayTime() {
        SimpleDateFormat format = new SimpleDateFormat("a HH:mm", Locale.getDefault());
        ((TextView) findViewById(R.id.txtTime)).setText(format.format(this.calendar.getTime()));
    }

    /* DatePickerDialog 호출
    private void showDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // 알람 날짜 설정
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, dayOfMonth);

                // 날짜 표시
                displayDate();
            }
        }, this.calendar.get(Calendar.YEAR), this.calendar.get(Calendar.MONTH), this.calendar.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    } */

    /* 알람 등록 */
    private void setAlarm() {
        // 알람 시간 설정
        this.calendar.set(Calendar.HOUR_OF_DAY, this.timePicker.getHour());
        this.calendar.set(Calendar.MINUTE, this.timePicker.getMinute());
        this.calendar.set(Calendar.SECOND, 0);

        // 현재일보다 이전이면 등록 실패
        if (this.calendar.before(Calendar.getInstance())) {
            Toast.makeText(this, "알람시간이 현재시간보다 이전일 수 없습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        // 알람 시간을 리스트에 추가
        alarmTimes.add(calendar);

        // Receiver 설정
        Intent intent = new Intent(this, AlarmReceiver.class);
        //intent.putExtra("content", "알람 등록 테스트");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmTimes.size() - 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
       // PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 알람 설정
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, this.calendar.getTimeInMillis(), pendingIntent);

        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(this.calendar.getTimeInMillis(), null), pendingIntent);
        //alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(this.calendar.getTimeInMillis() + 10000, null), pendingIntent1);

        // Toast 보여주기 (알람 시간 표시)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Toast.makeText(this, "Alarm : " + format.format(calendar.getTime()), Toast.LENGTH_LONG).show();
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            /*if (viewId == R.id.btnCalendar) { // btnCalendar 버튼의 리소스 ID로 변경
                // 달력
                showDatePicker();
            } else */
            if (viewId == R.id.btnAlarm) { // btnAlarm 버튼의 리소스 ID로 변경
                // 알람 등록
                setAlarm();
            }
        }
    };
}