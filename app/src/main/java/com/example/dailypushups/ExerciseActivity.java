package com.example.dailypushups;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ExerciseActivity extends AppCompatActivity {

    String todaysDate;
    EditText numberOfPushupsET;
    TextView timerTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        todaysDate = LocalDate.now().toString();

        timerTxt = (TextView) findViewById(R.id.timerTxt);
        numberOfPushupsET = (EditText) findViewById(R.id.NumberOfPushpsTxt);

    }

    public void saveToDb(View view){

        String numberOfPushups = numberOfPushupsET.getText().toString();

        if(Validator.isInteger(numberOfPushups)) {

            int numberOfPushupsInt = Integer.valueOf(numberOfPushups);

            Log.d("tag", numberOfPushups);

            EntryDatabase db = EntryDatabase.getDbInstance(this);

            Log.d("tag", "insert started");

            // om det inte finns några entries med dagens datum
            if(db.entryDao().getSpecificEntry(todaysDate) == null) {
                Entry entry = new Entry(numberOfPushupsInt, todaysDate);
                db.entryDao().insertEntry(entry);
            }

            //annars ska entrien med dagens datum uppdateras
            else{

                db.entryDao().updateSpecificEntry(todaysDate, numberOfPushupsInt);

            }

            Log.d("tag", "insert finished");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }

    }

    public void testShow(View view){

        new CountDownTimer(60000, 1000){

            @Override
            public void onTick(long ms){
                timerTxt.setText(String.valueOf("0:"+ms /1000));
            }
            @Override
            public void onFinish(){
                timerTxt.setText("0:00!!!");
            }


        }.start();

    }

}
