package com.example.myapplication;

import static com.example.myapplication.GameActivity1.countQ;
import static com.example.myapplication.GameActivity1.countQ2;
import static com.example.myapplication.GameActivity1.currentQuestion;
import static com.example.myapplication.GameActivity1.questionCat;


import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;

public class OnlineGameManager implements IGetQuestion {

    IGameView gameView;
    String status = "created";

    private String gameId;

    private  int level;

    private String player;

    private ArrayList<QuestionData> arr = new ArrayList<>();

    firebaseClass f;

    // get questions
    // set the question in game room
    //listen for changes on the gamerooom
    // update Activity / View with the question (displayquestion...)
    // check current player
    // get user click from activity
    public OnlineGameManager(IGameView view,String gameId,String player,int level)
    {

        this.gameView = view;
        this.gameId =gameId;
        this.player = player;
        this.level = level;
    //    this.arr = arr;
    }




    public void getAnswerFromUser(String answer)
    {


    }

    private boolean gameStarted = false;


    public void startGame()
    {
        f = new firebaseClass();
        f.setActivity(this);
        if(player.equals(AppConstants.Host)) {
            f.getQuestion(level, questionCat);


        }
        // by default cannot click untill other has joined
        else {
            status = "joined";
            QuestionData d = new QuestionData();
            f.listenForChanges(gameId, AppConstants.Other);
            //getQuestionFromListenForChanges(d);
        }
    }

    @Override
    public void questionsFromFirebase(boolean result, ArrayList<QuestionData> arrResult) {

        // listen for changes
        if(result) // this means success
        {
            Log.d("READ FROM FB", "onComplete: " + arr.size());
            Collections.shuffle(arrResult);

            arr.addAll(arrResult.subList(0, arrResult.size() - 1));

            setNextQuestionInGameRoom();

            if(!gameStarted)
            {
                gameStarted=true;
                f.listenForChanges(gameId, AppConstants.Host);

            }

        }


    }

    @Override
    public void getQuestionFromListenForChanges(QuestionData d)
    {
        gameView.displayQuestion(d, countQ2);
        QuestionData user = new QuestionData();
       // firebaseClass f = new firebaseClass();
        //f.addQuestionToFireStore(user, gameId);
    }

    public void setNextQuestionInGameRoom()
    {
        String question = arr.get(countQ).getQuestion();
        String A1 = arr.get(countQ).getA1();
        String A2 = arr.get(countQ).getA2();
        String A3 = arr.get(countQ).getA3();
        String A4 = arr.get(countQ).getA4();
        String subject = arr.get(countQ).getSubject();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        QuestionData user = new QuestionData(subject, level, question, A1, A2, A3, A4);
        firebaseClass f = new firebaseClass();
        f.addQuestionToFireStore(user,gameId);

        String[] srr = {arr.get(countQ).getA1(),
                arr.get(countQ).getA2(),
                arr.get(countQ).getA3(),
                arr.get(countQ).getA4()};
        gameView.displayQuestion(arr.get(countQ), countQ2);




    }


}
