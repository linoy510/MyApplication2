package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class GameActivity1 extends AppCompatActivity {

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
                            t.setText(arr.get(0).getQuestion());
                            t1.setText(arr.get(0).getA1());
                            t2.setText(arr.get(0).getA2());
                            t3.setText(arr.get(0).getsuA3());
                            t4.setText(arr.get(0).getA4());



                        }
                    }

                });


    }
}