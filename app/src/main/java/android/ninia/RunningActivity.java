package android.ninia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RunningActivity extends AppCompatActivity {

    private Button pauseBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        pauseBtn = findViewById(R.id.runningPauseBtn);
        cancelBtn = findViewById(R.id.runningStopBtn);
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pauseBtn.getText().equals("START"))
                {
                    pauseBtn.setText("PAUSE");
                }else
                {
                    pauseBtn.setText("START");
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirmCancel();
                Intent intent = new Intent(RunningActivity.this, HomeActivity.class);
                startActivity(intent);
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
