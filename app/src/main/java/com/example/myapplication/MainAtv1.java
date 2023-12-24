package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class MainAtv1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_atv1);
    }

    public void onClick(View view)
    {
     //   Intent intent = new Intent(MainAtv1.this, GameActivity1.class);
      //  startActivity(intent);

        Spinner spinner = findViewById(R.id.spAt1Level);
        spinner.setVisibility(View.VISIBLE);
    }
}