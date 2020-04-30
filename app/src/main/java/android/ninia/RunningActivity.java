package android.ninia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class RunningActivity extends AppCompatActivity {

    Button pauseBtn, cancelBtn;
    Chronometer chronometer;
    long pauseOffset = 0;
    boolean isRunning = false;
    TextView caloriesBurned;
    TextView steps;
    TextView distance;
    int totalSteps = 0;
    double totalCalories = 0;
    long timer = 0, extraTimer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        chronometer = (Chronometer)findViewById(R.id.chronometer);
        pauseBtn = (Button)findViewById(R.id.runningPauseBtn);
        cancelBtn = (Button)findViewById(R.id.runningStopBtn);
        caloriesBurned = (TextView)findViewById(R.id.approxCalTextView);
        steps = (TextView)findViewById(R.id.approxStepsTextView);
        distance = (TextView)findViewById(R.id.approxDistTextView);
        totalCalories = Double.parseDouble(caloriesBurned.getText().toString());

        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        chronometer.start();

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pauseBtn.getText().equals("START") && !isRunning)
                {
                    pauseBtn.setText("PAUSE");
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    isRunning = true;
                }else
                {
                    pauseBtn.setText("START");
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    isRunning = false;
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;

                confirmCancel();
            }
        });

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                timer = SystemClock.elapsedRealtime() - chronometer.getBase();

                if(timer - extraTimer >= 3000)
                {
                    totalSteps = (int)(Integer.parseInt(steps.getText().toString()) + 2.3);
                    totalCalories = totalSteps * 0.06;
                    caloriesBurned.setText(String.valueOf((int)totalCalories));
                    steps.setText(String.valueOf(totalSteps));
                    distance.setText(String.format("%.2f",Double.parseDouble(distance.getText().toString()) + 0.01));
                    extraTimer += 3000;
                }

            }
        });
    }

    public void confirmCancel()
    {
        AlertDialog.Builder stop = new AlertDialog.Builder(RunningActivity.this);
        stop.setTitle("Stop");
        stop.setMessage("Do you really want to stop?");
        stop.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RunningActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        stop.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        stop.show();
    }
}
