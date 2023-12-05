package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPublish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Spinner spinner;

        List<String> smartphones;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_publish);
        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);
        // Spinner Drop down elements

        smartphones = new ArrayList<String>();
        smartphones.add("חיות");
        smartphones.add("אוכל");
        smartphones.add("ארץ ישראל");
    }
}