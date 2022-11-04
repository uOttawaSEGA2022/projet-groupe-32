package com.example.mealerapp;


import com.google.firebase.auth.FirebaseAuth;

public class Client extends User {
    String typeUser = "Client";

    String InformationsCarteCredit;
    //Constructeur
    //On a en paramettre les paramètres nécessaires à la création de l'objet
    public Client(String prenom, String nom, String courriel, String motDePasse, String adressecourriel, String typeUser, String InformationsCarteCredit) {
        super(prenom, nom, courriel, motDePasse, typeUser, adressecourriel);
        this.InformationsCarteCredit = InformationsCarteCredit;
        //On appelle le constructeur de la classe parent User
    }

    public String getInformationsCarteCredit() {
        return InformationsCarteCredit;
    }
    public String getTypeUser() {
        return typeUser;
    }


    public void setInformationsCarteCredit(String informationsCarteCredit) {
        InformationsCarteCredit = informationsCarteCredit;
    }
}