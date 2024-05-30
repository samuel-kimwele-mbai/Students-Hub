package com.example.studentshub;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResourcesActivity extends AppCompatActivity {

    private ListView resourcesListView;
    private DatabaseReference resourcesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        // Initialize ListView
        resourcesListView = findViewById(R.id.resourcesListView);

        // Initialize Firebase database reference
        resourcesRef = FirebaseDatabase.getInstance().getReference().child("Resources");

        // Retrieve resources from Firebase
        retrieveResources();
    }

    private void retrieveResources() {
        // Listen for changes in the "Resources" node
        resourcesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> resourcesList = new ArrayList<>();

                // Iterate through the dataSnapshot to retrieve resources
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Extract resource details and add to the list
                    Resource resource = snapshot.getValue(Resource.class);
                    if (resource != null) {
                        String title = resource.getTitle();
                        String link = resource.getLink();
                        String description = resource.getDescription();

                        // Format the resource details
                        String formattedResource = "Title: " + title + "\nLink: " + link + "\nDescription: " + description;
                        resourcesList.add(formattedResource);
                    }
                }

                // Create ArrayAdapter to display resources in ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ResourcesActivity.this, android.R.layout.simple_list_item_1, resourcesList);
                resourcesListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(ResourcesActivity.this, "Failed to retrieve resources: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}