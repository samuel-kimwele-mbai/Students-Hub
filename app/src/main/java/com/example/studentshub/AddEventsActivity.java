package com.example.studentshub;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.events.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEventsActivity extends AppCompatActivity {

    private EditText editTextEventTitle;
    private EditText editTextDate;
    private EditText editTextDescription;
    private Button btnSubmit;
    private Calendar calendar;
    private DatabaseReference eventsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);

        // Initialize views
        editTextEventTitle = findViewById(R.id.editTextEventTitle);
        editTextDate = findViewById(R.id.editTextDate);
        editTextDescription = findViewById(R.id.editTextDescription);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Initialize Firebase database reference
        eventsRef = FirebaseDatabase.getInstance().getReference().child("Events");

        calendar = Calendar.getInstance();

        // Set click listener for Date field
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                editTextDate.setText(sdf.format(calendar.getTime()));
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                // Set minimum date to today
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        // Set click listener for Submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered data
                String eventTitle = editTextEventTitle.getText().toString().trim();
                String date = editTextDate.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();

                // Validate data (you can add your validation logic here)

                // Example: Check if any field is empty
                if (eventTitle.isEmpty() || date.isEmpty() || description.isEmpty()) {
                    Toast.makeText(AddEventsActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If data is valid, proceed with adding the event to Firebase
                // Create a unique key for the event
                String eventId = eventsRef.push().getKey();
                if (eventId != null) {
                    // Create a new Event object
                    com.example.studentshub.Event event = new com.example.studentshub.Event(eventTitle, date, description);


                    // Push the event to the "Events" node in Firebase
                    eventsRef.child(eventId).setValue(event)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AddEventsActivity.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                                    // Clear the fields after successful addition
                                    editTextEventTitle.setText("");
                                    editTextDate.setText("");
                                    editTextDescription.setText("");
                                } else {
                                    Toast.makeText(AddEventsActivity.this, "Failed to add event", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
