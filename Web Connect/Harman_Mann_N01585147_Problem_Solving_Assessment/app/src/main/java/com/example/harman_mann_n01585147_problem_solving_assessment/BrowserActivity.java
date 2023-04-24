package com.example.harman_mann_n01585147_problem_solving_assessment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.wv_container, new WV_Fragment(getIntent().getData().toString())).commit();

    }
}