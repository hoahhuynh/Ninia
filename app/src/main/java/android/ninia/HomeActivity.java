package android.ninia;

//TESTING GITHUB

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DecimalFormat;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements SensorEventListener{

    private Button walkingBtn, hikingBtn, cyclingBtn, weatherBtn, profileBtn;
    TextView steps, dist, cal;
    double stepsTaken = 0;
    String gender, feet, inches, weight, age;
    private Intent intent1;
    SharedPreferences preferences;
    boolean run = false;
    SensorManager sensorManager;
    DecimalFormat numberFormat = new DecimalFormat("#.0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        walkingBtn = (Button)findViewById(R.id.walkingButton);
        hikingBtn = (Button)findViewById(R.id.hikingButton);
        cyclingBtn = (Button)findViewById(R.id.cyclingButton);
        weatherBtn = (Button)findViewById(R.id.weatherButton);
        profileBtn = (Button)findViewById(R.id.profileButton);
        preferences = getSharedPreferences("com.android.ninia", Context.MODE_PRIVATE);
        steps = (TextView)findViewById(R.id.totalSteps);
        dist = (TextView)findViewById(R.id.totalDistance);
        cal = (TextView)findViewById(R.id.totalCalories);

        gender = preferences.getString("Gender", "Male");
        feet = preferences.getString("Feet", "5");
        inches = preferences.getString("Inches", "9");
        weight = preferences.getString("Weight", "180");
        age = preferences.getString("Age", "18");

        intent1 = new Intent(HomeActivity.this, PlotRouteActivity.class);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);


        /*if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            finish();
            return;
        }*/

        walkingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    /*intent1 = new Intent(HomeActivity.this, MapActivity.class);
                    intent1.putExtra("msg", "park");
                    startActivity(intent1);*/

                    startActivity(intent1);
                }
                else {
                    permissionChecking();
                }
            }
        });

        hikingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    /*intent1 = new Intent(HomeActivity.this, MapActivity.class);
                    intent1.putExtra("msg", "trail");
                    startActivity(intent1);*/

                    startActivity(intent1);
                }
                else {
                    permissionChecking();
                }
            }
        });

        cyclingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    /*intent1 = new Intent(HomeActivity.this, MapActivity.class);
                    intent1.putExtra("msg", "cycling park");
                    startActivity(intent1);*/

                    startActivity(intent1);
                }
                else {
                    permissionChecking();
                }
            }
        });

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1 = new Intent(HomeActivity.this,WeatherActivity.class);
                startActivity(intent1);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1 = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        run = true;
        Sensor count = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(count != null)
        {
            sensorManager.registerListener((SensorEventListener) this, count, SensorManager.SENSOR_DELAY_UI);
        }else
        {
            Toast.makeText(this,"Did not find step sensor",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        run = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(run)
        {
            steps.setText(String.valueOf(event.values[0]));
            stepsTaken = Double.parseDouble(steps.getText().toString());
            dist.setText(numberFormat.format(0.000426136 * stepsTaken) + " Mi");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void permissionChecking() {
        Dexter.withActivity(HomeActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        //startActivity(new Intent(HomeActivity.this, MapActivity.class));
                        //finish();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            AlertDialog.Builder denialAlert = new AlertDialog.Builder(HomeActivity.this);
                            denialAlert.setTitle("Permission is Permanently Denied")
                                    .setMessage("Permission to access device location is permanently denied. Go to Settings to allow the permission.")
                                    .setNegativeButton("Cancel", null)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package", getPackageName(), null));
                                        }
                                    }).show();
                        } else {
                            Toast.makeText(HomeActivity.this,"Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }
}
