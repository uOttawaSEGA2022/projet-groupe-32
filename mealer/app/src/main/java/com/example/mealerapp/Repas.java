package com.example.mealerapp;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Repas {
    private String idRepas;
    private String repasName;
    private String repasIngredients;
    private String descriptionRepas;
    private String cuisineType;
    private int price;
    private String repasStatus;

    public Repas(){
        }



    public Repas(String name, String repasIngredients, String descriptionRepas, String cuisineType, int price){
        this.repasName = name;
        this.repasIngredients = repasIngredients;
        this.descriptionRepas = descriptionRepas;
        this.cuisineType = cuisineType;
        this.idRepas = UUID.randomUUID().toString();
        this.price = price;
        this.repasStatus= "false";

    }
    public void addRepasDatabase(){
        FirebaseDatabase.getInstance().getReference("Repas").child(idRepas).setValue(this);
    }

    public String getRepasName() {return repasName;}
    public String getRepasIngredients() {return repasIngredients;}
    public String getRepasDescription() {return descriptionRepas;}
    public String getCuisineType() {return cuisineType;}
    public String getId(){return this.idRepas;}
    public int getPrice() {return price;}


    public  void traiterRepas(){
        if ( this.repasStatus=="false"){
            Log.i("Traiter un repas",  this.repasStatus);
            this.repasStatus="true";
            Log.i("Traiter un repas",  this.repasStatus);
            return;
        }
        else {
            Log.i("Traiter un repas",  this.repasStatus + " id : " + this.idRepas);
            this.repasStatus = "false";
            Log.i("Traiter un repas",  this.repasStatus);

        }
    };

    public String getRepasStatus(){
        return this.repasStatus;
    }
}
