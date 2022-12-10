package com.example.mealerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cooker_page_activity extends AppCompatActivity implements  View.OnClickListener {
    private TextView logOut;
    TextView viewRepasVendus, viewNote;
    FirebaseAuth mAuth;
    Cooker loggedCooker ;
    String uid ;
    DatabaseReference reference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cooker_page);
        viewRepasVendus = (TextView) findViewById(R.id.viewNbRepasVendus);
        viewNote = (TextView) findViewById(R.id.viewNote);
        mAuth=FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        uid=mAuth.getCurrentUser().getUid();
        if (!uid.isEmpty()) {
            getUserData() ;
        }
    }

    private void getUserData() {
        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Update note et nombre de repas vendus
                loggedCooker=snapshot.getValue(Cooker.class);
                assert loggedCooker != null;
                viewRepasVendus.setText(loggedCooker.getnombreRepasVendus()+"");
                viewNote.setText(loggedCooker.getNoteMoyenne()+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        if(view.getId()==R.id.changer_description){
            //Page avec la liste des repas associes au cuisiner
            startActivity(new Intent(cooker_page_activity.this, changer_description_cooker_activity.class));
        }

        if(view.getId()==R.id.changer_cheque){
            //Page avec la liste des repas associes au cuisiner
            startActivity(new Intent(cooker_page_activity.this, changer_cheque_cooker_activity.class));
        }

        if(view.getId()==R.id.traiterMenu){
            startActivity(new Intent(cooker_page_activity.this, traiter_menu_activity.class));
        }

        if(view.getId()==R.id.traiterDemande){
            startActivity(new Intent(cooker_page_activity.this, traiter_demande_achat_activity.class));
        }

        if(view.getId()==R.id.retirerUnRepas){
            startActivity(new Intent(cooker_page_activity.this, menu_general_du_cooker.class));
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}