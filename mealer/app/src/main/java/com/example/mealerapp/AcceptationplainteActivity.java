package com.example.mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AcceptationplainteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceptationplainte);
    }
    public void onClickAccepter(View view){
        if(view.getId()==R.id.temporairement_Btn){
            //FirebaseDatabase.getInstance().getReference("Plaintes").child(FirebaseAuth.getInstance().getUid()).getchild("plainteTraitee").setValue(true);
            startActivity(new Intent(AcceptationplainteActivity.this, suspendre_cooker_activity.class));
        }
    }
}