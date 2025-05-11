package com.example.javabiometric;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationRequest;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class EmergencyActivity extends AppCompatActivity {

    private Button Start, Stop;
    private Vibrator vibrator;
    Button police;
    private MaterialButton pinLocationBtn;
    private MaterialButton directionOneBtn;

    //Location One: This is just for an example. Get/Use the required location coordinates you want
    private String latitudeOne = "12.969266964630968";
    private String longitudeOne = "79.15615930717895";

    //Location Two: This is just for an example. Get/Use the required location coordinates you want
    private String latitudeTwo = "12.966101135740525";
    private String longitudeTwo = "79.15499467997694";

    private static final int SMS_PERMISSION_CODE = 1;
    private String phoneNumber = "9786881909"; // Replace with the recipient's phone number
    private String message = "I am in DANGER, HELP!!";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        Start = findViewById(R.id.button_start);
        Stop = findViewById(R.id.button_stop);
        police = findViewById(R.id.police);
        pinLocationBtn = findViewById(R.id.pinLocationBtn);
        directionOneBtn = findViewById(R.id.directionOneBtn);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.alert);


        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!vibrator.hasVibrator()) {
                    return; //Check if device has vibrator hardware or not, if not then return from this method
                    //don't execute futher code below
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    //if API = 26(Oreo) or higher
                    vibrator.vibrate(
                            VibrationEffect.createOneShot(1000000,VibrationEffect.DEFAULT_AMPLITUDE)
                    );

                } else {
                    //vibrate for 1 second
                    vibrator.vibrate(1000000);

                    //Vibration Pattern - you can create yours
//                    long[] pattern = {0, 200, 10, 500};
//                    vibrator.vibrate(pattern, 1);
                }

                mediaPlayer.setLooping(true);
                mediaPlayer.start();

                if (ContextCompat.checkSelfPermission(EmergencyActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EmergencyActivity.this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
                } else {
                    sendSms();
                }
            }
        });

        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To stop the vibartion
                vibrator.cancel();
                mediaPlayer.pause();
            }
        });

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:100"));
                startActivity(intent);
            }
        });

        pinLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinLocationMap(latitudeOne, longitudeOne);
            }
        });

        //handle directionOneBtn click: Open map using intent to show direction from current location (latitude, longitude) to specific location (latitude, longitude)
        directionOneBtn.setOnClickListener(v-> {
            directionFromCurrentMap(latitudeOne, longitudeOne);
        });


    }

    private void sendSms() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed to send, please try again later!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSms();
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void pinLocationMap(String latitude, String longitude){
        // Create a Uri from an intent string. Open map using intent to pin a specific location (latitude, longitude)
        Uri mapUri = Uri.parse("https://maps.google.com/maps/search/" + latitude + "," + longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
        startActivity(intent);
    }


    private void directionFromCurrentMap(String destinationLatitude, String destinationLongitude){
        // Create a Uri from an intent string. Open map using intent to show direction from current location (latitude, longitude) to specific location (latitude, longitude)
        Uri mapUri = Uri.parse("https://maps.google.com/maps?daddr="+ destinationLatitude + "," + destinationLongitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
        startActivity(intent);
    }


}