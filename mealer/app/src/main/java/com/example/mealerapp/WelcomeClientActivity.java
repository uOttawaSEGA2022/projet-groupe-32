package com.example.mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomeClientActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage_client);
        logOut = (TextView) findViewById(R.id.logout_btn);
        logOut.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.logout_btn) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}