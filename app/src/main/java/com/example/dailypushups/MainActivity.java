package com.example.dailypushups;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EntryDatabase db;
    private String todaysDate;


    private TextView completedInfoTxt;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //alarmManager.cancel(pendingIntent);

        long timer = 1000 * 60;
        Log.d("tagalarm", String.valueOf(timer));

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timer, pendingIntent);
        Log.d("tag", "ALARM SET2  :d");

        todaysDate = LocalDate.now().toString();
        db = EntryDatabase.getDbInstance(this);

        completedInfoTxt = findViewById(R.id.completedInfoTxt);

        Entry entry;

        if(!db.entryDao().getAll().isEmpty()) {

            if (dailyCompleted()) {

                completedInfoTxt.setText("You have done your daily pushups!");
                entry = db.entryDao().getNextToLatest();

            } else {

                entry = db.entryDao().getLatest();
            }

            TextView yesterdaysPushups = (TextView) findViewById(R.id.yesterdaysPushupsTxt);
            yesterdaysPushups.setText(String.valueOf(entry.pushups));

        }

    }

    public void goToExercise(View view){

        Intent intent = new Intent(this, ExerciseActivity.class);
        startActivity(intent);

    }

    public void goToHistory(View view){

        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);

    }

    public boolean dailyCompleted(){

        boolean completed = true;

        if(db.entryDao().getSpecificEntry(todaysDate) == null){

            completed = false;

        }

        return completed;

    }

}
