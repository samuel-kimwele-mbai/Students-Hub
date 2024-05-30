package com.example.studentshub;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity {

    private EditText editTextFeedback;
    private Button btnSubmitFeedback;
    private DatabaseReference feedbackRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Initialize Firebase database reference
        feedbackRef = FirebaseDatabase.getInstance().getReference().child("Feedback");

        // Initialize views
        TextView titleFeedback = findViewById(R.id.titleFeedback);
        editTextFeedback = findViewById(R.id.editTextFeedback);
        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback);

        // Set feedback title
        titleFeedback.setText("Provide Feedback");

        // Set onClickListener for submit button
        btnSubmitFeedback.setOnClickListener(v -> submitFeedback());
    }

    private void submitFeedback() {
        // Get the feedback text from EditText
        String feedback = editTextFeedback.getText().toString().trim();

        // Check if feedback is not empty
        if (!feedback.isEmpty()) {
            // Generate a unique key for the feedback
            String feedbackId = feedbackRef.push().getKey();

            // Save the feedback to the Firebase database
            feedbackRef.child(feedbackId).setValue(feedback)
                    .addOnSuccessListener(aVoid -> {
                        // Feedback saved successfully
                        Toast.makeText(FeedbackActivity.this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
                        // Clear the EditText after submission
                        editTextFeedback.setText("");
                    })
                    .addOnFailureListener(e -> {
                        // Failed to save feedback
                        Toast.makeText(FeedbackActivity.this, "Failed to submit feedback", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Show a toast message if feedback is empty
            Toast.makeText(FeedbackActivity.this, "Please provide feedback", Toast.LENGTH_SHORT).show();
        }
    }
}
