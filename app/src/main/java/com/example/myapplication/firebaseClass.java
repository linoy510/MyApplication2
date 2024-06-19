package com.example.myapplication;

import static com.example.myapplication.AppConstants.Other;
import static com.example.myapplication.AppConstants.game_config;
import static com.example.myapplication.AppConstants.two_phone;
import static com.example.myapplication.GameActivity1.countQ;
import static com.example.myapplication.GameActivity1.countQ2;
import static com.example.myapplication.OnlineGameManager.arr;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    private roomGame g;

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
        //String s = "חיות";
        firebaseFirestore.collection("questions").orderBy("a1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
    public void addQuestionToFireStore(QuestionData qd,String gameId, int current, String status, String questionStatus)
    {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        roomGame roomGame = new roomGame();

        //if(countQ2 % 2 == 0 || countQ2 == -1)

        roomGame.setA1(qd.getA1());
        roomGame.setA2(qd.getA2());
        roomGame.setA3(qd.getA3());
        roomGame.setA4(qd.getA4());
        roomGame.setQuestion(qd.getQuestion());
        roomGame.setLevel(qd.getLevel());
        roomGame.setSubject(qd.getSubject());
        roomGame.setCurrentPlayer(current);
        roomGame.setStatus(status);
        roomGame.setQuestionStatus(questionStatus);
        if(questionStatus.equals("true"))
            roomGame.setNamePlayer1(1);

        fb.collection("GameRoom").document(gameId).set(roomGame).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("AddQuestion ", "onSuccess: added");

                if(questionStatus.equals("finish"))
                {
                    if(g.getNamePlayer1() >= 5)
                    {
                        Intent i = new Intent((Activity)activity, finishPage.class);
                        i.putExtra("winOrLose", "win");
                        ((Activity)activity).startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent((Activity)activity, finishPage.class);
                        i.putExtra("winOrLose", "lose");
                        ((Activity)activity).startActivity(i);
                    }

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("AddQuestion ", "onfail : added " + e.getMessage());

            }
        });
    }

    public void updateResult(roomGame g,String gameId)
    {
        countQ2 = 0;
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        roomGame roomGame = new roomGame();

        roomGame.setA1(g.getA1());
        roomGame.setA2(g.getA2());
        roomGame.setA3(g.getA3());
        roomGame.setA4(g.getA4());
        roomGame.setQuestion(g.getQuestion());
        roomGame.setLevel(g.getLevel());
        roomGame.setSubject(g.getSubject());
        roomGame.setCurrentPlayer(countQ2);
        roomGame.setQuestionStatus(g.getQuestionStatus());
        Log.d("count2", "nextQuestionInGameRoom: " + countQ2);

        fb.collection("GameRoom").document(gameId).set(roomGame).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("AddQuestion ", "onSuccess: success add");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("AddQuestion ", "onfail : fail add " + e.getMessage());

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


                    g = value.toObject(roomGame.class);
                    String A1 = g.getA1();
                    String A2 = g.getA2();
                    String A3 = g.getA3();
                    String A4 = g.getA4();
                    String q = g.getQuestion();
                    String sub = g.getSubject();
                    int l = g.getLevel();
                    String questionStatus = g.getQuestionStatus();
                    String status = g.getStatus();
                    countQ2 = g.getCurrentPlayer();
                    //g.setCurrentPlayer(countQ2 + 1);
                    //if(question wrong) enable button
                    QuestionData qd = new QuestionData(sub,l,q,A1,A2,A3,A4);


                    Log.d("GAME DEBUG", "qdata: " + questionStatus);

                    // if I am host
                    if( player.equals(AppConstants.Host)) {

                        if (g.getStatus().equals("created"))
                        {
                            addQuestionToFireStore(qd,gameId,countQ2, "created", questionStatus);
                            return;
                        }

                        // if it wad other turn and he answered correct
                        // set next question,, countQ2 ???
                        //
                        // return;



                        // 1. If it is other:
                        // 2. if question is posted -> it is other's question - only display and enable buttons
                        // 3. if status is true -> this means it is the question otherI answered, do nothing
                        // 4. if status is false -> this means host has answered wrong -> enable buttons

                        // !!! what the other set's in firebase!! after answering


                        // If it is host:
                        // if it is true -> other answered correct post new question("posted") , countq2 is host
                        // if it is false -> enable buttons

                        // countQ2 -> whose turn
                        // questionStatus -> posted, true false
                        //if(questionStatus.equals("posted") && countQ2 == 1) return;
                        if(countQ2 == 0)
                        {
                           // if(questionStatus.equals("posted"))
                           // {
                             //   activity.getQuestionFromListenForChanges(qd, questionStatus);
                             //   return;
                           // }
                            Log.d("GAME DEBUG", "countQ2: " + countQ2);

                            // this was other turn
                            if(questionStatus.equals("true"))
                            {
                                // other has answered correct
                                nextQuestionInGameRoom(l,gameId);
                                return;
                            }
                        }
                        if(countQ2 == -1)
                            countQ2 = 0;
                        Log.d("count2", "nextQuestionInGameRoom: " + countQ2);

                        g.setCurrentPlayer(countQ2);



                        // I played and I am right
                        // other played and he is right
                        // this means set next question in firebase!

                        // if I am host and other answered wrong - enable answers
                        // If I am other and host answered wrong - enable answers
                        //



                        // this means status is JOINED


                        activity.getQuestionFromListenForChanges(qd, questionStatus);
                        return;
                        // this means that CountQ2 is not zero
                        // game is ongoingHello! THIS IS THE CODE FOR THE GAME: B31O5nJvY4NavTTf6c8A

                    }
                    else
                    {
                        if(status.equals("created"))
                        {
                            g.setStatus("joined");
                            //    countQ2 = g.getCurrentPlayer() + 1;
                            //    g.setCurrentPlayer(countQ2);
                            //g.setCurrentPlayer(0);
                            fb.collection("GameRoom").document(gameId).set(g).addOnCompleteListener(new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    activity.getQuestionFromListenForChanges(qd, questionStatus);


                                }
                            });

                        }
                        else
                        {
//                            if(countQ2 ==0){
//                                countQ2 = 1;
//                            }
                            if(g.getQuestionStatus().equals("finish"))
                            {
                                if(g.getNamePlayer2() >= 5)
                                {
                                    Intent i = new Intent((Activity)activity, finishPage.class);
                                    i.putExtra("winOrLose", "win");
                                    ((Activity)activity).startActivity(i);
                                }
                                else
                                {
                                    Intent i = new Intent((Activity)activity, finishPage.class);
                                    i.putExtra("winOrLose", "lose");
                                    ((Activity)activity).startActivity(i);
                                }
                            }
                            else
                            {
                                g.setCurrentPlayer(countQ2);
                                Log.d("count2", "nextQuestionInGameRoom: " + countQ2);
                                activity.getQuestionFromListenForChanges(qd, questionStatus);
                            }




                        }





                 /*       fb.collection("GameRoom").document(gameId).set(g).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                activity.getQuestionFromListenForChanges(qd);

                            }
                        });

                  */
                        return;


                    }


                    // if created and I am host - do nothing

                    // status should be set only if game created and I am other

                }
            }
        });
    }

    public void nextQuestionInGameRoom(int level,String gameId)
    {
        countQ++;
        String question = arr.get(countQ).getQuestion();
        String A1 = arr.get(countQ).getA1();
        String A2 = arr.get(countQ).getA2();
        String A3 = arr.get(countQ).getA3();
        String A4 = arr.get(countQ).getA4();
        String subject = arr.get(countQ).getSubject();
        //countQ2 = 0;
        QuestionData user = new QuestionData(subject, level, question, A1, A2, A3,A4);

        addQuestionToFireStore(user,gameId, countQ2, "joined", "posted");
        Log.d("count2", "nextQuestionInGameRoom: " + countQ2);
        //activity.getQuestionFromListenForChanges(user);
    }


}

