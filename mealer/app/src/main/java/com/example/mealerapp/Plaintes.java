package com.example.mealerapp;

public class Plaintes {
    String titrePlainte;
    String idClient;
    String idCuisinier;
    String datePlainte;
    boolean plainteTraitee=false;//Par défaut toutes les plaintes sont non résolues

    public Plaintes(String titre_Plainte, String id_Client, String id_Cuisinier,String date_Plainte){
        //On appelle le constructeur de la classe parent User
        titrePlainte = titre_Plainte;
        idClient = id_Client;
        idCuisinier = id_Cuisinier;
        datePlainte = date_Plainte;//Touver comment stocker une date
    }

    public boolean getPlainteTraitee(){
        return plainteTraitee;
    }

    public void setPlainteTraitee(){
        plainteTraitee = true;
    }



}
