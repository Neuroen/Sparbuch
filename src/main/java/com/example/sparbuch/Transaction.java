package com.example.sparbuch;

public class Transaction
{
    public String name;
    public String date;
    public float value;

    public Transaction()
    {

    }

    public Transaction(String name, String date, float value)
    {
        this.name = name;
        this.date = date;
        this.value = value;
    }

    public String getName()
    {
        return name;
    }

    public String getDate()
    {
        return date;
    }

    public float getValue()
    {
        return value;
    }
}