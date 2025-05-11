package com.example.javabiometric;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
import java.util.HashMap;

public class PeriodCalendarActivity extends AppCompatActivity {

    private String userId = "your_user_id";  // Replace with actual user ID logic
    private String startDate = null;           // To store the selected start date
    private String endDate = null;             // To store the selected end date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_calendar);

        Button btnStartDate = findViewById(R.id.btnStartDate);
        Button btnEndDate = findViewById(R.id.btnEndDate);
        Button btnSaveDates = findViewById(R.id.btnSaveDates); // Button to save both dates

        btnStartDate.setOnClickListener(view -> showDatePicker("startDate"));
        btnEndDate.setOnClickListener(view -> showDatePicker("endDate"));

        btnSaveDates.setOnClickListener(view -> saveDatesToFirebase()); // Save both dates when button clicked
    }

    private void showDatePicker(String dateType) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    if (dateType.equals("startDate")) {
                        startDate = date; // Save the selected start date
                    } else {
                        endDate = date; // Save the selected end date
                    }
                    Toast.makeText(this, dateType + " set to " + date, Toast.LENGTH_SHORT).show();
                }, year, month, day);

        datePicker.show();
    }

    private void saveDatesToFirebase() {
        if (startDate != null && endDate != null) {
            // Create a HashMap to save both dates together
            HashMap<String, String> dates = new HashMap<>();
            dates.put("startDate", startDate);
            dates.put("endDate", endDate);

            FirebaseDatabase.getInstance().getReference("cycles").child(userId).setValue(dates)
                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Dates saved successfully!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to save dates", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Please select both start and end dates", Toast.LENGTH_SHORT).show();
        }
    }
}


