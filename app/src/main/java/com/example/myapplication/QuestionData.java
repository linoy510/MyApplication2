package com.example.myapplication;

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
    public String getsuA3()
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
}
