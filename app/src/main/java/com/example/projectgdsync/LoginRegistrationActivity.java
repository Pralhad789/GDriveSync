package com.example.projectgdsync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRegistrationActivity extends AppCompatActivity {
    Button loginButton, registerButton;
    FirebaseAuth auth;
    EditText username, password, confirmPassword;
    TextInputLayout passwordTextInput, confirmPasswordTextInput, usernameTextInput;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);
        auth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        password = findViewById(R.id.password_edit_text);
        confirmPassword = findViewById(R.id.confirm_password_edit_text);
        passwordTextInput = findViewById(R.id.password_text_input);
        confirmPasswordTextInput = findViewById(R.id.confirm_password_text_input);
        username = findViewById(R.id.username_edit_text);
        usernameTextInput = findViewById(R.id.username_text_input);

        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Toast.makeText(LoginRegistrationActivity.this, "User logged in ", Toast.LENGTH_SHORT).show();
                Intent I = new Intent(LoginRegistrationActivity.this, HomeActivity.class);
                startActivity(I);
            } else {
                Toast.makeText(LoginRegistrationActivity.this, "Login to continue", Toast.LENGTH_SHORT).show();
            }
        };
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginRegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(username.getText().toString(), password.getText().toString(), confirmPassword.getText().toString())) {
                    auth.createUserWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(LoginRegistrationActivity.this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginRegistrationActivity.this, "Success", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginRegistrationActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginRegistrationActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            private boolean validate(String u, String p, String cp) {
                if (u.length() == 0) {
                    usernameTextInput.setError("Enter Username!");
                    return false;
                }
                if (p.compareTo(cp) != 0) {
                    confirmPasswordTextInput.setError("Passwords do not match!");
                    return false;
                } else if (p.length() == 0 || p.length() < 8) {
                    passwordTextInput.setError("Password should be at least 8 characters!");
                    return false;
                }
                return true;
            }
        });
    }
}
