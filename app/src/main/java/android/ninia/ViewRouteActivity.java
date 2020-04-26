package android.ninia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewRouteActivity extends AppCompatActivity {

    private Button startBtn;
    private Intent intent;
    private TextView startLocation;
    private TextView endLocation;
    private TextView distance;
    private TextView duration;
    private TextView steps;
    //int numOfSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_route);

        startBtn = findViewById(R.id.viewRouteStartBTN);
        intent = new Intent(ViewRouteActivity.this, RunningActivity.class);
        startLocation = (TextView)findViewById(R.id.viewRoutestartLocationTV);
        endLocation = (TextView)findViewById(R.id.viewRoutegoalLocationTV);
        distance = (TextView)findViewById(R.id.viewRouteapproxDistTextView);
        duration = (TextView)findViewById(R.id.viewRouteDurationTextView);
        steps = (TextView)findViewById(R.id.viewRouteapproxStepsTextView);


        //numOfSteps = (int) (Integer.parseInt(Route.distance.replaceAll("\\D+","")) * 5280 / 2.3);
        //Log.i("Steps",String.valueOf(numOfSteps));
        startLocation.setText(Route.startLocation);
        endLocation.setText(Route.endLocation);
        duration.setText(Route.duration);
        distance.setText(Route.distance);
        steps.setText(Route.steps);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


    }
}
