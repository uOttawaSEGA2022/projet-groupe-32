package com.example.mealerapp;

import java.util.ArrayList;
import java.util.List;

public class Cooker extends User{
    String description="";
    List<Plainte> listePlaintes = new ArrayList<Plainte>();
    String suspension;
    String suspensionEndTime;
    String nombreRepasVendus;
    String moyenne;
    List<Float> notesRecues;
    String chequeImageUrl = "";
    private boolean suspended ;

    //Constructeur
    //On a en paramettre les paramètres nécessaires à la création de l'objet
    public Cooker(String prenom,String nom, String courriel, String motDePasse,String UserType,String adresse){
        //On appelle le constructeur de la classe parent User
        super(prenom, nom, courriel, motDePasse, UserType, adresse);
        this.description=description;
        this.suspension="non";
        this.suspensionEndTime="";
        this.nombreRepasVendus="0";
        this.moyenne="";
        this.UserType="Cooker";
        this.chequeImageUrl=null ;
        this.suspended = false ; //suspension initialisée par défaut à false
    }
    public Cooker(String prenom,String nom, String courriel, String motDePasse,String UserType,String adresse,String description,String suspension,String suspensionEndTime,List<Plainte> listePlaintes){
        super(prenom, nom, courriel, motDePasse, UserType, adresse);
        this.listePlaintes=listePlaintes;
    };
    public Cooker(){
    }

    public List <Plainte> getList(){
        return listePlaintes;
    }

    public String getSuspension(){
        return suspension;
    }
    public String getSuspensionEndTime(){
        return suspensionEndTime;
    }

    public void setSuspensionEndTime(String suspensionEndTime) {
        this.suspensionEndTime = suspensionEndTime;
    }

    public void setSuspension(String suspension) {
        this.suspension = suspension;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setNote(float note){
        this.notesRecues.add(note);
    }

    @Override
    public String getCourriel() {
        return super.getCourriel();
    }

    public String getChequeImageURL() {
        return this.chequeImageUrl ;
    }

    public void setChequeImageURL(String data) {
        this.chequeImageUrl=data ;
    }


    public String getnombreRepasVendus(){
        return nombreRepasVendus;
    }


    public String getNoteMoyenne() {
        return moyenne;
    }

    public void setMoyenne(String moyenne) {
        this.moyenne=moyenne;
    }

}
