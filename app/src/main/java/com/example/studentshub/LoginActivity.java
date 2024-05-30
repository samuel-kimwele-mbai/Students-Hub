package com.example.studentshub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText usernameEditText, passwordEditText;
    private TextView forgotPasswordTextView, signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("students");

        // Initialize views
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordText);
        signUpTextView = findViewById(R.id.signupText);

        // Set click listeners
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login button click
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Authenticate user
                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                                    // Start the home activity
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle forgot password text click
                showForgotPasswordDialog();
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sign-up text click
                showSignUpDialog();
            }
        });
    }

    // Method to show forgot password dialog
    private void showForgotPasswordDialog() {
        // Inflate the layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_forgot_password, null);

        // Find views in the dialog layout
        EditText emailEditText = view.findViewById(R.id.email);

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view)
                .setTitle("Forgot Password")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle submit button click
                        String email = emailEditText.getText().toString().trim();
                        if (!TextUtils.isEmpty(email)) {
                            // Send password reset email
                            mAuth.sendPasswordResetEmail(email)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(LoginActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }

    // Method to show sign-up dialog
    private void showSignUpDialog() {
        // Inflate the layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_sign_up, null);

        // Find views in the dialog layout
        EditText fullNameEditText = view.findViewById(R.id.fullName);
        EditText emailEditText = view.findViewById(R.id.email);
        EditText regNumberEditText = view.findViewById(R.id.regNumber);
        Spinner genderSpinner = view.findViewById(R.id.genderSpinner);
        EditText phoneNumberEditText = view.findViewById(R.id.phoneNumber);
        EditText passwordEditText = view.findViewById(R.id.password);
        EditText confirmPasswordEditText = view.findViewById(R.id.confirmPassword);

        // Spinner Adapter for gender selection
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genders_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view)
                .setTitle("Sign Up")
                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle register button click
                        String fullName = fullNameEditText.getText().toString().trim();
                        String email = emailEditText.getText().toString().trim();
                        String regNumber = regNumberEditText.getText().toString().trim();
                        String gender = genderSpinner.getSelectedItem().toString().trim();
                        String phoneNumber = phoneNumberEditText.getText().toString().trim();
                        String password = passwordEditText.getText().toString().trim();
                        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                        // Check if all fields are filled
                        if (fullName.isEmpty() || email.isEmpty() || regNumber.isEmpty() ||
                                gender.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() ||
                                confirmPassword.isEmpty()) {
                            Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                        } else {
                            // Check if email is valid
                            if (!isValidEmail(email)) {
                                Toast.makeText(LoginActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                            } else if (phoneNumber.length() != 10) {
                                Toast.makeText(LoginActivity.this, "Phone number must be 10 digits long", Toast.LENGTH_SHORT).show();
                            } else if (!regNumber.matches("[A-Za-z]+/\\d+/\\d+")) {
                                Toast.makeText(LoginActivity.this, "Invalid admission number format", Toast.LENGTH_SHORT).show();
                            } else {
                                // Register the user
                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Registration success, update UI with the signed-in user's information
                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    // Save student details to the database
                                                    saveStudentDetails(user.getUid(), fullName, email, regNumber, gender, phoneNumber);
                                                    Toast.makeText(LoginActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                                                    // Start the home activity
                                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                    startActivity(intent);
                                                } else {
                                                    // If registration fails, display a message to the user.
                                                    Toast.makeText(LoginActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        });
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }

    // Method to save student details to the database
    private void saveStudentDetails(String userId, String fullName, String email, String regNumber, String gender, String phoneNumber) {
        Student student = new Student(fullName, email, regNumber, gender, phoneNumber);
        mDatabase.child(userId).setValue(student);
    }

    // Method to check if an email is valid
    private boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
