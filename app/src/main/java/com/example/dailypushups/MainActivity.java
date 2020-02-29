package com.example.dailypushups;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EntryDatabase db;
    private String todaysDate;

    private TextView completedInfoTxt;
    private Button startBtn;
    private Button historyBtn;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  AlarmCreator.setAlarm(this,1);

        todaysDate = LocalDate.now().toString();
        db = EntryDatabase.getDbInstance(this);

        completedInfoTxt = findViewById(R.id.completedInfoTxt);
        historyBtn = findViewById(R.id.historyBtn);

        Entry entry;

        List<Entry> test = db.entryDao().getAll();

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

        //om du inte gjort fler än 1 entry kan du inte se din historik
        if(!(db.entryDao().getAll().size() > 1)){

            historyBtn.setVisibility(View.INVISIBLE);

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
