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
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RunningActivity extends AppCompatActivity {

    Button pauseBtn, cancelBtn;
    Chronometer chronometer;
    long pauseOffset = 0;
    boolean isRunning = false;
    TextView caloriesBurned;
    TextView steps;
    TextView distance;
    TextView currentLocation;
    TextView goalLocation;
    TextView percentage;
    ProgressBar progress;
    int maxDistance = 0;
    int progPercentage = 0;
    int totalSteps = 0;
    double totalDistance = 0;
    double totalCalories = 0;
    long timer = 0, extraTimer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        currentLocation = (TextView) findViewById(R.id.startLocationTV);
        goalLocation = (TextView) findViewById(R.id.goalLocationTV);
        chronometer = (Chronometer)findViewById(R.id.chronometer);
        pauseBtn = (Button)findViewById(R.id.runningPauseBtn);
        cancelBtn = (Button)findViewById(R.id.runningStopBtn);
        caloriesBurned = (TextView)findViewById(R.id.approxCalTextView);
        steps = (TextView)findViewById(R.id.approxStepsTextView);
        distance = (TextView)findViewById(R.id.approxDistTextView);
        progress = (ProgressBar)findViewById(R.id.progressBar);
        percentage = (TextView)findViewById(R.id.progressPercentage);
        totalCalories = Double.parseDouble(caloriesBurned.getText().toString());
        maxDistance = getDistance();
        progress.setMax(100);

        currentLocation.setText(Route.startLocation);
        goalLocation.setText(Route.endLocation);
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
                    totalSteps = (int)(Integer.parseInt(steps.getText().toString()) + 4.9);
                    totalCalories = totalSteps * 0.09;
                    totalDistance += 0.0024;
                    caloriesBurned.setText(String.valueOf((int)totalCalories));
                    steps.setText(String.valueOf(totalSteps));
                    distance.setText(String.format("%.2f",totalDistance));
                    progPercentage = (int) (Double.parseDouble(String.valueOf(distance.getText().toString())) / maxDistance * 1000);
                    progress.setProgress(progPercentage);
                    percentage.setText(progPercentage + "%");
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

    public int getDistance()
    {
        String digits = "";
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(Route.distance);

        while(matcher.find())
        {
            digits += matcher.group();
        }
        return Integer.valueOf(digits);
    }
}

