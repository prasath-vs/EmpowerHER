package com.example.javabiometric;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NavDrawerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer); // Set the layout that includes the navigation drawer

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Load the HomeActivity
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish(); // Optional: If you don't want to go back to NavDrawerActivity
    }
}
