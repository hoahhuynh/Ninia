package android.ninia;

//TESTING GITHUB

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.Map;

public class HomeActivity extends AppCompatActivity implements SensorEventListener {

    boolean run = false;
    SensorManager sensorManager;
    TextView steps;
    Button walkingBtn, hikingBtn, cyclingBtn, weatherBtn;
    Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        walkingBtn = (Button)findViewById(R.id.walkingButton);
        hikingBtn = (Button)findViewById(R.id.hikingButton);
        cyclingBtn = (Button)findViewById(R.id.cyclingButton);
        weatherBtn = (Button)findViewById(R.id.weatherButton);

        steps = findViewById(R.id.totalSteps);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        intent1 = new Intent(HomeActivity.this, PlotRouteActivity.class);

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

    private void permissionChecking() {
        Dexter.withActivity(HomeActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        startActivity(new Intent(HomeActivity.this, MapActivity.class));
                        finish();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater main_menu = getMenuInflater();
        main_menu.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(run)
        {
            steps.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
