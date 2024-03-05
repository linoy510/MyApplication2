package com.example.myapplication;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class firebaseClass
{

    private IGetQuestion activity;

    public firebaseClass()
    {
    }

    public void setActivity(IGetQuestion activity)
    {
        this.activity = activity;
    }
    public void addUserToFireStore(QuestionData user)
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
    public void getQuestion(int level, String[] questionCat)
    {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Random r = new Random();
        int randIndex = r.nextInt(questionCat.length - 1);
        String orderBy = questionCat[randIndex];
        String s = "חיות";
        firebaseFirestore.collection("questions").whereEqualTo("level", level).orderBy("a1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<QuestionData> tArr = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        tArr.add(doc.toObject(QuestionData.class));
                    }

                    if(activity!=null)
                        activity.questionsFromFirebase(true,tArr);
                }
                else {
                    Log.d("cat arr", "onComplete: " + task.getException().getMessage());
                    if(activity!=null)
                        activity.questionsFromFirebase(false,null);


                }

            }

        });


        }
    public void addQuestionToFireStore(QuestionData user,String gameId)
    {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("GameRoom").document(gameId).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("AddQuestion ", "onSuccess: added");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("AddQuestion ", "onfail : added " + e.getMessage());

            }
        });
    }


    public void listenForChanges(String gameId, QuestionData questionData)
    {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("GameRoom").document(gameId).addSnapshotListener( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if(value != null && value.exists())
                {
                    QuestionData g = value.toObject(QuestionData.class);
                    //questionData = new QuestionData(g.getSubject(),g.getLevel(),g.getQuestion(),g.getA1(),g.getA2(),g.getA3(),g.getA4(),"joined",1);
                    String A1 = g.getA1();
                    String A2 = g.getA2();
                    String A3 = g.getA3();
                    String A4 = g.getA4();
                    String q = g.getQuestion();
                    String sub = g.getSubject();
                    int l = g.getLevel();
                    g.setStatus("joined");
                    g.setCurrentPlayer(1);

                    activity.getQuestionFromListenForChanges(g);

                }
            }
        });
    }

    }

