package com.example.javabiometric;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class InsightsActivity extends AppCompatActivity {

    private Button loadInsightsButton;
    private TextView insightsTextView;
    private DatabaseReference cyclesRef;
    private DatabaseReference symptomsRef;

    private final String USER_ID = "your_user_id";
    private final int DEFAULT_CYCLE_LENGTH = 28; // Default to a 28-day cycle if no data is available

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insights);

        loadInsightsButton = findViewById(R.id.loadInsightsButton);
        insightsTextView = findViewById(R.id.insightsTextView);

        cyclesRef = FirebaseDatabase.getInstance().getReference("cycles").child(USER_ID);
        symptomsRef = FirebaseDatabase.getInstance().getReference("symptoms").child(USER_ID);

        loadInsightsButton.setOnClickListener(v -> loadInsights());
    }

    private void loadInsights() {
        insightsTextView.setText("");  // Clear previous insights
        loadCycles();
    }

    private void loadCycles() {
        cyclesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String startDate = dataSnapshot.child("startDate").getValue(String.class);
                String endDate = dataSnapshot.child("endDate").getValue(String.class);

                String cycleInfo;
                if (startDate != null && endDate != null) {
                    cycleInfo = "Last period: " + startDate + " to " + endDate;

                    // Calculate next period based on start date and cycle length
                    String nextPeriodDate = calculateNextPeriod(startDate, DEFAULT_CYCLE_LENGTH);
                    cycleInfo += "\nNext expected period: " + nextPeriodDate;
                } else {
                    cycleInfo = "No period data available";
                }

                List<String> periods = new ArrayList<>();
                periods.add(cycleInfo);

                // Load symptoms after cycles
                loadSymptoms(periods);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(InsightsActivity.this, "Failed to load period data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String calculateNextPeriod(String lastPeriodStartDate, int cycleLength) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(lastPeriodStartDate));
            calendar.add(Calendar.DAY_OF_MONTH, cycleLength);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "Date format error";
        }
    }

    private void loadSymptoms(List<String> periods) {
        symptomsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> symptoms = new ArrayList<>();
                List<String> healthTips = new ArrayList<>();

                for (DataSnapshot symptomSnapshot : dataSnapshot.getChildren()) {
                    String symptomType = symptomSnapshot.child("type").getValue(String.class);
                    String symptomIntensity = symptomSnapshot.child("intensity").getValue(String.class);

                    if (symptomType != null && symptomIntensity != null) {
                        symptoms.add("Symptom: " + symptomType + " (Intensity: " + symptomIntensity + ")");
                        healthTips.add(generateHealthTip(symptomType, symptomIntensity));
                    } else {
                        symptoms.add("Symptom data incomplete");
                    }
                }

                displayInsights(periods, symptoms, healthTips);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(InsightsActivity.this, "Failed to load symptom data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String generateHealthTip(String symptomType, String intensity) {
        switch (symptomType.toLowerCase()) {
            case "cramps":
                return "For " + intensity + " cramps, try a warm compress or mild pain relievers.";
            case "fatigue":
                return "For " + intensity + " fatigue, ensure adequate hydration and rest.";
            case "headache":
                return "For " + intensity + " headache, consider hydration, rest, and avoiding bright screens.";
            default:
                return "Maintain a balanced diet and stay hydrated.";
        }
    }

    private void displayInsights(List<String> periods, List<String> symptoms, List<String> healthTips) {
        StringBuilder insights = new StringBuilder("Periods:\n");
        for (String period : periods) {
            insights.append(period).append("\n");
        }

        insights.append("\nSymptoms:\n");
        for (String symptom : symptoms) {
            insights.append(symptom).append("\n");
        }

        insights.append("\nHealth Tips:\n");
        for (String tip : healthTips) {
            insights.append(tip).append("\n");
        }

        insightsTextView.setText(insights.toString());
    }
}
