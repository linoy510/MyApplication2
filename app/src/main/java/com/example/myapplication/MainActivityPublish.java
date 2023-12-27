package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPublish extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spTopic;
    ArrayList<String> Topics;
    Spinner spLevel;
    ArrayList<String> Levels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_publish);
        spTopic = (Spinner) (findViewById(R.id.spinnerTopic));
        Topics = new ArrayList<String>();
        Topics.add("בחר נושא");
        Topics.add("חיות");
        Topics.add("אוכל");
        Topics.add("ארץ ישראל");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Topics);
        spTopic.setAdapter(dataAdapter1);
        spTopic.setOnItemSelectedListener(this);
        spLevel = (Spinner) (findViewById(R.id.spLevel));
        Levels = new ArrayList<String>();
        Levels.add("select level");
        Levels.add("Level 1");
        Levels.add("Level 2");
        Levels.add("Level 3");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Levels);
        spLevel.setAdapter(dataAdapter2);
        spLevel.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}