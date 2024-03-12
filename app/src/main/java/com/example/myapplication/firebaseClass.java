package com.example.myapplication;

import static com.example.myapplication.GameActivity1.countQ;
import static com.example.myapplication.GameActivity1.countQ2;

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


    public void listenForChanges(String gameId, String player)
    {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("GameRoom").document(gameId).addSnapshotListener( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if(value != null && value.exists())
                {
                    //We need to decide what happened...???
                    // I am host:
                    //      if  status is created - do nothing
                    //      if status is joined ->
                    //      If countQ = 0 -> game has just started
                    //              host plays first
                    //      else
                    //            -> this means game is ongoing
                    //          display
                    //            if countQ is Odd this means it's other's turn -   disable
                    //
                    //  Else -> this means I am other
                    //      If countQ = 0 -> game has just started
                    //              display and disable
                  //         else
                    //          display
                    //            -> this means game is ongoing
                    //            if countQ is EVEN disable


                    QuestionData g = value.toObject(QuestionData.class);
                    String A1 = g.getA1();
                    String A2 = g.getA2();
                    String A3 = g.getA3();
                    String A4 = g.getA4();
                    String q = g.getQuestion();
                    String sub = g.getSubject();
                    int l = g.getLevel();
                    // if created and I am host - do nothing
                    // status should be set only if game created and I am other
                    if(player.equals("other") || countQ2 != -1)
                        g.setStatus("joined");
                    else
                        g.setStatus("created");
                    //
                    if(!g.getStatus().equals("created"))
                    {
                        countQ2 = g.getCurrentPlayer() + 1;
                        g.setCurrentPlayer(countQ2);
                    }
                    else
                        g.setCurrentPlayer(countQ2);


                    fb.collection("GameRoom").document(gameId).set(g);

                    activity.getQuestionFromListenForChanges(g);

                }
            }
        });
    }

    }

