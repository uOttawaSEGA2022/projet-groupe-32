package com.example.mealerapp;
import java.util.*;
import java.io.*;

public class Cooker extends User{
    String description;
    List<Plainte> listePlaintes = new ArrayList<Plainte>();
    String suspension;
    String suspensionEndTime;
    String UserType;
    //Constructeur

    //On a en paramettre les paramètres nécessaires à la création de l'objet
    public Cooker(String prenom,String nom, String courriel, String motDePasse,String UserType,String adresse,String description,String suspension,String suspensionEndTime,List<Plainte> listePlaintes){
        //On appelle le constructeur de la classe parent User
        super(prenom, nom, courriel, motDePasse, UserType, adresse);
        this.listePlaintes=listePlaintes;
        this.suspension="non";
        this.suspensionEndTime="";
        this.UserType=UserType;
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

    public String getTypeUser() {
        return "Cooker";
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
