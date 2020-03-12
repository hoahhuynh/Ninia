package android.ninia;

//TESTING GITHUB

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {

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

        /*if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            finish();
            return;
        }*/

        walkingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    intent1 = new Intent(HomeActivity.this, MapActivity.class);
                    intent1.putExtra("msg", "park");
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
                    intent1 = new Intent(HomeActivity.this, MapActivity.class);
                    intent1.putExtra("msg", "trail");
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
                    intent1 = new Intent(HomeActivity.this, MapActivity.class);
                    intent1.putExtra("msg", "cycling park");
                    startActivity(intent1);
                }
                else {
                    permissionChecking();
                }
            }
        });
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
}
