package com.example.myapplication;

public class roomGame
{
    private String status;
    private String namePlayer1;
    private String namePlayer2;
    private int currentPlayer;
    private String question;
    private String A1;
    private String A2;
    private String A3;
    private String A4;
    private String subject;
    private int level;
    public roomGame()
    {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNamePlayer1() {
        return namePlayer1;
    }

    public void setNamePlayer1(String namePlayer1) {
        this.namePlayer1 = namePlayer1;
    }

    public String getNamePlayer2() {
        return namePlayer2;
    }

    public void setNamePlayer2(String namePlayer2) {
        this.namePlayer2 = namePlayer2;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA1() {
        return A1;
    }

    public void setA1(String a1) {
        A1 = a1;
    }

    public String getA2() {
        return A2;
    }

    public void setA2(String a2) {
        A2 = a2;
    }

    public String getA3() {
        return A3;
    }

    public void setA3(String a3) {
        A3 = a3;
    }

    public String getA4() {
        return A4;
    }

    public void setA4(String a4) {
        A4 = a4;
    }

    public roomGame(String status, String namePlayer1, String namePlayer2, int currentPlayer)
    {
        this.currentPlayer = currentPlayer;
        this.status = status;
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2;
    }
}
