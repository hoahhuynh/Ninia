package android.ninia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WeatherActivity extends AppCompatActivity {

    TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        String apiKey = "0f466f82b10ed302759b311d35c20156";
        String zip = "30144";
        temp = findViewById(R.id.temperature);
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URLConnection connection = new URL("https://api.openweathermap.org/data/2.5/weather?zip="+zip
                    + "&units=imperial&APPID="+apiKey).openConnection();
            InputStream is = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();
            String string;
            while((string = bufferedReader.readLine()) != null)
                sb.append(string);
            String display = sb.toString().replaceAll("\\{", "");
            display = display.replaceAll("\\}","");
            display = display.replaceAll("\"","");
            display = display.replaceAll("\\[","");
            display = display.replaceAll("\\]","");
            display = display.replaceAll(",","\n");
            int index = display.indexOf("main:temp:")+5;
            int endex = display.indexOf("visibility:");
            display = display.substring(index, endex);
            display = display.replaceAll(":",": ");
            display = display.replace("feels_like", "feels like");
            display = display.replace("temp_min", "low");
            display = display.replace("temp_max", "high");
            temp.setText(display);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WeatherActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
