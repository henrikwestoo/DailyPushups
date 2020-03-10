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
    private Button historyBtn;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //skapar ett alarm som kommer aktivera en notifikation som 24 timmar
        AlarmCreator.setAlarm(this,1440);

        //vi behöver dagens datum för att ta fram förra gångens antal armhävningar
        todaysDate = LocalDate.now().toString();

        db = EntryDatabase.getDbInstance(this);

        //variabler för views
        completedInfoTxt = findViewById(R.id.completedInfoTxt);
        historyBtn = findViewById(R.id.historyBtn);

        Entry entry;
        //om databasen inte är tom vill vi uppvisa hur många armhävningar
        //användaren gjorde förra gången
        if(!db.entryDao().getAll().isEmpty()) {

            entry = db.entryDao().getLatest();

            //om användaren har gjort armhävningar idag så ändras meddelandet
            if (dailyCompleted()) {

                completedInfoTxt.setText("You have done your daily pushups!");
            }

            TextView yesterdaysPushups = findViewById(R.id.yesterdaysPushupsTxt);
            yesterdaysPushups.setText(String.valueOf(entry.pushups));

        }

        //om du inte gjort fler än 1 entry kan du inte se din historik
        //detta eftersom grafen inte skulle bli så fin utan data
        if(!(db.entryDao().getAll().size() > 1)){

            historyBtn.setVisibility(View.INVISIBLE);

        }

    }

    //bunden till "start" knappen
    public void goToExercise(View view){

        Intent intent = new Intent(this, ExerciseActivity.class);
        startActivity(intent);

    }

    //bunden till "history" knappen
    public void goToHistory(View view){

        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);

    }

    //kontrollerar om användaren gjort armhävningar idag
    public boolean dailyCompleted(){

        boolean completed = true;

        if(db.entryDao().getSpecificEntry(todaysDate) == null){

            completed = false;

        }

        return completed;

    }

}
