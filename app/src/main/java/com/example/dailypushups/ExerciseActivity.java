package com.example.dailypushups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
    }

    public void saveToDb(View view){

        EditText numberOfPushupsET = (EditText) findViewById(R.id.NumberOfPushpsTxt);
        String numberOfPushups = numberOfPushupsET.getText().toString();

        int numberOfPushupsInt = Integer.valueOf(numberOfPushups);

        Log.d("tag", numberOfPushups);

        EntryDatabase db = EntryDatabase.getDbInstance(this);
        Entry entry = new Entry();

        entry.pushups = numberOfPushupsInt;
        entry.date = "2020-02-21";

        Log.d("tag", "insert started");

        db.entryDao().insertEntry(entry);

        Log.d("tag", "insert finished");

    }

    public void testShow(View view){

        EntryDatabase db = EntryDatabase.getDbInstance(this);

        String number  = String.valueOf(db.entryDao().getLatest().pushups);
        Log.d("tag", number);

        List<Entry> entries =  db.entryDao().getAll();

        for(Entry entry : entries){

            Log.d("tag", String.valueOf(entry.id) + entry.pushups + entry.date);

        }


    }

}
