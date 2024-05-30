package com.example.studentshub;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Initialize views
        TextView titleAbout = findViewById(R.id.titleAbout);
        TextView textAboutDescription = findViewById(R.id.textAboutDescription);
        TextView titleMission = findViewById(R.id.titleMission);
        TextView textMissionDescription = findViewById(R.id.textMissionDescription);
        TextView titleVision = findViewById(R.id.titleVision);
        TextView textVisionDescription = findViewById(R.id.textVisionDescription);

        // Set text
        titleAbout.setText("About Us");
        textAboutDescription.setText("We are an association that aims at uniting the students.");

        titleMission.setText("Mission");
        textMissionDescription.setText("To sensitize university students Kitui County on the available resources on how to access and utilize them.");

        titleVision.setText("Vision");
        textVisionDescription.setText("To unite and mobilize university students at county level towards attaining the visionary county’s economic development.");
    }
}
