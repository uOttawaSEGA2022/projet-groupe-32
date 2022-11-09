package com.example.mealerapp;

public class Demande {
    String dateDemande;
    String idClient;
    Boolean demandeTraitee = false;
    Repas repas;

    public Demande(){}

    public Demande(String idClient, Repas repas){
        this.repas = repas;
        this.idClient = idClient;
    }

    public Repas getRepas(){
        return repas;
    }
    public String getDate(){
        return dateDemande;
    }

    public String getId(){
        return idClient;
    }

}
