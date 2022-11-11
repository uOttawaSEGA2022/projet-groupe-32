package com.example.mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class cooker_page_activity extends AppCompatActivity implements OnClickListener{
    private TextView logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cooker_page);
    }
    public void onClick(View view){
        if(view.getId()==R.id.logout_Button){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(cooker_page_activity.this, MainActivity.class));
        }

        if(view.getId()==R.id.ajouterUnRepas){
            //Page avec le formulaire pour ajouter un repas
            startActivity(new Intent(cooker_page_activity.this, MainActivity.class));
        }

        if(view.getId()==R.id.retirerUnRepas){
            //Page avec la liste des repas associes au cuisiner
            startActivity(new Intent(cooker_page_activity.this, MainActivity.class));
        }

        if(view.getId()==R.id.traiterMenu){
            startActivity(new Intent(cooker_page_activity.this, traiter_menu_activity.class));
        }

        if(view.getId()==R.id.traiterDemandes){
            startActivity(new Intent(cooker_page_activity.this, traiter_demande_achat_activity.class));
        }
    }

}