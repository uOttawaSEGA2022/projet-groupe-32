package com.example.mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Cook_Temporary_suspension extends AppCompatActivity {
private Button logOut;
private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_temporary_suspension);
        textView=findViewById(R.id.textViewDate);
        logOut = (Button) findViewById(R.id.logoutButton);
        String suspensionEndTime=getIntent().getStringExtra("SuspensionEndTime");
        textView.setText(suspensionEndTime);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Cook_Temporary_suspension.this, MainActivity.class));
            }
        });
    }
}