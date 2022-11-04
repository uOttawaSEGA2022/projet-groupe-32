package com.example.mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignupasActivity extends AppCompatActivity implements View.OnClickListener {
    private Button signUpAsCook;
    private Button signUpAsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupas);
        signUpAsCook = (Button) findViewById(R.id.SignUpAsCookButton);
        signUpAsCook.setOnClickListener(this);
        signUpAsClient = (Button) findViewById(R.id.SignUpAsClientButton);
        signUpAsClient.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.SignUpAsCookButton) {
            startActivity(new Intent(this, CooksignupPage2Activity.class));
        }
        if (view.getId() == R.id.SignUpAsClientButton) {
            startActivity(new Intent(this, SignUpPageActivity.class));
        }
    }
}