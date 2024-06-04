package com.example.myapplication;

import java.util.ArrayList;

public interface IGetQuestion {

    void questionsFromFirebase(boolean result, ArrayList<QuestionData> arrResult);
    void getQuestionFromListenForChanges(QuestionData d, String questionStatus);

}
