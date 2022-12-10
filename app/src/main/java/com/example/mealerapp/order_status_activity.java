package com.example.mealerapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class order_status_activity extends AppCompatActivity {

    ListView listViewOrders;
    ArrayList<Float> list1=new ArrayList<>();
    List<Demande> ordersArrayList;
    String courriel;
    DatabaseReference databaseDemandes;

    FirebaseAuth mAuth;


    String idConnectedClient;
    String idConnectedCooker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_status);

        mAuth = FirebaseAuth.getInstance();
        idConnectedClient = mAuth.getCurrentUser().getUid();

        listViewOrders = (ListView) findViewById(R.id.listViewOrders);
        databaseDemandes = FirebaseDatabase.getInstance().getReference("Demandes");
        ordersArrayList = new ArrayList<>();


        listViewOrders.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Demande demande = ordersArrayList.get(i);
                if (demande.getDemandeExists().equals("true") & demande.getDemandeTraitee().equals("true")) {
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
                    Log.i("Demande parcourue ", demande.getDemandeTraitee() + " id : " + demande.getIdDemande());
                    //&& demande.getDemandeExists().equals("true")& demande.getDemandeTraitee().equals("true")
                    if (demande.getIdClient().equals(idConnectedClient)) {
                        ordersArrayList.add(demande);
                    }
                }
                orderListe demandesAdapter = new orderListe(order_status_activity.this, ordersArrayList);
                listViewOrders.setAdapter(demandesAdapter);
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
                if (demande.getDemandeTraitee().equals("true")) {
                    b.dismiss();
                    showPlaiteDescription(demande);
                }
            }
        });
        buttonEvaluerCuisinier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (demande.getDemandeTraitee().equals("true")) {
                    b.dismiss();
                    showEvaluerDemande(demande);
                }
            }
        });
    }

    private void showEvaluerDemande(Demande demande) {
        final ArrayList<Float> list;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rating_cooker, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        final Button buttonSoumettreEvaluation = (Button) dialogView.findViewById(R.id.buttonSubmit);
        final RatingBar ratingBar = (RatingBar) dialogView.findViewById(R.id.ratingBar);
        buttonSoumettreEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rating = ratingBar.getRating();
                Log.i("je sais pas", " la note est " + rating);
                // demande.getRepas().setRepasRating(rating);
               CalculateMoyenne(demande,rating);
                b.dismiss();
            }
        });

    }

    public String getMoyenne(ArrayList<Float> list){
        if (list.isEmpty()){
            return "0";
        }
        else {
            float moy=0;
            if ( list.size() != 0) {
                for (float i : list) {
                    moy += i;
                }
                moy /= list.size();
            }
            return moy +"";
        }
    }

    private void CalculateMoyenne(Demande demande,Float rating) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(demande.getRepas().getIdCuisinier());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean note = false;
                String moyenne;
                Cooker cook = snapshot.getValue(Cooker.class);
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals("noteRecu")){
                        note=true;
                        break;
                    }
                }
                if(note==true){
                    getCookerGrade(demande);
                    ArrayList<Float> list = list1;
                    list.add(rating);
                    moyenne = getMoyenne(list);
                    Log.i("je sais pas", " la size est   " + list.size());
                    Log.i("je sais pas", " la moyenne est " + getMoyenne(list));
                    cook.setMoyenne(moyenne);
                    ref.child("noteRecu").setValue(list);
                    ref.child("noteMoyenne").setValue(moyenne);
                }else{
                    ArrayList<Float> mylist=new ArrayList<>();
                    mylist.add(rating);
                    ref.child(demande.getRepas().getIdCuisinier()).child("noteRecu").setValue(mylist);
                    moyenne = getMoyenne(mylist);
                    Log.i("je sais pas", " la size est else  " + mylist.size());
                    Log.i("je sais pas", " la moyenne est else " + getMoyenne(mylist));
                    cook.setMoyenne(moyenne);
                    ref.child("noteRecu").setValue(mylist);
                    ref.child("noteMoyenne").setValue(moyenne);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void getCookerGrade(Demande demande){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(demande.getRepas().getIdCuisinier());
        ref.child("noteRecu").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Float>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<Float>>() {};
                list1 = snapshot.getValue(genericTypeIndicator);
               // list1 = snapshot.getValue(ArrayList.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void showPlaiteDescription(Demande demande) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.deposer_plainte_dialogue, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        final EditText editTextTitre = dialogView.findViewById(R.id.titre_plainte1);
        final EditText editTextDescription = dialogView.findViewById(R.id.plainte_description1);
        final Button buttonSoumettreplainte = (Button) dialogView.findViewById(R.id.deposer_plainte1);
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
                FirebaseDatabase.getInstance().getReference().child(demande.getRepas().getIdCuisinier()).child("courriel").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        courriel=snapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Plainte plainte = new Plainte(Titre, FirebaseAuth.getInstance().getCurrentUser().getUid(), courriel,date.toString(), Description);
                plainte.addPlainteDatabase();
                editTextDescription.getText().clear();
                editTextTitre.getText().clear();
            }
        });

    }


}
