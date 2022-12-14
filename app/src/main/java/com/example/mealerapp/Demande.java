package com.example.mealerapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class Demande {
    private String idDemande;
    private String notified;
    private String idCooker;
    private String dateDemande;
    private String idClient;
    private String demandeTraitee;
    private String demandeExists="true";
    private Repas repas;


    public Demande(){}

    public Demande(String idClient, Repas repas){
        this.repas = repas;
        this.idClient = idClient;
        this.idDemande = UUID.randomUUID().toString();
        this.demandeTraitee="false";
        this.notified="false";
    }

    public void addDemandeDatabase(){
        FirebaseDatabase.getInstance().getReference("Demandes").child(this.getIdDemande()).setValue(this);
    }

    public String getDemandeTraitee(){return this.demandeTraitee;};
    public String getDemandeExists(){return this.demandeExists;}

    public void traiterDemande(){
        String idConnectedCooker;
        FirebaseAuth mAuth;
        mAuth=FirebaseAuth.getInstance();
        idConnectedCooker = mAuth.getUid();
        DatabaseReference valueRef = FirebaseDatabase.getInstance().getReference("Users").child(idConnectedCooker).child("nombreRepasVendus");
        Log.i("Increase",  this.demandeTraitee + " id : " + this.idDemande + " increase ");
        if ( this.demandeTraitee.equals("false")){
            Log.i("Increase",  this.demandeTraitee + " id : " + this.idDemande + " increase ");
            valueRef.setValue(ServerValue.increment(1));
            this.demandeTraitee="true";
            FirebaseDatabase.getInstance().getReference("Demandes").child(this.getIdDemande()).child("demandeTraitee").setValue("true");
            return;
        }
    };
    public void rejetterDemande(){
        String idConnectedCooker;
        FirebaseAuth mAuth;
        mAuth=FirebaseAuth.getInstance();
        idConnectedCooker = mAuth.getUid();
        DatabaseReference valueRef = FirebaseDatabase.getInstance().getReference("Users").child(idConnectedCooker).child("nombreRepasVendus");
        Log.i("Increase",  this.demandeTraitee + " id : " + this.idDemande + " increase ");
        valueRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long repasVendu=snapshot.getValue(Long.class);
                long  newnombreRepasVendu= repasVendu +1;
                valueRef.setValue(newnombreRepasVendu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    };


    public void rejetterDemande(Demande demande) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Demandes").child(demande.getIdDemande());
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                database.removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    public Repas getRepas(){
        return repas;
    }
    public String getDate(){
        return dateDemande;
    }

    public String getIdDemande(){
        return idDemande;
    }

    public String getIdClient(){
        return idClient;
    }
    public String getNotified(){
        return notified;
    }
    public String getDemandeStatus(){ return this.demandeTraitee;}



}
