package android.ninia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewRouteActivity extends AppCompatActivity {

    private Button startBtn;
    private Intent intent;
    private TextView startLocation;
    private TextView endLocation;
    private TextView distance;
    private TextView duration;
    private TextView steps;
    private TextView caloriesToBurn;
    double maxDistance = 0;
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
        caloriesToBurn = (TextView)findViewById(R.id.approxCalTextView);
        maxDistance = getDistance();


        //numOfSteps = (int) (Integer.parseInt(Route.distance.replaceAll("\\D+","")) * 5280 / 2.3);
        //Log.i("Steps",String.valueOf(numOfSteps));

        startLocation.setText(Route.startLocation);
        endLocation.setText(Route.endLocation);
        duration.setText(Route.duration);
        distance.setText(Route.distance);
        caloriesToBurn.setText(String.valueOf((int)(maxDistance * 8.5)));
        steps.setText(String.format("%.2f",Double.parseDouble(Route.steps)));

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewRouteActivity.this, MapActivity.class);
        startActivity(intent);
    }
}
