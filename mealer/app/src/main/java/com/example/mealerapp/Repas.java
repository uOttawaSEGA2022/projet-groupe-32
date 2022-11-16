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
    private String idCuisinier;

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
    public String getIdRepas(){return this.idRepas;}
    public int getPrice() {return price;}


    public  void traiterRepas(Repas repas){
        if ( this.repasStatus=="false"){
            this.repasStatus="true";
            FirebaseDatabase.getInstance().getReference("Repas").child(repas.getIdRepas()).child("repasStatus").setValue("true");
            return;
        }
        else {
            this.repasStatus = "false";
            FirebaseDatabase.getInstance().getReference("Repas").child(repas.getIdRepas()).child("repasStatus").setValue("false");

        }
    };

    public String getRepasStatus(){
        return this.repasStatus;
    }
}
