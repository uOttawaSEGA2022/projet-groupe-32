package com.example.mealerapp;


public class Client extends User {
    String typeUser;
    String InformationsCarteCredit;
    //Constructeur
    //On a en paramettre les paramètres nécessaires à la création de l'objet
public Client(){

}
    public Client(String prenom, String nom, String courriel, String motDePasse, String adressecourriel, String typeUser, String InformationsCarteCredit) {
        super(prenom, nom, courriel, motDePasse, adressecourriel);
        typeUser = "Client";
        this.InformationsCarteCredit = InformationsCarteCredit;
        ;
        //On appelle le constructeur de la classe parent User
    }

    public String getInformationsCarteCredit() {
        return InformationsCarteCredit;
    }

    public void setInformationsCarteCredit(String informationsCarteCredit) {
        InformationsCarteCredit = informationsCarteCredit;
    }
}