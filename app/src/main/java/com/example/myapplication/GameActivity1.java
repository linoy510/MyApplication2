package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class GameActivity1 extends AppCompatActivity implements View.OnClickListener{
    private int counter = 0;
    private int countQ = 0;
    private QuestionData currentQuestion = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);


        getQuestion();
    }

    private void getQuestion() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("questions")
                .whereEqualTo("subject", "ארץ ישראל").whereEqualTo("level", 1).limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<QuestionData> arr = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                arr.add(doc.toObject(QuestionData.class));
                            }
                           // Toast.makeText(GameActivity1.this, arr.get(0).getQuestion(), Toast.LENGTH_SHORT).show();
                            TextView t = findViewById(R.id.textView10);
                            Button t1 = findViewById(R.id.button3);
                            Button t2 = findViewById(R.id.button4);
                            Button t3 = findViewById(R.id.button5);
                            Button t4 = findViewById(R.id.button6);

                            t1.setOnClickListener(GameActivity1.this);
                            t2.setOnClickListener(GameActivity1.this);
                            t3.setOnClickListener(GameActivity1.this);
                            t4.setOnClickListener(GameActivity1.this);

                            t.setText(arr.get(countQ).getQuestion());
                            ArrayList<String> arr2 = arr.get(countQ).shuffleQuestions();
                            currentQuestion = arr.get(countQ);

                            t1.setText(arr2.get(0));
                            t2.setText(arr2.get(1));
                            t3.setText(arr2.get(2));
                            t4.setText(arr2.get(3));



                        }

                    }

                });


    }

    @Override
    public void onClick(View v)
    {
        Button b = (Button)v;
        String s = b.getText().toString();

        if(currentQuestion.checkAnswer(s))
        {
            counter++;
        }
        countQ++;
      //  Intent intent = new Intent(GameActivity1.this, GameActivity1.class);
       // startActivity(intent);
        getQuestion();

    }
}