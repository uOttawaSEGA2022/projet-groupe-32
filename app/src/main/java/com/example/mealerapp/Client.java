package com.example.mealerapp;


import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Client extends User {
    String UserType;
    String CVC;
    String InformationsCarteCredit;
    //Constructeur
    //On a en paramettre les paramètres nécessaires à la création de l'objet

    public Client(String prenom, String nom, String courriel, String motDePasse, String UserType, String adressecourriel) {
        super(prenom, nom, courriel, motDePasse, UserType, adressecourriel);
        this.CVC = "";
        this.InformationsCarteCredit = "";
        this.UserType = "Client";
        //On appelle le constructeur de la classe parent User
    }

    public Client() {

    }

    public String getInformationsCarteCredit() {
        return InformationsCarteCredit;
    }

    public String getTypeUser() {
        return "Client";
    }


    public void setInformationsCarteCredit(String cvv, String informationsCarteCredit) {
        InformationsCarteCredit = informationsCarteCredit + " " + cvv;
    }

    }