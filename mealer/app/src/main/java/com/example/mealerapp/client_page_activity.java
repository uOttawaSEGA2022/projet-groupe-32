package com.example.mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class client_page_activity extends AppCompatActivity{
    private TextView logOut;
    ArrayList <Repas> repasList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_page);
        logOut = (Button) findViewById(R.id.logout_Button);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(client_page_activity.this, MainActivity.class));
            }
        });
    }


//Client client;
//
//
//
//    public  void recherParTypeCuisine(){
//        repasList= client.RechercherTypeCuisine("Ce que l'utilisateur a tape");
//    }
//    public  void recherParTypeCuisine(){
//        repasList= RechercherTypeCuisine("Ce que l'utilisateur a tape");
//    }
}