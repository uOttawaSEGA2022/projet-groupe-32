package com.example.mealerapp;

import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Repas {
    private boolean repasOffer = false;
    private String idRepas;
    private String repasName;
    private String repasIngredients;
    private String descriptionRepas;
    private String cuisineType;
    private String repasType;
    private Double price;
    private boolean repasStatus=false;
    public Repas() {
    }

    public Repas(String name, String repasIngredients,String cuisineType,Double price,String descriptionRepas) {
        repasName = name;
        this.repasIngredients = repasIngredients;
        this.descriptionRepas = descriptionRepas;
        this.cuisineType = cuisineType;
        this.idRepas = FirebaseDatabase.getInstance().getReference("Repas").push().getKey();
        //this.repasType = repasType;
        this.price = price;
        this.repasOffer = false;
        this.repasStatus = false;
    }

    public String getRepasName() {
        return repasName;
    }

    public String getRepasIngredients() {
        return repasIngredients;
    }

    public String getRepasDescription() {
        return descriptionRepas;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public String getRepasType() {
        return repasType;
    }

    public String getId() {
        return idRepas;
    }

    public Double getPrice() {
        return price;
    }

    public boolean getRepasOffer() {
        return repasOffer;
    }


    public boolean getStatus(){
        return repasStatus;
    }
    public void traiterRepas() {
        if (!this.repasStatus) {
            this.repasStatus = true;
            return;
        }
        this.repasStatus = false;
    }


}
