package com.example.mealerapp;

public class Administrator extends User{
    public Administrator(String prenom, String nom, String courriel, String motDePasse, String adresse) {
        super(prenom, nom, courriel, motDePasse,"Administrator",adresse);
        //On appelle le constructeur de la classe parent User
    }
    public Administrator(){}
}
