package com.example.javabiometric;


public class Period {
    private String startDate;
    private String endDate;

    public Period() {
        // Default constructor required for calls to DataSnapshot.getValue(Period.class)
    }

    public Period(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}

