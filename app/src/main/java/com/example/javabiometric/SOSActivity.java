package com.example.javabiometric;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SOSActivity extends AppCompatActivity {

    private TextView tabitem1, tabitem2, tabitem3;
    private int selectedTabNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sosactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tabitem1 = findViewById(R.id.tabitem1);
        tabitem2 = findViewById(R.id.tabitem2);
        tabitem3 = findViewById(R.id.tabitem3);

        getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainer, FragmentOne.class, null)
                                        .commit();

        tabitem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTab(1);
            }
        });

        tabitem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTab(2);
            }
        });

        tabitem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTab(3);
            }
        });

    }

    private void selectTab(int tabNumber){
        TextView selectedTextView;
        TextView nonSelectedTextView1;
        TextView nonSelectedTextView2;

        if(tabNumber == 1){
            selectedTextView = tabitem1;

            nonSelectedTextView1 = tabitem2;
            nonSelectedTextView2 = tabitem3;

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, FragmentOne.class, null)
                    .commit();
        }
        else if(tabNumber == 2){
            selectedTextView = tabitem2;

            nonSelectedTextView1 = tabitem1;
            nonSelectedTextView2 = tabitem3;

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, FragmentTwo.class, null)
                    .commit();
        }
        else{
            selectedTextView = tabitem3;

            nonSelectedTextView1 = tabitem1;
            nonSelectedTextView2 = tabitem2;

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, FragmentThree.class, null)
                    .commit();
        }

        float slideTo = (tabNumber - selectedTabNumber) * selectedTextView.getWidth();

        TranslateAnimation translateAnimation = new TranslateAnimation(0, slideTo, 0, 0);
        translateAnimation.setDuration(100);

        if(selectedTabNumber == 1){
            tabitem1.startAnimation(translateAnimation);
        }
        else if (selectedTabNumber == 2) {
            tabitem2.startAnimation(translateAnimation);
        }
        else{
            tabitem3.startAnimation(translateAnimation);
        }

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                selectedTextView.setBackgroundResource(R.drawable.round_back_white_100);
                selectedTextView.setTypeface(null, Typeface.BOLD);
                selectedTextView.setTextColor(Color.BLACK);

                nonSelectedTextView1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                nonSelectedTextView1.setTextColor(Color.parseColor("#80FFFFFF"));
                nonSelectedTextView1.setTypeface(null, Typeface.NORMAL);

                nonSelectedTextView2.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                nonSelectedTextView2.setTextColor(Color.parseColor("#80FFFFFF"));
                nonSelectedTextView2.setTypeface(null, Typeface.NORMAL);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        selectedTabNumber = tabNumber;
    }

}