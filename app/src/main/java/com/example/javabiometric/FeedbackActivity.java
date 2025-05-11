//package com.example.javabiometric;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class FeedbackActivity extends AppCompatActivity {
//
//    private EditText etName, etEmail, etFeedback;
//    private Button btnSubmit;
//    private DatabaseReference databaseFeedback;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feedback);
//
//        // Initialize Firebase Database
//        databaseFeedback = FirebaseDatabase.getInstance().getReference("feedback");
//
//        // Initialize UI elements
//        etName = findViewById(R.id.et_name);
//        etEmail = findViewById(R.id.et_email);
//        etFeedback = findViewById(R.id.et_feedback);
//        btnSubmit = findViewById(R.id.btn_submit);
//
//        // Handle button click
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submitFeedback();
//            }
//        });
//    }
//
//    private void submitFeedback() {
//        String name = etName.getText().toString().trim();
//        String email = etEmail.getText().toString().trim();
//        String feedbackText = etFeedback.getText().toString().trim();
//
//        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(feedbackText)) {
//            // Create a unique ID for each feedback
//            String id = databaseFeedback.push().getKey();
//
//            // Create a Feedback object
//            Feedback feedback = new Feedback(name, email, feedbackText);
//
//            // Store the feedback in Firebase
//            assert id != null;
//            databaseFeedback.child(id).setValue(feedback);
//
//            // Clear the form and show a success message
//            etName.setText("");
//            etEmail.setText("");
//            etFeedback.setText("");
//            Toast.makeText(FeedbackActivity.this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
//        }
//    }
//}

package com.example.javabiometric;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity {

    private EditText etName, etEmail, etFeedback;
    private RatingBar ratingBar; // New RatingBar for star ratings
    private Button btnSubmit;
    private DatabaseReference databaseFeedback;
    private ImageView helpBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Initialize Firebase Database
        databaseFeedback = FirebaseDatabase.getInstance().getReference("feedback");

        // Initialize UI elements
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etFeedback = findViewById(R.id.et_feedback);
        ratingBar = findViewById(R.id.ratingBar);  // Initialize RatingBar
        btnSubmit = findViewById(R.id.btn_submit);
        helpBtn = findViewById(R.id.helpBtn);

        // Handle button click
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FeedbackActivity.this, HelpActivity.class);
                startActivity(i);
            }
        });
    }

    private void submitFeedback() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String feedbackText = etFeedback.getText().toString().trim();
        float rating = ratingBar.getRating(); // Get the rating from RatingBar

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(feedbackText)) {
            // Create a unique ID for each feedback
            String id = databaseFeedback.push().getKey();

            // Create a Feedback object with the rating
            Feedback feedback = new Feedback(name, email, feedbackText, rating);

            // Store the feedback in Firebase
            assert id != null;
            databaseFeedback.child(id).setValue(feedback);

            // Clear the form and show a success message
            etName.setText("");
            etEmail.setText("");
            etFeedback.setText("");
            ratingBar.setRating(0); // Reset the RatingBar
            Toast.makeText(FeedbackActivity.this, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }
}

