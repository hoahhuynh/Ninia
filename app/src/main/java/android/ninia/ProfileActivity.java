package android.ninia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import java.lang.reflect.Array;

public class ProfileActivity extends AppCompatActivity {

    String gender, ft, inches, weight, age;
    Spinner genderSpin, ftSpin, inSpin, ageSpin;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        preferences = getSharedPreferences("com.android.ninia", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        genderSpin = (Spinner)findViewById(R.id.genderSPNR);
        ftSpin = (Spinner)findViewById(R.id.feetSPNR);
        inSpin = (Spinner)findViewById(R.id.inchesSpinner);
        ageSpin = (Spinner)findViewById(R.id.ageSPNR);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,R.array.gender_values, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> feetAdapter = ArrayAdapter.createFromResource(this,R.array.feet_values, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> inchesAdapter = ArrayAdapter.createFromResource(this,R.array.inches_values, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> ageAdapter = ArrayAdapter.createFromResource(this,R.array.age_values, android.R.layout.simple_spinner_item);

        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inchesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderSpin.setAdapter(genderAdapter);
        ftSpin.setAdapter(feetAdapter);
        inSpin.setAdapter(inchesAdapter);
        ageSpin.setAdapter(ageAdapter);

        genderSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ftSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ft = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        inSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inches = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ageSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        prefEditor.putString("Gender", gender);
        prefEditor.putString("Feet", ft);
        prefEditor.putString("Inches", inches);
        prefEditor.putString("Weight", weight);
        prefEditor.putString("Age", age);
        prefEditor.apply();

        preferences.getString("Gender", "Male");
        preferences.getString("Feet", "4");
        preferences.getString("Inches", "0");
        preferences.getString("Weight", "100");
        preferences.getString("Age", "18");


    }
}
