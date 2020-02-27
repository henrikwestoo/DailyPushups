package com.example.dailypushups;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ExerciseActivity extends AppCompatActivity {

    //dagens datum
    private String todaysDate;

    private boolean timerRunning;
    private CountDownTimer countDownTimer;

    private EntryDatabase db;

    //variabler för mina views (in order of appearance)
    private Button startTimerButton;
    private TextView timerTxt;
    private EditText numberOfPushupsET;
    private TextView infoTxt;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        timerRunning = false;

        //hämta dagens datum
        todaysDate = LocalDate.now().toString();

        db = EntryDatabase.getDbInstance(this);

        //tilldela värden till view-variabler
        startTimerButton = findViewById(R.id.startTimerBtn);
        timerTxt = findViewById(R.id.timerTxt);
        numberOfPushupsET = findViewById(R.id.numberOfPushpsTxt);
        infoTxt = findViewById(R.id.infoTxt);
        confirmButton = findViewById(R.id.confirmBtn);

        //dölj de views som endast ska synas när timern är klar
        toggleInputFieldVisibility(false);

    }

    public void toggleInputFieldVisibility(boolean show){

        if(show){

            infoTxt.setVisibility(View.VISIBLE);
            numberOfPushupsET.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
        }
        else{

            infoTxt.setVisibility(View.INVISIBLE);
            numberOfPushupsET.setVisibility(View.INVISIBLE);
            confirmButton.setVisibility(View.INVISIBLE);
        }

    }

    public void saveToDb(View view){

        String numberOfPushups = numberOfPushupsET.getText().toString();

        if(Validator.isInteger(numberOfPushups)) {

            int numberOfPushupsInt = Integer.valueOf(numberOfPushups);

            Log.d("tag", numberOfPushups);

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

    public void startTimer(View view){

        if(timerRunning){

            countDownTimer.cancel();
            timerRunning = false;
            startTimerButton.setText("START TIMER");
            timerTxt.setText("1:00");

        }

        else{

            timerRunning = true;
            startTimerButton.setText("STOP TIMER");
            toggleInputFieldVisibility(false);

            countDownTimer = new CountDownTimer(60000, 1000) {

                @Override
                public void onTick(long ms) {
                    if (ms / 1000 < 10) {

                        timerTxt.setText(String.valueOf("0:0" + ms / 1000));
                    } else {
                        timerTxt.setText(String.valueOf("0:" + ms / 1000));
                    }
                }

                @Override
                public void onFinish() {
                    vibrate();
                    timerTxt.setText("0:00");
                    toggleInputFieldVisibility(true);
                    timerRunning = false;
                    startTimerButton.setText("START TIMER");
                }

            }.start();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void vibrate(){

        Vibrator vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        VibrationEffect vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrator.vibrate(vibrationEffect);

    }

}
