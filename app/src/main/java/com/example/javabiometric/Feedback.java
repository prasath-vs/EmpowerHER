package com.example.javabiometric;

public class Feedback {
    private String name;
    private String email;
    private String feedback;
    private float rating;

    // Required default constructor for Firebase object mapping
    public Feedback() {}

    public Feedback(String name, String email, String feedback, float rating) {
        this.name = name;
        this.email = email;
        this.feedback = feedback;
        this.rating = rating;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getFeedback() { return feedback; }
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
