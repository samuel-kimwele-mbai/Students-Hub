package com.example.studentshub;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventsActivity extends AppCompatActivity {

    DatabaseReference eventsRef;
    LinearLayout eventsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // Initialize Firebase Database reference for the "Events" node
        eventsRef = FirebaseDatabase.getInstance().getReference().child("Events");

        // Initialize layout
        eventsContainer = findViewById(R.id.eventsContainer);

        // Button to trigger fetching and displaying events
        Button eventsButton = findViewById(R.id.eventsButton);
        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAndDisplayEvents();
            }
        });
    }

    private void fetchAndDisplayEvents() {
        // Clear existing views before fetching new events
        eventsContainer.removeAllViews();

        // Retrieve event details from Firebase Realtime Database
        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    String title = eventSnapshot.child("title").getValue(String.class);
                    String date = eventSnapshot.child("date").getValue(String.class);
                    String description = eventSnapshot.child("description").getValue(String.class);

                    // Create TextViews for event title, date, and description
                    TextView titleTextView = createTextView("Title: " + title);
                    TextView dateTextView = createTextView("Date: " + date);
                    TextView descriptionTextView = createTextView("Description: " + description);

                    // Add TextViews and divider to the layout
                    eventsContainer.addView(titleTextView);
                    eventsContainer.addView(dateTextView);
                    eventsContainer.addView(descriptionTextView);
                    eventsContainer.addView(createDivider());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        return textView;
    }

    private View createDivider() {
        View divider = new View(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 2); // Change the height as needed
        divider.setLayoutParams(params);
        divider.setBackgroundColor(getResources().getColor(R.color.divider_color)); // Set divider color
        return divider;
    }
}
