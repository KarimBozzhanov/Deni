package com.example.deni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = RegisterActivity.class.getName();
    FirebaseAuth mAuth;

    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.getWindow().setGravity(Gravity.CENTER);
        progressDialog.setMessage("Регистрация...");

        Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userNameEditText = (EditText) findViewById(R.id.nameText);
                String userName = userNameEditText.getText().toString();
                EditText emailEditText = (EditText) findViewById(R.id.registerEmailText);
                String email = emailEditText.getText().toString();
                EditText passEditText = (EditText) findViewById(R.id.registerPassText);
                String password = passEditText.getText().toString();
                EditText confirmPassEditText = (EditText) findViewById(R.id.confirmPassText);
                String confirmPass = confirmPassEditText.getText().toString();
                if (userName.equals("")){
                    Toast.makeText(RegisterActivity.this, "Введите ваше имя", Toast.LENGTH_SHORT).show();
                } else {
                    if(email.equals("")) {
                        Toast.makeText(RegisterActivity.this, "Введите email", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password.equals(confirmPass)) {
                            progressDialog.show();
                            createUser(userName, email, password, confirmPass);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }


    public void createUser(String userName, String email, String password, String confirmPass){
        if (password.equals(confirmPass)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userName)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            firebaseFirestore = FirebaseFirestore.getInstance();
                                            HashMap hashMap = new HashMap();
                                            hashMap.put("userName", user.getDisplayName());
                                            hashMap.put("fullName", null);
                                            hashMap.put("userEmail", user.getEmail());
                                            hashMap.put("userCity", null);
                                            hashMap.put("userSpeciality", null);
                                            hashMap.put("userAvatar", null);
                                            hashMap.put("userGender", null);
                                            hashMap.put("userNotifications", true);
                                            firebaseFirestore.collection("Users").document(user.getUid()).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                    HashMap portfolio = new HashMap();
                                                    portfolio.put("login", "Не указан");
                                                    portfolio.put("city", "Не указан");
                                                    portfolio.put("speciality", "Не указана");
                                                    portfolio.put("salary", "Не указано");
                                                    portfolio.put("currency", "Не указано");
                                                    portfolio.put("experience", "Не указан");
                                                    portfolio.put("employment", "Не указан");
                                                    portfolio.put("education", "Не указано");
                                                    portfolio.put("information", "Не указана");
                                                    portfolio.put("phoneNumber", "Не указан");
                                                    portfolio.put("email", "Не указана");
                                                    portfolio.put("gender", "Не указан");

                                                    firebaseFirestore.collection("Portfolio").document(user.getUid()).set(portfolio).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            progressDialog.cancel();
                                                            Toast.makeText(RegisterActivity.this, "Добро пожаловать " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                                            Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                                                            startActivity(i);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            progressDialog.cancel();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(LOG_TAG, e.getMessage());
                            if (e.getMessage().equals("The given password is invalid. [ Password should be at least 6 characters ]")){
                                Toast.makeText(RegisterActivity.this, "Пароль должен быть больше 6 символов", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.cancel();
                        }
                    });
        } else {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
        }
    }

    public void loginActivity(View v){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}