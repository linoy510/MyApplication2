package com.example.myapplication;

public interface IGameView {
    void displayQuestion(QuestionData q, int currentPlayer, String questionStatus);
    void showMessage(String message);

}
