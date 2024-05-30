package com.example.studentshub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CommunityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        // Initialize views
        TextView whatsappGroupLink = findViewById(R.id.whatsappGroupLink);

        // Set click listener
        whatsappGroupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open WhatsApp group link
                openWhatsAppGroup();
            }
        });
    }

    // Method to open WhatsApp group link
    private void openWhatsAppGroup() {
        // Replace the URL with your WhatsApp group link
        String whatsappGroupLink = "https://chat.whatsapp.com/8n5NwScWMcT5Z62K1qm9z8";

        // Create intent to open the WhatsApp group link
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(whatsappGroupLink));
        startActivity(intent);
    }
}
