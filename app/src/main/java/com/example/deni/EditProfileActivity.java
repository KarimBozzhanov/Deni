package com.example.deni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    private static final String LOG_TAG = EditProfileActivity.class.getName();

    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;

    EditText editUserName, editUserFullName, editUserCity, editUserEmail;
    Spinner editUserCategory;
    CheckBox maleCheckBox, femaleCheckBox, editUserNotifications;
    Button saveProfile, closeEditProfile;
    ProgressDialog progressDialog;
    String userCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(this);
        progressDialog.getWindow().setGravity(Gravity.CENTER);
        progressDialog.setMessage("Изменение профиля...");
        Bundle bundle = getIntent().getExtras();

        editUserName = findViewById(R.id.editUserName);
        editUserFullName = findViewById(R.id.editUserFullName);
        editUserCity = findViewById(R.id.editUserCity);
        editUserCategory = findViewById(R.id.editUserCategory);
        editUserEmail = findViewById(R.id.editUserEmail);
        maleCheckBox = findViewById(R.id.maleCheckBox);
        femaleCheckBox = findViewById(R.id.femaleCheckBox);
        editUserNotifications = findViewById(R.id.editUserNotifications);
        saveProfile = findViewById(R.id.saveProfile);
        closeEditProfile = findViewById(R.id.closeEditProfile);


        editUserName.append(bundle.getString("detailUserName"));
        editUserFullName.append(bundle.getString("fullName"));
        editUserCity.append(bundle.getString("userCity"));
        editUserEmail.append(bundle.getString("userEmail"));

        if (bundle.getString("userGender").equals("Не указан")) {
            maleCheckBox.setChecked(false);
            femaleCheckBox.setChecked(false);
        } else if (bundle.getString("userGender").equals("Мужской")) {
            maleCheckBox.setChecked(true);
            femaleCheckBox.setChecked(false);
        } else if (bundle.getString("userGender").equals("Женский")) {
            femaleCheckBox.setChecked(true);
            maleCheckBox.setChecked(false);
        }

        if (bundle.getString("userNotifications").equals("Да")) {
            editUserNotifications.setChecked(true);
        } else {
            editUserNotifications.setChecked(false);
        }

        if (bundle.getString("userSpeciality").equals("")) {
            ArrayAdapter<?> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editUserCategory.setAdapter(arrayAdapter);
        } else if (bundle.getString("userSpeciality").equals("3D моделирование")) {
            ArrayAdapter<?> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.categoryModel, android.R.layout.simple_spinner_item);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editUserCategory.setAdapter(arrayAdapter);
        } else if (bundle.getString("userSpeciality").equals("Визуализация")) {
            ArrayAdapter<?> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.categoryVisual, android.R.layout.simple_spinner_item);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editUserCategory.setAdapter(arrayAdapter);
        } else if (bundle.getString("userSpeciality").equals("Дизайн и архитектура")) {
            ArrayAdapter<?> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.categoryDesign, android.R.layout.simple_spinner_item);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editUserCategory.setAdapter(arrayAdapter);
        } else if (bundle.getString("userSpeciality").equals("Рабочая документация")) {
            ArrayAdapter<?> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.categoryDoc, android.R.layout.simple_spinner_item);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editUserCategory.setAdapter(arrayAdapter);
        }

        maleCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    femaleCheckBox.setChecked(false);
                }
            }
        });

        femaleCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    maleCheckBox.setChecked(false);
                }
            }
        });

        editUserCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userCategory = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        closeEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                DocumentReference dc = firebaseFirestore.collection("Users").document(user.getUid());
                HashMap hashMap = new HashMap();
                hashMap.put("userName", editUserName.getText().toString());
                hashMap.put("fullName", editUserFullName.getText().toString());
                hashMap.put("userCity", editUserCity.getText().toString());
                hashMap.put("userSpeciality", userCategory);
                hashMap.put("userEmail", editUserEmail.getText().toString());
                if (maleCheckBox.isChecked()) {
                    hashMap.put("userGender", "Мужской");
                } else if (femaleCheckBox.isChecked()) {
                    hashMap.put("userGender", "Женский");
                }
                if (editUserNotifications.isChecked()) {
                    hashMap.put("userNotifications", true);
                } else {
                    hashMap.put("userNotifications", false);
                }
                dc.update(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        UserProfileChangeRequest changeUser = new UserProfileChangeRequest.Builder()
                                .setDisplayName(editUserName.getText().toString())
                                .build();
                        user.updateProfile(changeUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                user.updateEmail(editUserEmail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.cancel();
                                        Toast.makeText(EditProfileActivity.this, "Профиль изменен", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EditProfileActivity.this, "Не удалось изменить профиль", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfileActivity.this, "Не удалось изменить профиль", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, "Не удалось изменить профиль", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}