package com.example.mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class cooker_page_activity extends AppCompatActivity implements  View.OnClickListener {
    private TextView logOut;
    TextView viewRepasVendus, viewNote;


    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cooker_page);
        viewRepasVendus = (EditText) findViewById(R.id.viewNbRepasVendus);
        viewNote = (EditText) findViewById(R.id.viewNote);
        mAuth=FirebaseAuth.getInstance();

    }

    public void onClick(View view){
        if(view.getId()==R.id.logout_Button_Dashboard){
            mAuth.signOut();
            startActivity(new Intent(cooker_page_activity.this, MainActivity.class));
        }

        if(view.getId()==R.id.ajouterUnRepas){
            //Page avec le formulaire pour ajouter un repas
            startActivity(new Intent(cooker_page_activity.this, ajouter_Repas_Activity.class));
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
        if(view.getId()==R.id.logout_Button_traiter_demande){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(cooker_page_activity.this, MainActivity.class));
        }
    }
    @Override
    protected void onStart() {

        super.onStart();
        /*
        String idConnectedCooker = FirebaseAuth.getInstance().getUid();
        Log.i("idConnectedCooker", idConnectedCooker);
        DataSnapshot refCuisinier = FirebaseDatabase.getInstance().getReference("Users").child(idConnectedCooker).get().getResult();
        Cooker cuisinier = refCuisinier.getValue(Cooker.class);
        viewRepasVendus.setText(cuisinier.getnombreRepasVendus());
        viewNote.setText(cuisinier.getMoyenne());

         */

    }
}