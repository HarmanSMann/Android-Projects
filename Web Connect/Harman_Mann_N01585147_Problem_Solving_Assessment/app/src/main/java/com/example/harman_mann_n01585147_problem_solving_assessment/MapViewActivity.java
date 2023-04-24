package com.example.harman_mann_n01585147_problem_solving_assessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MapViewActivity extends AppCompatActivity {
    TextView geoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        geoText = findViewById(R.id.geoText);
        String[] geo =  getIntent().getData().toString().split("[:,?]");
        String result = "";
        if (geo.length == 3) {
            result = "latitude: " + geo[1] + " longitude: " + geo[2];
        } else if (geo.length == 4) {
            result = "latitude: " + geo[1] + " longitude: " + geo[2] + " zoom: " + geo[3].split("=")[1];
        } else {
            result = "Empty Geo Info";
        }
        geoText.setText(result);
    }
}