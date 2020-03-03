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
    private TextView instructionsTxt;
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

        //db.entryDao().deleteAllEntries();
        //addSampleData(1, 6, "2020-03-2");

        //tilldela värden till view-variabler
        startTimerButton = findViewById(R.id.startTimerBtn);
        timerTxt = findViewById(R.id.timerTxt);
        numberOfPushupsET = findViewById(R.id.numberOfPushpsTxt);
        instructionsTxt = findViewById(R.id.instructionsTxt);
        infoTxt = findViewById(R.id.infoTxt);
        confirmButton = findViewById(R.id.confirmBtn);

        toggleInputFieldVisibility(false);

    }

    //döljer eller visar de views som endast ska synas när timern är klar
    public void toggleInputFieldVisibility(boolean show){

        if(show){

            instructionsTxt.setVisibility(View.INVISIBLE);
            infoTxt.setVisibility(View.VISIBLE);
            numberOfPushupsET.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
        }
        else{

            instructionsTxt.setVisibility(View.VISIBLE);
            infoTxt.setVisibility(View.INVISIBLE);
            numberOfPushupsET.setVisibility(View.INVISIBLE);
            confirmButton.setVisibility(View.INVISIBLE);
        }

    }

    //sparar antalet som användaren angett i databasen
    public void saveToDb(View view){

        String numberOfPushups = numberOfPushupsET.getText().toString();

        //kontrollerar att det är en integer som kommer in
        if(Validator.isInteger(numberOfPushups)) {

            int numberOfPushupsInt = Integer.valueOf(numberOfPushups);

                // om det inte finns några entries med dagens datum gör vi en ny
                if (db.entryDao().getSpecificEntry(todaysDate) == null) {
                    Entry entry = new Entry(numberOfPushupsInt, todaysDate);
                    db.entryDao().insertEntry(entry);
                }

                //annars ska den befintliga entryn uppdateras (endast om antalet
                // armhävningar är större än det som redan registrerats)
                else if((db.entryDao().getSpecificEntry(todaysDate).pushups < numberOfPushupsInt) ){

                    db.entryDao().updateSpecificEntry(todaysDate, numberOfPushupsInt);

                }

                //vi går tillbaka till mainaktiviteten
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

        }

    }

    //kopplat till "start timer" knappen
    //sätter igång en timer på 60 sekunder
    public void startTimer(View view){

        //om en timer redan körs stoppar vi den
        if(timerRunning){

            countDownTimer.cancel();
            timerRunning = false;
            startTimerButton.setText("START TIMER");
            timerTxt.setText("60");
            instructionsTxt.setVisibility(View.VISIBLE);

        }

        else{

            timerRunning = true;
            startTimerButton.setText("STOP TIMER");
            toggleInputFieldVisibility(false);
            instructionsTxt.setVisibility(View.INVISIBLE);

            countDownTimer = new CountDownTimer(60000, 1000) {

                //varje gång timern tickar uppdaterar vi texten
                @Override
                public void onTick(long ms) {
                        timerTxt.setText(String.valueOf(ms / 1000));
                }

                //när timern når sitt slut
                @Override
                public void onFinish() {
                    vibrate();
                    timerTxt.setText("0");
                    toggleInputFieldVisibility(true);
                    timerRunning = false;
                    startTimerButton.setText("START TIMER");
                }

            }.start();
        }
    }

    //när timern är slut vibrerar enheten
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void vibrate(){

        Vibrator vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        VibrationEffect vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrator.vibrate(vibrationEffect);

    }

}
