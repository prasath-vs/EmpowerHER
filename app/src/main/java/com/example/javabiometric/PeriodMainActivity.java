package com.example.javabiometric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class PeriodMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_main);

        findViewById(R.id.btnPeriodCalendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PeriodMainActivity.this, PeriodCalendarActivity.class));
            }
        });

        findViewById(R.id.btnSymptoms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PeriodMainActivity.this, SymptomsActivity.class));
            }
        });

        findViewById(R.id.btnInsights).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PeriodMainActivity.this, InsightsActivity.class));
            }
        });

//        findViewById(R.id.btnSettings).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(PeriodMainActivity.this, SettingsActivity.class));
//            }
//        });
    }
}
