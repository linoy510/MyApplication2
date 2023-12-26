package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainAtv1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
 Spinner spLevels;
 ArrayList<String> Levels;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_atv1);
        spLevels = (Spinner) (findViewById(R.id.spAt1Level));
        Levels = new ArrayList<String>();
        Levels.add("select level");
        Levels.add("Level 1");
        Levels.add("Level 2");
        Levels.add("Level 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Levels);
        spLevels.setAdapter(dataAdapter);
        spLevels.setOnItemSelectedListener(this);


    }

    public void onClick(View view)
    {

        Spinner spinner = findViewById(R.id.spAt1Level);
        spinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if(position!=0) {
            int level = position;
            Intent intent = new Intent(MainAtv1.this, GameActivity1.class);
            intent.putExtra("levelSelected", level);
            startActivity(intent);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}