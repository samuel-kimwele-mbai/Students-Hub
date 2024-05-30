package com.example.studentshub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Accessing the ImageView
        ImageView appIconImageView = findViewById(R.id.appIconImageView);

        // Setting a click listener for the ImageView
        appIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on app icon if needed
                // For example, launch another activity or show a tooltip
            }
        });
    }

    // Method to show user type dialog when the "Select User Type" button is clicked
    public void showUserTypeDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select User Type")
                .setMessage("Are you an admin or a student?")
                .setPositiveButton("Admin", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Show admin login dialog
                        showAdminLoginDialog();
                    }
                })
                .setNegativeButton("Student", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Start the login activity for students
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    // Method to show admin login dialog
    private void showAdminLoginDialog() {
        // Here you can create a custom dialog layout or use AlertDialog to prompt for admin login credentials
        // For simplicity, let's use a basic AlertDialog with email and password fields
        // You can customize this dialog as needed
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Admin Login");
        View dialogView = getLayoutInflater().inflate(R.layout.admin_login_dialog, null); // Inflate your custom dialog layout here

        EditText emailEditText = dialogView.findViewById(R.id.admin_email);
        EditText passwordEditText = dialogView.findViewById(R.id.admin_password);

        builder.setView(dialogView);
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle admin login logic here
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Authenticate admin user
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Authentication success
                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                    if (currentUser != null && currentUser.getUid().equals("6Z6ne7vDAbRM3tEXKUKc7eKauZt1")) {
                                        // Navigate to admin dashboard activity
                                        startActivity(new Intent(MainActivity.this, AdminDashboardActivity.class));
                                    } else {
                                        Toast.makeText(MainActivity.this, "Unauthorized admin", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Authentication failed
                                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


}
