package com.example.javabiometric;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class SymptomsActivity extends AppCompatActivity {

    private Spinner symptomTypeSpinner;
    private Spinner symptomIntensitySpinner;
    private String userId = "your_user_id"; // Replace with actual user ID logic

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        symptomTypeSpinner = findViewById(R.id.spinnerSymptomType);
        symptomIntensitySpinner = findViewById(R.id.spinnerSymptomIntensity);

        // Set up the spinners with sample data
        setupSpinners();

        findViewById(R.id.btnSaveSymptom).setOnClickListener(this::saveSymptom);
    }

    private void setupSpinners() {
        // Sample data for symptom types and intensities
        String[] symptomTypes = {"Headache", "Nausea", "Fatigue", "Cramps", "Mood Swings"};
        String[] symptomIntensities = {"Mild", "Moderate", "Severe"};

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, symptomTypes);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        symptomTypeSpinner.setAdapter(typeAdapter);

        ArrayAdapter<String> intensityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, symptomIntensities);
        intensityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        symptomIntensitySpinner.setAdapter(intensityAdapter);
    }

    private void saveSymptom(View view) {
        String symptomType = symptomTypeSpinner.getSelectedItem().toString();
        String intensity = symptomIntensitySpinner.getSelectedItem().toString();

        // Input validation
        if (symptomType.isEmpty() || intensity.isEmpty()) {
            Toast.makeText(this, "Please select both symptom type and intensity", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a HashMap to store symptom data
        HashMap<String, String> symptomData = new HashMap<>();
        symptomData.put("type", symptomType);
        symptomData.put("intensity", intensity);

        FirebaseDatabase.getInstance().getReference("symptoms").child(userId).push().setValue(symptomData)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Symptom saved: " + symptomType + " with intensity: " + intensity, Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to save symptom", Toast.LENGTH_SHORT).show());
    }
}



