package com.example.harman_mann_n01585147_problem_solving_assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button geo, browser;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geo = findViewById(R.id.btn_geo);
        browser = findViewById(R.id.btn_browser);

        geo.setOnClickListener(view -> {
            //trying to implement a way to grab location information for the system
/*            Uri locationURI = getIntent().getData();
            if (locationURI != null) {
                String latitudeString = locationURI.getQueryParameter("latti");
                String longitudeString = locationURI.getQueryParameter("longitude");
                if (latitudeString != null && longitudeString != null) {
                    latitude = Double.parseDouble(latitudeString);
                    longitude = Double.parseDouble(longitudeString);
                    // Use latitude and longitude here
                }
            }*/
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:99.9,-44.6?z=17"));
            startActivity(intent);
        });

        browser.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.humber.ca"));
            startActivity(intent);
        });


    }
}