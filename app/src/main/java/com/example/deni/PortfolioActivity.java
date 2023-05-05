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

public class PortfolioActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;

    FirebaseUser user;

    TextView portfolioLogin, portfolioCity, portfolioSpeciality, portfolioSalary, portfolioCurrency, portfolioExperience, portfolioEmployment, portfolioEducation, portfolioInfo, portfolioPhoneNumber, portfolioEmail, portfolioGender;
    Button editPortfolio;
    Portfolio portfolio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        portfolioLogin = findViewById(R.id.portfolioLogin);
        portfolioCity = findViewById(R.id.portfolioCity);
        portfolioSpeciality = findViewById(R.id.portfolioSpeciality);
        portfolioSalary = findViewById(R.id.portfolioSalary);
        portfolioCurrency = findViewById(R.id.portfolioCurrency);
        portfolioExperience = findViewById(R.id.portfolioExperience);
        portfolioEmployment = findViewById(R.id.portfolioEmployment);
        portfolioEducation = findViewById(R.id.portfolioEducation);
        portfolioInfo = findViewById(R.id.portfolioInfo);
        portfolioPhoneNumber = findViewById(R.id.portfolioPhoneNumber);
        portfolioEmail = findViewById(R.id.portfolioEmail);
        portfolioGender = findViewById(R.id.portfolioGender);

        editPortfolio = findViewById(R.id.editPortfolio);

        DocumentReference dc = firebaseFirestore.collection("Portfolio").document(user.getUid());

        dc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                portfolio = documentSnapshot.toObject(Portfolio.class);
                portfolioLogin.setText(portfolio.getPortfolioLogin());
                portfolioCity.setText(portfolio.getPortfolioCity());
                portfolioSpeciality.setText(portfolio.getPortfolioSpeciality());
                portfolioSalary.setText(portfolio.getPortfolioSalary());
                if (portfolio.getPortfolioCurrency().equals("Доллар")) {
                    portfolioCurrency.setText("USD");
                } else if (portfolio.getPortfolioCurrency().equals("Евро")) {
                    portfolioCurrency.setText("EUR");
                } else if (portfolio.getPortfolioCurrency().equals("Рубль")) {
                    portfolioCurrency.setText("RUB");
                } else if (portfolio.getPortfolioCurrency().equals("Тенге")) {
                    portfolioCurrency.setText("KZT");
                }
                portfolioExperience.setText(portfolio.getPortfolioExperience());
                portfolioEmployment.setText(portfolio.getPortfolioEmployment());
                portfolioEducation.setText(portfolio.getPortfolioEducation());
                portfolioInfo.setText(portfolio.getPortfolioInfo());
                portfolioPhoneNumber.setText(portfolio.getPortfolioPhoneNumber());
                portfolioEmail.setText(portfolio.getPortfolioEmail());
                portfolioGender.setText(portfolio.getPortfolioGender());
            }
        });

        editPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PortfolioActivity.this, EditPortfolioActivity.class);
                intent.putExtra("login", portfolioLogin.getText().toString());
                intent.putExtra("city", portfolioCity.getText().toString());
                intent.putExtra("speciality", portfolioSpeciality.getText().toString());
                intent.putExtra("salary", portfolioSalary.getText().toString());
                intent.putExtra("currency", portfolioCurrency.getText().toString());
                intent.putExtra("experience", portfolioExperience.getText().toString());
                intent.putExtra("employment", portfolioEmployment.getText().toString());
                intent.putExtra("education", portfolioEducation.getText().toString());
                intent.putExtra("information", portfolioInfo.getText().toString());
                intent.putExtra("phoneNumber", portfolioPhoneNumber.getText().toString());
                intent.putExtra("email", portfolioEmail.getText().toString());
                intent.putExtra("gender", portfolioGender.getText().toString());
                startActivity(intent);
            }
        });
    }
}