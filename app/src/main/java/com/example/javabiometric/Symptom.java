package com.example.javabiometric;


public class Symptom {
    private String name;
    private String date;

    public Symptom() {
        // Default constructor required for calls to DataSnapshot.getValue(Symptom.class)
    }

    public Symptom(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}

