package com.example.mealerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class order_status_activity extends AppCompatActivity {

    ListView listViewOrders;

    List<Demande> ordersArrayList;

    DatabaseReference databaseDemandes;

    FirebaseAuth mAuth;


    String idConnectedClient;
    String idConnectedCooker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_status);

        mAuth=FirebaseAuth.getInstance();
        idConnectedClient = mAuth.getCurrentUser().getUid();

        listViewOrders = (ListView) findViewById(R.id.listViewOrders);
        databaseDemandes = FirebaseDatabase.getInstance().getReference("Demandes");
        ordersArrayList = new ArrayList<>();


        listViewOrders.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Demande demande = ordersArrayList.get(i);
                if(demande.getDemandeExists().equals("true")& demande.getDemandeTraitee().equals("true")){
                    //Log.i("Demande ajoutée",  demande.getDemandeTraitee() + " id : " + demande.getIdDemande());
                    showEvaluerDeposerPlainte(demande);
                }
                return true;

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseDemandes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ordersArrayList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Demande demande = data.getValue(Demande.class);
                    Log.i("Demande parcourue ",  demande.getDemandeTraitee() + " id : " + demande.getIdDemande());
                    //&& demande.getDemandeExists().equals("true")& demande.getDemandeTraitee().equals("true")
                    if ( demande.getIdClient().equals(idConnectedClient) ) {
                        ordersArrayList.add(demande);
                    }
                }
                orderListe demandesAdapter = new orderListe(order_status_activity.this, ordersArrayList) ;
                listViewOrders.setAdapter(demandesAdapter) ;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void showEvaluerDeposerPlainte(Demande demande) {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.demande_acceptee_dialog, null);
        dialogBuilder.setView(dialogView);
        final android.app.AlertDialog b = dialogBuilder.create();
        b.show();

        final Button buttonDeposerPlainte = (Button) dialogView.findViewById(R.id.buttonDeposerPlainte);
        final Button buttonEvaluerCuisinier = (Button) dialogView.findViewById(R.id.buttonEvaluerCuisinier);
        buttonDeposerPlainte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(demande.getDemandeTraitee().equals("true")){
                    b.dismiss();
                    showPlaiteDescription(demande);
                }
            }
        });
         buttonEvaluerCuisinier.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(demande.getDemandeTraitee().equals("true")){
                 b.dismiss();
                 showEvaluerDemande(demande);
                 }
             }
         });
    }

    private void showEvaluerDemande(Demande demande) {

        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rating_cooker, null);
        dialogBuilder.setView(dialogView);
        final android.app.AlertDialog b = dialogBuilder.create();
        b.show();
        final Button buttonSoumettreEvaluation= (Button) dialogView.findViewById(R.id.buttonSubmit);
        final RatingBar ratingBar=(RatingBar) dialogView.findViewById(R.id.ratingBar);
        buttonSoumettreEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating=ratingBar.getRating();
                //demande.getRepas().setRepasRating(rating);

            }
        });
    }

    private void showPlaiteDescription(Demande demande) {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.deposer_plainte_dialogue, null);
        dialogBuilder.setView(dialogView);
        final android.app.AlertDialog b = dialogBuilder.create();
        b.show();
        final EditText editTextTitre = findViewById(R.id.titre_plainte);
        final EditText editTextDescription = findViewById(R.id.plainte_description);
        final Button buttonSoumettreplainte = (Button) dialogView.findViewById(R.id.deposer_plainte);
        buttonSoumettreplainte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Titre = editTextTitre.getText().toString().trim();
                String Description = editTextDescription.getText().toString().trim();
                if (Titre.isEmpty()) {
                    editTextTitre.setError("Titre est requis");
                    editTextTitre.requestFocus();
                    return;
                }
                if (Description.isEmpty()) {
                    editTextDescription.setError("Description est requise");
                    editTextDescription.requestFocus();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                Plainte plainte=new Plainte(Titre,FirebaseAuth.getInstance().getCurrentUser().getUid(),demande.getRepas().getIdCuisinier(),date.toString(),Description);
                plainte.addPlainteDatabase();
            }
        });

    }


}
