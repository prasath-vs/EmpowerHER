package com.example.javabiometric;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchNotifications, switchPregnancyMode;
    private EditText editCycleLength;
    private DatabaseReference settingsReference;
    private final String USER_ID = "your_user_id"; // Replace with actual user ID logic

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchNotifications = findViewById(R.id.switchNotifications);
        switchPregnancyMode = findViewById(R.id.switchPregnancyMode);
        editCycleLength = findViewById(R.id.editCycleLength);

        settingsReference = FirebaseDatabase.getInstance().getReference("users").child(USER_ID).child("settings");

        loadSettings();

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> saveSettings("notifications", isChecked));
        switchPregnancyMode.setOnCheckedChangeListener((buttonView, isChecked) -> saveSettings("pregnancyMode", isChecked));

        editCycleLength.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                String cycleLength = editCycleLength.getText().toString();
                if (!cycleLength.isEmpty()) {
                    saveSettings("cycleLength", Integer.parseInt(cycleLength));
                }
            }
        });
    }

    private void loadSettings() {
        settingsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean notifications = dataSnapshot.child("notifications").getValue(Boolean.class);
                Boolean pregnancyMode = dataSnapshot.child("pregnancyMode").getValue(Boolean.class);
                String cycleLength = dataSnapshot.child("cycleLength").getValue(String.class);

                if (notifications != null) {
                    switchNotifications.setChecked(notifications);
                }
                if (pregnancyMode != null) {
                    switchPregnancyMode.setChecked(pregnancyMode);
                }
                if (cycleLength != null) {
                    editCycleLength.setText(cycleLength);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SettingsActivity.this, "Failed to load settings.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveSettings(String key, Object value) {
        settingsReference.child(key).setValue(value).addOnSuccessListener(aVoid ->
                Toast.makeText(this, "Setting saved", Toast.LENGTH_SHORT).show()
        ).addOnFailureListener(e ->
                Toast.makeText(this, "Failed to save setting: " + e.getMessage(), Toast.LENGTH_SHORT).show()
        );
    }
}
