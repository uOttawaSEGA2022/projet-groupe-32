package com.example.mealerapp;
import java.util.*;
import java.io.*;

public class Cooker extends User{
    String description;
    List<Plainte> listePlaintes = new ArrayList<Plainte>();

    //Constructeur

    //On a en paramettre les paramètres nécessaires à la création de l'objet
    public Cooker(String prenom,String nom, String courriel, String motDePasse, String adresse){
        //On appelle le constructeur de la classe parent User
        super(prenom, nom, courriel, motDePasse,"Cooker", adresse);
    }

    public String getDescription() {
        return description;
    }

    public String getTypeUser() {
        return "Cooker";
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
