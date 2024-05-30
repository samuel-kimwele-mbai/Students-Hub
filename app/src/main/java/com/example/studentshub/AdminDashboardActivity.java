package com.example.studentshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminDashboardActivity extends AppCompatActivity {

    private CardView cardViewAddEvents;
    private CardView cardViewAddResources;
    private Button btnAddEvents;
    private Button btnAddResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Initialize views
        cardViewAddEvents = findViewById(R.id.cardViewAddEvents);
        cardViewAddResources = findViewById(R.id.cardViewAddResources);
        btnAddEvents = findViewById(R.id.btnAddEvents);
        btnAddResources = findViewById(R.id.btnAddResources);

        // Set click listeners for buttons
        btnAddEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Add Events activity
                Intent intent = new Intent(AdminDashboardActivity.this, AddEventsActivity.class);
                startActivity(intent);
            }
        });

        btnAddResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Add Resources activity
                Intent intent = new Intent(AdminDashboardActivity.this, AddResourcesActivity.class);
                startActivity(intent);
            }
        });
    }
}
