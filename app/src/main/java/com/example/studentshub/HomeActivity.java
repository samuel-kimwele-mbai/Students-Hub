package com.example.studentshub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    private CardView eventCard, resourcesCard, communityCard, helpCard, feedbackCard, aboutCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize card views
        eventCard = findViewById(R.id.eventCard);
        resourcesCard = findViewById(R.id.resourcesCard);
        communityCard = findViewById(R.id.communityCard);
        helpCard = findViewById(R.id.helpCard);
        feedbackCard = findViewById(R.id.feedbackCard);
        aboutCard = findViewById(R.id.aboutCard);

        // Set click listeners for card views
        eventCard.setOnClickListener(v -> {
            // Redirect to EventActivity
            startActivity(new Intent(HomeActivity.this, EventsActivity.class));
        });

        resourcesCard.setOnClickListener(v -> {
            // Redirect to ResourcesActivity (replace ResourcesActivity with your desired activity)
            startActivity(new Intent(HomeActivity.this, ResourcesActivity.class));
        });

        communityCard.setOnClickListener(v -> {
            // Redirect to CommunityActivity (replace CommunityActivity with your desired activity)
            startActivity(new Intent(HomeActivity.this, CommunityActivity.class));
        });

        helpCard.setOnClickListener(v -> {
            // Redirect to HelpActivity (replace HelpActivity with your desired activity)
            startActivity(new Intent(HomeActivity.this, HelpActivity.class));
        });

        feedbackCard.setOnClickListener(v -> {
            // Redirect to FeedbackActivity (replace FeedbackActivity with your desired activity)
            startActivity(new Intent(HomeActivity.this, FeedbackActivity.class));
        });

        aboutCard.setOnClickListener(v -> {
            // Redirect to AboutActivity (replace AboutActivity with your desired activity)
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
        });
    }
}
