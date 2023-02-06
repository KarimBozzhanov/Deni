package com.example.deni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getName();
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailEditText = (EditText) findViewById(R.id.emailText);
                String emailString = emailEditText.getText().toString();

                EditText passEditText = (EditText) findViewById(R.id.passwordText);
                String passwordString = passEditText.getText().toString();

                if(emailString.equals("")){
                    Toast.makeText(LoginActivity.this, "Введите вашу почту", Toast.LENGTH_SHORT).show();
                } else {
                    if(passwordString.equals("")){
                        Toast.makeText(LoginActivity.this, "Введите пароль", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.show();
                        userSignIn(emailString, passwordString);
                    }
                }
            }
        });
    }

    public void userSignIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.cancel();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Добро пожаловать" + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(LOG_TAG, e.getMessage());
                        progressDialog.cancel();
                        if(e.getMessage().equals("The password is invalid or the user does not have a password.")){
                            Toast.makeText(LoginActivity.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                        } else if(e.getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")){
                            Toast.makeText(LoginActivity.this, "Неверная почта", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void registerActivity(View v) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}