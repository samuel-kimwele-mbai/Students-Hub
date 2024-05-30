package com.example.studentshub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Initialize views
        TextView phoneNumberTextView = findViewById(R.id.phoneNumber);
        TextView emailAddressTextView = findViewById(R.id.emailAddress);

        // Set admin contact information
        phoneNumberTextView.setText("Phone: 0790506501");
        emailAddressTextView.setText("Email: kicuesalt@gmail.com");

        // Set click listener for phone number
        phoneNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberTextView.getText().toString().trim();
                // Create intent to dial phone number
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        // Set click listener for email address
        emailAddressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = emailAddressTextView.getText().toString().trim();
                // Create intent to compose email
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + emailAddress));
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
    }
}
