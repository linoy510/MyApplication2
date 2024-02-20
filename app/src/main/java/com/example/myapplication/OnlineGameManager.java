package com.example.myapplication;

import static com.example.myapplication.GameActivity1.questionCat;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;

public class OnlineGameManager implements IGetQuestion {

    IGameView gameView;

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


    public void startGame()
    {
        f = new firebaseClass();
        f.setActivity(this::questionsFromFirebase);
        if(player.equals(AppConstants.Host))
            f.getQuestion(level, questionCat);
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
        }





    }

    private void setNextQuestionInGameRoom()
    {
        String question = arr.get(2).getQuestion();
        String A1 = arr.get(3).getA1();
        String A2 = arr.get(4).getA2();
        String A3 = arr.get(5).getA3();
        String A4 = arr.get(6).getA4();
        String subject = arr.get(0).getSubject();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        QuestionData user = new QuestionData(subject, level, question, A1, A2, A3, A4);
        firebaseClass f = new firebaseClass();
        f.addQuestionToFireStore(user);

    }
}
