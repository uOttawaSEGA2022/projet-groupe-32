package com.example.mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        viewRepasVendus = (EditText) findViewById(R.id.viewNbRepasVendus);
        viewNote = (EditText) findViewById(R.id.viewNote);
        mAuth=FirebaseAuth.getInstance();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users") ;
        uid=mAuth.getCurrentUser().getUid() ;
        if (!uid.isEmpty()) {
            getUserData() ;
        }
    }

    private void getUserData() {
        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loggedCooker=snapshot.getValue(Cooker.class) ;
                viewRepasVendus.setText(loggedCooker.getnombreRepasVendus());
                viewNote.setText(loggedCooker.getMoyenne());
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
        /*String idConnectedCooker = FirebaseAuth.getInstance().getUid();
        Log.i("idConnectedCooker", idConnectedCooker);
        DataSnapshot refCuisinier = FirebaseDatabase.getInstance().getReference("Users").child(idConnectedCooker).get().getResult();
        Cooker cuisinier = refCuisinier.getValue(Cooker.class);*/
    }
}