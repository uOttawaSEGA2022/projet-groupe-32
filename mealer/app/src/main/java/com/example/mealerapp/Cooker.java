package com.example.mealerapp;

public class Cooker extends User{
    String description;
    String typeUser="Cooker";
    //Constructeur

    //On a en paramettre les paramètres nécessaires à la création de l'objet
    public Cooker(String prenom,String nom, String courriel, String motDePasse, String adresse, String typeUser, String description){
        //On appelle le constructeur de la classe parent User
        super(prenom, nom, courriel, motDePasse,typeUser, adresse);
        this.description=description;
    }

    public String getDescription() {
        return description;
    }
    public String getTypeUser() {
        return typeUser;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
