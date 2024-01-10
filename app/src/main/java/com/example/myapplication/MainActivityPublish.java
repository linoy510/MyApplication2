package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPublish extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spTopic;
    ArrayList<String> Topics;
    Spinner spLevel;
    ArrayList<String> Levels;
    private int level=0;
    private String subject="";
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
        if(position == 0)
            return;

        if(parent.getId() == R.id.spinnerTopic)
        {
            subject = Topics.get(position);//findViewById(R.id.spinnerTopic).toString();;
        }
        else if (parent.getId()==R.id.spLevel) {
            level=position;
        }


    }


    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    public void onCilck2(View view)
    {
        if(level==0 || subject.equals(""))
            return;

        EditText textQuestion = findViewById(R.id.editTextText);
        EditText textRightAnswer = findViewById(R.id.editTextText2);
        EditText textWrongAnswer1 = findViewById(R.id.editTextText4);
        EditText textWrongAnswer2 = findViewById(R.id.editTextText3);
        EditText textWrongAnswer3 = findViewById(R.id.editTextText5);

        if(!TextUtils.isEmpty(textQuestion.getText()) && !TextUtils.isEmpty(textRightAnswer.getText()) && !TextUtils.isEmpty(textWrongAnswer1.getText()) && !TextUtils.isEmpty(textWrongAnswer2.getText()) && !TextUtils.isEmpty(textWrongAnswer3.getText()))
        {
            String question = textQuestion.getText().toString();
            String A1 = textRightAnswer.getText().toString();
            String A2 = textWrongAnswer1.getText().toString();
            String A3 = textWrongAnswer2.getText().toString();
            String A4 = textWrongAnswer3.getText().toString();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            QuestionData user = new QuestionData(subject, level, question, A1, A2, A3, A4);
            addUserToFireStore(user);
        }
    }

    private void addUserToFireStore(QuestionData user)
    {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("questions").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("PUBLISH ", "onSuccess: published");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("PUBLISH ", "onfail : published " + e.getMessage());

            }
        });

    }
}