package com.example.projectgdsync;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    Button loginButton, registerButton;
    EditText username, password, confirmPassword;
    TextView forgotPassword;
    TextInputLayout passwordTextInput,confirmPasswordTextInput, usernameTextInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        password = findViewById(R.id.password_edit_text);
        confirmPassword = findViewById(R.id.confirm_password_edit_text);
        passwordTextInput = findViewById(R.id.password_text_input);
        confirmPasswordTextInput = findViewById(R.id.confirm_password_text_input);
        username = findViewById(R.id.username_edit_text);
        forgotPassword = findViewById(R.id.forgot_password);
        usernameTextInput = findViewById(R.id.username_text_input);


        forgotPassword.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
            startActivity(intent);
            finish();
        });
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,LoginRegistrationActivity.class);
            startActivity(intent);
            finish();
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(username.getText().toString(),
                        password.getText().toString())){
                    firebaseAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Not sucessfull", Toast.LENGTH_SHORT).show();firebaseAuth = FirebaseAuth.getInstance();
                            } else {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            }
                        }
                    });
                }
            }

            private boolean validate(String u, String p) {
                if(u.length()==0){
                    usernameTextInput.setError("Enter Username!");
                    return false;
                }

                else if(p.length()==0||p.length()<8) {
                    passwordTextInput.setError("Password should be at least 8 characters!");
                    return false;
                }
                return true;
            }
        });
    }
}
