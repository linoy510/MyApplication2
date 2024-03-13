package com.example.myapplication;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionData
{
    private String subject;
    private int level;
    private String question;
    private String A1;
    private String A2;
    private String A3;
    private String A4;




    public QuestionData()
    {

    }
    public QuestionData(String subject, int level, String question, String A1, String A2, String A3, String A4)
    {
        this.subject = subject;
        this.level = level;
        this.question = question;
        this.A1 = A1;
        this.A2 = A2;
        this.A3 = A3;
        this.A4 = A4;

    }



    public String getSubject()
    {
        return subject;
    }
    public int getLevel()
    {
        return level;
    }
    public String getQuestion()
    {
        return question;
    }
    public String getA1()
    {
        return A1;
    }
    public String getA2()
    {
        return A2;
    }
    public String getA3()
    {
        return A3;
    }
    public String getA4()
    {
        return A4;
    }
    public void setSubject(String subject)
    {
         this.subject = subject;
    }
    public void setLevel(int level)
    {
        this.level = level;
    }
    public void setQuestion(String question)
    {
        this.question = question;
    }
    public void setA1(String A1)
    {
        this.A1 = A1;
    }
    public void setA2(String A2)
    {
        this.A2 = A2;
    }
    public void setA3(String A3)
    {
        this.A3 = A3;
    }
    public void setA4(String A4)
    {
        this.A4 = A4;
    }


    public boolean checkAnswer(String answer)
    {
        return this.A1.equals(answer);
    }

    public ArrayList<String> shuffleQuestions()
    {
        ArrayList<String> arr = new ArrayList<>();
        arr.add(A1);arr.add(A2);arr.add(A3);arr.add(A4);

        Collections.shuffle(arr);return arr;

    }

}
