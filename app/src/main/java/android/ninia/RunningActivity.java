package android.ninia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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
        caloriesBurned.setText("0");

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
                Intent intent = new Intent(RunningActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        caloriesBurned.setText(String.format("%.2f",Double.parseDouble(caloriesBurned.getText().toString())+ 0.06));
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                timer = SystemClock.elapsedRealtime() - chronometer.getBase();

                if(timer - extraTimer >= 3000)
                {

                    caloriesBurned.setText(String.format("%.2f",Double.parseDouble(caloriesBurned.getText().toString())+ 0.06));
                    steps.setText(String.valueOf((int)Double.parseDouble(steps.getText().toString()) + 2));
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

    }
}
