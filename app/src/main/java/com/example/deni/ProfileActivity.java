package com.example.deni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    TextView detailUserName, fullName, userCity, userSpeciality, userEmail, userGender, userNotifications;
    Button editProfile, closeProfile;
    User getUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();

        detailUserName = (TextView) findViewById(R.id.detailUserName);
        fullName = (TextView) findViewById(R.id.fullName);
        userCity = (TextView) findViewById(R.id.userCity);
        userSpeciality = (TextView) findViewById(R.id.userSpeciality);
        userEmail = (TextView) findViewById(R.id.userEmail);
        userGender = (TextView) findViewById(R.id.userGender);
        userNotifications = (TextView) findViewById(R.id.userNotifications);

        closeProfile = (Button) findViewById(R.id.closeProfile);
        editProfile = (Button) findViewById(R.id.editProfile);

        firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference userTable = firebaseFirestore.collection("Users").document(user.getUid());
        userTable.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                getUser = documentSnapshot.toObject(User.class);
                detailUserName.setText(getUser.getUserName());
                fullName.setText(getUser.getFullName());
                userCity.setText(getUser.getUserCity());
                userSpeciality.setText(getUser.getUserSpeciality());
                userEmail.setText(getUser.getUserEmail());
                userGender.setText(getUser.getUserGender());
                if (getUser.getUserNotifications() == true) {
                    userNotifications.setText("Да");
                } else {
                    userNotifications.setText("Нет");
                }
            }
        });

        closeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("detailUserName", detailUserName.getText().toString());
                intent.putExtra("fullName", fullName.getText().toString());
                intent.putExtra("userCity", userCity.getText().toString());
                intent.putExtra("userSpeciality", userSpeciality.getText().toString());
                intent.putExtra("userEmail", userEmail.getText().toString());
                intent.putExtra("userGender", userGender.getText().toString());
                intent.putExtra("userNotifications", userNotifications.getText().toString());
                startActivity(intent);
            }
        });
    }
}