package com.example.myapplication;

import static com.example.myapplication.AppConstants.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainAtv1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spLevels;
    ArrayList<String> Levels;
    String gameid = "";

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_publish) {

            Intent intent = new Intent(MainAtv1.this, MainActivityPublish.class);
            startActivity(intent);
        }
        return true;
    }


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


    public void onClick(View view) {

        Spinner spinner = findViewById(R.id.spAt1Level);
        spinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0) {
            int level = position;
            Intent intent = new Intent(MainAtv1.this, GameActivity1.class);
            intent.putExtra("levelSelected", level);
            intent.putExtra("GAME_TYPE", PRACTICE);
            startActivity(intent);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    public void onClicked(View view) {

    }

    public void addRoomToFB(View view) {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();

        roomGame rg = new roomGame();
        fb.collection("GameRoom").add(rg).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                TextView codeTextView = findViewById(R.id.codeTextView);
                ImageView shareImage = findViewById(R.id.shareicon2);
                gameid = documentReference.getId();
                codeTextView.setText("Your game code is: " + gameid + " .Share it with your friends!!!");
                codeTextView.setVisibility(View.VISIBLE);
                shareImage.setVisibility(View.VISIBLE);

                Log.d("ONSUCCESS", "id:" + documentReference.getId());
                //shareWithFriends(view);
            }


        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ONFAILER", "onFailure: " + e.getMessage());
            }
        });


    }


    public void shareWithFriends(View view)
    {
        // implicit intent - אינטרנט מרומז
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        //this action indicates that you want to send data.
        shareIntent.setType("text/plain"); // for sharing text
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello! THIS IS THE CODE FOR THE GAME: " + gameid + " JOIN THE GAME! THE CREATOR IS WAITING FOR YOU!");
        startActivity(Intent.createChooser(shareIntent, "Share using"));
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, @Nullable Intent data)
    {
        super.onActivityResult(reqCode, resCode, data);
        Intent i = new Intent(this, GameActivity1.class);
        i.putExtra("game id", gameid);
        i.putExtra("player", Host);
        i.putExtra(game_config, two_phone);
        startActivity(i);
    }

    public void joinClicked(View view)
    {
        EditText enterCode = findViewById(R.id.editSend);
        Button clickJoin = findViewById(R.id.nextClick);
        enterCode.setVisibility(View.VISIBLE);
        clickJoin.setVisibility(View.VISIBLE);
    }

    public void clickToNext(View view)
    {
        EditText eCode = findViewById(R.id.editSend);
        String gameCode = eCode.getText().toString();
        Intent i = new Intent(this, GameActivity1.class);
        i.putExtra("gameId", gameCode);
        i.putExtra("player", Other);
        i.putExtra(game_config, two_phone);
        startActivity(i);
    }
}