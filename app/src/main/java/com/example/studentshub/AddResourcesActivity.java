package com.example.studentshub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddResourcesActivity extends AppCompatActivity {

    private EditText editTextResourceTitle;
    private EditText editTextResourceLink;
    private EditText editTextResourceDescription;
    private Button btnSubmitResource;

    // Firebase variables
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mResourcesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resources);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mResourcesRef = mDatabase.getReference("Resources");

        // Initialize views
        editTextResourceTitle = findViewById(R.id.editTextResourceTitle);
        editTextResourceLink = findViewById(R.id.editTextResourceLink);
        editTextResourceDescription = findViewById(R.id.editTextResourceDescription);
        btnSubmitResource = findViewById(R.id.btnSubmitResource);

        // Set click listener for Submit button
        btnSubmitResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered data
                String resourceTitle = editTextResourceTitle.getText().toString().trim();
                String resourceLink = editTextResourceLink.getText().toString().trim();
                String resourceDescription = editTextResourceDescription.getText().toString().trim();

                // Validate data
                if (resourceTitle.isEmpty() || resourceLink.isEmpty() || resourceDescription.isEmpty()) {
                    Toast.makeText(AddResourcesActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If data is valid, proceed with adding the resource to Firebase Database
                addResourceToDatabase(resourceTitle, resourceLink, resourceDescription);
            }
        });
    }

    // Method to add resource to Firebase Database
    private void addResourceToDatabase(String title, String link, String description) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Create a new resource object
            Resource resource = new Resource(title, link, description);

            // Push the resource object to Firebase Realtime Database under "Resources" node
            mResourcesRef.push().setValue(resource)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddResourcesActivity.this, "Resource added successfully", Toast.LENGTH_SHORT).show();

                            // Clear the input fields
                            editTextResourceTitle.setText("");
                            editTextResourceLink.setText("");
                            editTextResourceDescription.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddResourcesActivity.this, "Failed to add resource", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Show error message if user is not authenticated
            Toast.makeText(AddResourcesActivity.this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
}
