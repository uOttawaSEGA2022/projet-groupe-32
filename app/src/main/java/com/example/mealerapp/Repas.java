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
    private double price;
    private String repasStatus;
    private String idCuisinier;

    public Repas() {
    }


    public Repas(String name, String repasIngredients, String descriptionRepas, String cuisineType, double price, String idCuisinierC) {
        this.repasName = name;
        this.repasIngredients = repasIngredients;
        this.descriptionRepas = descriptionRepas;
        this.cuisineType = cuisineType;
        this.idRepas = UUID.randomUUID().toString();
        this.idCuisinier = idCuisinierC;
        this.price = price;
        this.repasStatus = "false";

    }

    public void addRepasDatabase() {
        FirebaseDatabase.getInstance().getReference("Repas").child(idRepas).setValue(this);
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

    public String getIdRepas() {
        return this.idRepas;
    }

    public String getIdCuisinier() {
        return this.idCuisinier;
    }

    public double getPrice() {
        return price;
    }

    public void setRepasStatus(String status) {
        this.repasStatus = status;
    }

    public boolean traiterRepas(Repas repas) {
        boolean a=false;
        if (repas.getRepasStatus() == "false") {
            a=true;
            repas.setRepasStatus("true");
            FirebaseDatabase.getInstance().getReference("Repas").child(repas.getIdRepas()).child("repasStatus").setValue("true");
            return a;
        } else {
            repas.setRepasStatus("false");
            FirebaseDatabase.getInstance().getReference("Repas").child(repas.getIdRepas()).child("repasStatus").setValue("false");
        }
        return a;
    }

    ;

    public String getRepasStatus() {
        return repasStatus;
    }

    public String getId() {
        return idRepas;
    }

}
