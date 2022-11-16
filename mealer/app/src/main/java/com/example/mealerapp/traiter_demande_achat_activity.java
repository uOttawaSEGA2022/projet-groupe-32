package com.example.mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class traiter_demande_achat_activity extends AppCompatActivity {

    ListView listViewDemandes;

    List<Demande> demandesArrayList;

    DatabaseReference databaseDemandes;

    FirebaseAuth mAuth;

    String idConnectedCooker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.traiter_demande_achat);

        mAuth=FirebaseAuth.getInstance();
        idConnectedCooker = mAuth.getUid();

        listViewDemandes = (ListView) findViewById(R.id.listViewDemandes);
        databaseDemandes = FirebaseDatabase.getInstance().getReference("Demandes");
        demandesArrayList = new ArrayList<>();

        listViewDemandes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Demande demande = demandesArrayList.get(i);
                Log.i("Demande ajoutée",  demande.getDemandeTraitee() + " id : " + demande.getIdDemande());
                showAccepterRejeter(demande);
                return true;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        /*
        Repas repas = new Repas("Toast à l'avocat et au saumon fumé","Tartinade de cajou à l'aneth, œuf poché, oignons rouge et graines de sésame","Repas délicieux et nutritionnel", "Européenne", 25, "QBK2NcQWtQWy2y6WmhkpezrbP452");
        Demande demande = new Demande("amin_nna@gmail.com", repas);
        demande.addDemandeDatabase();
         */




        databaseDemandes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                demandesArrayList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Demande demande = data.getValue(Demande.class);
                    Log.i("Demande parcourue ",  demande.getDemandeTraitee() + " id : " + demande.getIdDemande());
                    if ( demande.getRepas().getIdCuisinier().equals(idConnectedCooker) && demande.getDemandeTraitee().equals("false")) {
                        demandesArrayList.add(demande);
                    }
                }

                DemandeList demandesAdapter = new DemandeList(traiter_demande_achat_activity.this, demandesArrayList) ;
                listViewDemandes.setAdapter(demandesAdapter) ;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void showAccepterRejeter(Demande demande) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.traiter_demande_dialogue, null);
        dialogBuilder.setView(dialogView);


        final Button buttonAccepterDemande = (Button) dialogView.findViewById(R.id.buttonAccepterDemande);
        final Button buttonRefuserDemande = (Button) dialogView.findViewById(R.id.buttonRefuserDemande);

        dialogBuilder.setTitle("");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonAccepterDemande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accepterDemande(demande);
                b.dismiss();
            }
        });

        buttonRefuserDemande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refuserDemande(demande);
                b.dismiss();
            }
        });

    }

    private void accepterDemande(Demande demande) {

        demande.traiterDemande();

        //FirebaseDatabase.getInstance().getReference("Demandes").child(demande.getIdDemande()).child("demandeTraitee").setValue("true");
        Toast.makeText(getApplicationContext(), "Demande acceptée", Toast.LENGTH_LONG).show();

    }

    private void refuserDemande(Demande demande) {

        demande.traiterDemande();
        Toast.makeText(getApplicationContext(), "Demande refusée", Toast.LENGTH_LONG).show();
    }




}