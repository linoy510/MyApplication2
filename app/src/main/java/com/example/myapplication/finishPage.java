package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

public class finishPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_page);
        ImageView view = findViewById(R.id.imageView);
        Intent i1 = getIntent();
        String s = i1.getStringExtra("winOrLose");
        if(s.equals("lose"))
            view.setImageResource(R.drawable.youlose);
        else
            view.setImageResource(R.drawable.youwin);
        CountDownTimer cdt = new CountDownTimer(2000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(finishPage.this, GameActivity1.class);
                startActivity(intent);
            }
        }.start();

    }
}