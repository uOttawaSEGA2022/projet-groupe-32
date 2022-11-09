package com.example.mealerapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Demande {
    String idDemande;
    String dateDemande;
    String idClient;
    Boolean demandeTraitee=false;
    Repas repas;

    public Demande(){}

    public Demande(String idClient, Repas repas){
        this.repas = repas;
        this.idClient = idClient;
        this.idDemande = UUID.randomUUID().toString();
    }

    public void addDemandeDatabase(){
        FirebaseDatabase.getInstance().getReference("Demandes").child(this.idDemande).setValue(this);
    }
    public boolean demandeTraitee(){return demandeTraitee;};
    public void traiterDemande(){
        if ( !this.demandeTraitee){
            this.demandeTraitee=true;
            return;
        }
        this.demandeTraitee=false;
    };


    public Repas getRepas(){
        return repas;
    }
    public String getDate(){
        return dateDemande;
    }

    public String getId(){
        return idDemande;
    }

    public String getIdClient(){
        return idClient;
    }

}
