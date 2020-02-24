package com.example.dailypushups;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.time.LocalDate;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ExerciseActivity extends AppCompatActivity {

    String todaysDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        todaysDate = LocalDate.now().toString();
    }

    public void saveToDb(View view){

        EditText numberOfPushupsET = (EditText) findViewById(R.id.NumberOfPushpsTxt);
        String numberOfPushups = numberOfPushupsET.getText().toString();

        if(Validator.isInteger(numberOfPushups)) {

            int numberOfPushupsInt = Integer.valueOf(numberOfPushups);

            Log.d("tag", numberOfPushups);

            EntryDatabase db = EntryDatabase.getDbInstance(this);
            Entry entry = new Entry();

            entry.pushups = numberOfPushupsInt;
            entry.date = todaysDate;

            Log.d("tag", "insert started");

            // om det inte finns några entries med dagens datum
            db.entryDao().insertEntry(entry);
            //annars ska entrien med dagens datum uppdateras

            Log.d("tag", "insert finished");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }

    }

    public void testShow(View view){

        EntryDatabase db = EntryDatabase.getDbInstance(this);

        db.entryDao().deleteAllEntires();


    }

}
