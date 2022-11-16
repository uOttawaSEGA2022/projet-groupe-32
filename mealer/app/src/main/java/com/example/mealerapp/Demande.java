package com.example.mealerapp;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Demande {
    private String idDemande;
    private String dateDemande;
    private String idClient;
    private String demandeTraitee;
    private Repas repas;

    public Demande(){}

    public Demande(String idClient, Repas repas){
        this.repas = repas;
        this.idClient = idClient;
        this.idDemande = UUID.randomUUID().toString();
        this.demandeTraitee="false";
    }

    public void addDemandeDatabase(){

        FirebaseDatabase.getInstance().getReference("Demandes").child(idDemande).setValue(this);
    }

    public String getDemandeTraitee(){return this.demandeTraitee;};

    public void traiterDemande(){
        if ( this.demandeTraitee=="false"){
            Log.i("Traiter une demande",  this.demandeTraitee);
            this.demandeTraitee="true";
            Log.i("Traiter une demande",  this.demandeTraitee);
            //FirebaseDatabase.getInstance().getReference("Demandes").child(this.idDemande).child("demandeTraitee").setValue("true");
            return;
        }
        else {
            Log.i("Traiter une demande",  this.demandeTraitee + " id : " + this.idDemande);
            this.demandeTraitee = "false";
            Log.i("Traiter une demande",  this.demandeTraitee);
            //FirebaseDatabase.getInstance().getReference("Demandes").child(this.idDemande).child("demandeTraitee").setValue("false");
        }
    };


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

}
