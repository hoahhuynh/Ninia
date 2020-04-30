package android.ninia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.lang.reflect.Array;

public class ProfileActivity extends AppCompatActivity {

    String gender, ft, inches, weight, age;
    Spinner genderSpin, ftSpin, inSpin, ageSpin;
    EditText weightEDT;
    Button save;
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
        save = (Button)findViewById(R.id.profSaveBTN);
        weightEDT = (EditText)findViewById(R.id.weightEDT);

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


        weightEDT.setText(preferences.getString("Weight", "150"));
        if(preferences.getString("Gender", "Male").equalsIgnoreCase("Male"))
        {
            genderSpin.setSelection(0);
        }
        else
        {
            genderSpin.setSelection(1);
        }
        ftSpin.setSelection(Integer.parseInt(preferences.getString("Feet", "5")) - 3);
        inSpin.setSelection(Integer.parseInt(preferences.getString("Inches", "0")) % 11);
        ageSpin.setSelection(Integer.parseInt(preferences.getString("Age", "18")) % 18);


        genderSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
                prefEditor.putString("Gender", gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ftSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ft = parent.getItemAtPosition(position).toString();
                prefEditor.putString("Feet", ft);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        inSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inches = parent.getItemAtPosition(position).toString();
                prefEditor.putString("Inches", inches);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ageSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age = parent.getItemAtPosition(position).toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(ProfileActivity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefEditor.putString("Weight", weightEDT.getText().toString());
                prefEditor.putString("Age", age);
                prefEditor.apply();
                prefEditor.commit();

                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
