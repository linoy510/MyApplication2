package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.common.collect.Range;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameActivity1 extends AppCompatActivity implements View.OnClickListener,IGetQuestion,IGameView{


    private int counter = 0;
    public static int countQ = 0;
    public static int countQ2 = -1;
    public static int countWrong = 0;
    public final static String[] questionCat = {"A1","A2","A3","A4","level","question","subject"};
    private ArrayList<QuestionData> arr = new ArrayList<>();

    TextView t;
    Button t1;
    Button t2;
    Button t3;
    Button t4;
    int level =1;
    String typeGame = "";
    public static QuestionData currentQuestion = null;

    private String gameID="";

    OnlineGameManager onlineGameManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        //AppBarLayout t = new AppBarLayout(View.GONE());

        Intent i1 = getIntent();
        level = i1.getIntExtra("levelSelected",1);
        Intent i2 = getIntent();
        typeGame = i2.getStringExtra("game type");
        setUI();

        if(!typeGame.equals("online"))
        {
            firebaseClass f = new firebaseClass();
            f.setActivity(this);
            f.getQuestion(level, questionCat);
        }
        else
        {

            gameID = getIntent().getStringExtra("game id");
            if(gameID == null) gameID = getIntent().getStringExtra("gameId");
            FirebaseFirestore fb = FirebaseFirestore.getInstance();
            DocumentReference docRef= fb.collection("questions").document(gameID);
            String player = getIntent().getStringExtra("player");
            onlineGameManager = new OnlineGameManager(this,gameID,player,level);
            onlineGameManager.startGame();
        }


    }

    private void setUI()
    {

        t = findViewById(R.id.textView10);
        t1 = findViewById(R.id.button3);
        t2 = findViewById(R.id.button4);
        t3 = findViewById(R.id.button5);
        t4 = findViewById(R.id.button6);

    //    t.setText(arr.get(countQ).getQuestion());
        t1.setOnClickListener(GameActivity1.this);
        t2.setOnClickListener(GameActivity1.this);
        t3.setOnClickListener(GameActivity1.this);
        t4.setOnClickListener(GameActivity1.this);

    }





    private void displayQuestion()
    {
        if(countQ < 11) {
            t.setText(arr.get(countQ).getQuestion());
            ArrayList<String> arr2 = arr.get(countQ).shuffleQuestions();
            currentQuestion = arr.get(countQ);

            t1.setText(arr2.get(0));
            t2.setText(arr2.get(1));
            t3.setText(arr2.get(2));
            t4.setText(arr2.get(3));
        }
        else
        {
            //  Intent intent = new Intent(GameActivity1.this, GameActivity1.class);
            // startActivity(intent);
        }

    }

    @Override
    public void onClick(View v)
    {
        Button b = (Button)v;
        String s = b.getText().toString();
        String check = "false";
        if(currentQuestion.checkAnswer(s))
        {
            Toast.makeText(GameActivity1.this, "you are right!", Toast.LENGTH_SHORT).show();
            countQ++;
            check = ("true");
        }
        else Toast.makeText(GameActivity1.this, "you are wrong", Toast.LENGTH_SHORT).show();
        //countQ++;
        if(!typeGame.equals("online"))
           displayQuestion();

        else //online
        // make sure other calls sgart game only once
        // other pudate other anwer and countQ2
        {
            if(getIntent().getStringExtra("player").equals("host"))
            {
                if(countQ >= 10)
                {
                    check = ("finish");
                }
                onlineGameManager.setNextQuestionInGameRoom(check);
            }

            else // other
            {
                //onlineGameManager.startGame();
                onlineGameManager.setOtherResult(check, currentQuestion);

            }
        }
    }

    @Override
    public void questionsFromFirebase(boolean result, ArrayList<QuestionData> arrResult) {

        if(result) // this means success
        {
            Log.d("READ FROM FB", "onComplete: " + arr.size());
            Collections.shuffle(arrResult);

            arr.addAll(arrResult.subList(0, arrResult.size()-1));
            this.displayQuestion();
        }
        else
            Toast.makeText(this,"failed to read data " ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getQuestionFromListenForChanges(QuestionData d, String questionStatus) {

    }

    @Override
    public void displayQuestion(QuestionData q, int currentPlayer, String questionStatus) {
       /*if(getIntent().getStringExtra("player").equals("other") && questionStatus.equals("false") && currentPlayer == 1 ||
                getIntent().getStringExtra("player").equals("host") && questionStatus.equals("false") && currentPlayer == 0)
        {
            t1.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);
            t3.setVisibility(View.VISIBLE);
            t4.setVisibility(View.VISIBLE);
        }*/
        currentQuestion = q;//arr.get(countQ);
        t.setText(currentQuestion.getQuestion());
        ArrayList<String> arr2 = currentQuestion.shuffleQuestions();

        t1.setText(arr2.get(0));
        t2.setText(arr2.get(1));
        t3.setText(arr2.get(2));
        t4.setText(arr2.get(3));
        if(countQ2 != -1) {
            if (currentPlayer == 1 && getIntent().getStringExtra("player").equals("host") || currentPlayer == 0 && getIntent().getStringExtra("player").equals("other"))
            {
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.INVISIBLE);
                t4.setVisibility(View.INVISIBLE);

            }
            else
            {

                t1.setVisibility(View.VISIBLE);
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                t4.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            t1.setVisibility(View.INVISIBLE);
            t2.setVisibility(View.INVISIBLE);
            t3.setVisibility(View.INVISIBLE);
            t4.setVisibility(View.INVISIBLE);
        }

    }


    public void getQuestionFromListenForChanges(QuestionData d)
    {

    }

    @Override
    public void showMessage(String message)
    {

    }
}