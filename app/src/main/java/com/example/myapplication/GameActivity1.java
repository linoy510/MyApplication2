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
import com.google.common.collect.Range;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameActivity1 extends AppCompatActivity implements View.OnClickListener{

    private int counter = 0;
    private int countQ = 0;

    private final static String[] questionCat = {"A1","A2","A3","A4","level","question","subject"};
    private ArrayList<QuestionData> arr = new ArrayList<>();
    TextView t;;
    Button t1;
    Button t2;
    Button t3;
    Button t4;
    private QuestionData currentQuestion = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);


        setUI();


        getQuestion();
    }

    private void setUI() {
         t = findViewById(R.id.textView10);
         t1 = findViewById(R.id.button3);
        t2 = findViewById(R.id.button4);
        t3 = findViewById(R.id.button5);
        t4 = findViewById(R.id.button6);

        t1.setOnClickListener(GameActivity1.this);
        t2.setOnClickListener(GameActivity1.this);
        t3.setOnClickListener(GameActivity1.this);
        t4.setOnClickListener(GameActivity1.this);

    }

    private void getQuestion() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Intent i = getIntent();
        int level = i.getIntExtra("levelSelected",1);

        Random r = new Random();
        int randIndex = r.nextInt(questionCat.length);
        String orderBy = questionCat[randIndex];
        firebaseFirestore.collection("questions").whereEqualTo("subject", "ארץ ישראל").whereEqualTo("level", level).orderBy(questionCat[randIndex]).limit(10)

    //    firebaseFirestore.collection("questions")
      //          .whereEqualTo("subject", "ארץ ישראל").whereEqualTo("level", level).limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<QuestionData> tArr = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                tArr.add(doc.toObject(QuestionData.class));
                            }

                            Log.d("READ FROM FB", "onComplete: " + arr.size());
                            Collections.shuffle(tArr);

                            arr.addAll(tArr.subList(0,tArr.size()));


                           // Toast.makeText(GameActivity1.this, arr.get(0).getQuestion(), Toast.LENGTH_SHORT).show();

                            // this means we received the questions...
                            // call displayQuestion

                            displayQuestion();





                        }
                        else
                            Log.d("cat arr", "onComplete: " + task.getException().getMessage());

                    }

                });


    }

    private void displayQuestion() {

        t.setText(arr.get(countQ).getQuestion());
        ArrayList<String> arr2 = arr.get(countQ).shuffleQuestions();
        currentQuestion = arr.get(countQ);

        t1.setText(arr2.get(0));
        t2.setText(arr2.get(1));
        t3.setText(arr2.get(2));
        t4.setText(arr2.get(3));

    }

    @Override
    public void onClick(View v)
    {
        Button b = (Button)v;
        String s = b.getText().toString();

        if(currentQuestion.checkAnswer(s))
        {
            Toast.makeText(GameActivity1.this, "you are right!", Toast.LENGTH_SHORT).show();
            counter++;
        }
        else Toast.makeText(GameActivity1.this, "you are wrong", Toast.LENGTH_SHORT).show();
        countQ++;
      //  Intent intent = new Intent(GameActivity1.this, GameActivity1.class);
       // startActivity(intent);
        displayQuestion();

    }
}