package com.example.dailypushups;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String todaysDate = LocalDate.now().toString();

        //databasinstansiering för att kontrollera gårdagens armhävningar
        EntryDatabase db = EntryDatabase.getDbInstance(this);
        Entry entry;

        if(!db.entryDao().getAll().isEmpty()) {

            if(db.entryDao().getLatest().date.equals(todaysDate))
            {
                entry = db.entryDao().getNextToLatest();
            }

            else
            {
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

}
