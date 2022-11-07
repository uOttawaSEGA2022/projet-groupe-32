package com.example.mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class item_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);
    }

    public void onClickRejetter(View view){
        if(view.getId()==R.id.rejeterPlainteButton){
            //FirebaseDatabase.getInstance().getReference("Plaintes").child(FirebaseAuth.getInstance().getUid()).getchild("plainteTraitee").setValue(true);
            }
    }

    public void onClickAccepter(View view){
        if(view.getId()==R.id.accepterPlainteButton){
            //FirebaseDatabase.getInstance().getReference("Plaintes").child(FirebaseAuth.getInstance().getUid()).getchild("plainteTraitee").setValue(true);
            startActivity(new Intent(item_activity.this, AcceptationplainteActivity.class));
        }
    }
}