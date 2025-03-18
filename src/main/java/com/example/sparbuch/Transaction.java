package com.example.sparbuch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Locale;

public class Transaction
{
    @JsonProperty
    public String name;
    @JsonProperty
    public String date;
    @JsonProperty
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

    @JsonIgnore
    public String getName()
    {
        return name;
    }
    @JsonIgnore
    public String getDate()
    {
        return date;
    }
    @JsonIgnore
    public String getValue()
    {
        return String.format(Locale.GERMAN, "%.2f €", value);
    }
}