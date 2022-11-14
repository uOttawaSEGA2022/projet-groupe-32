package com.example.mealerapp;

import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Repas {
    private String idRepas;
    private String repasName;
    private String repasIngredients;
    private String descriptionRepas;
    private String cuisineType;
    private String repasType;
    private int price;
    Boolean repasStatus=false;

        public Repas(){}

    public Repas(String name, String repasIngredients, String descriptionRepas, String cuisineType, int price){
        repasName = name;
        this.repasIngredients = repasIngredients;
        this.descriptionRepas = descriptionRepas;
        this.cuisineType = cuisineType;
        this.idRepas = UUID.randomUUID().toString();
        //this.repasType = repasType;
        this.price = price;

    }
    public void addRepasDatabase(){
        FirebaseDatabase.getInstance().getReference("Repas").child(this.idRepas).setValue(this);
    }

    public String getRepasName() {return repasName;}
    public String getRepasIngredients() {return repasIngredients;}
    public String getRepasDescription() {return descriptionRepas;}
    public String getCuisineType() {return cuisineType;}
    public String getRepasType() {return repasType;}
    public String getId(){return idRepas;}
    public int getPrice() {return price;}

    public boolean repasRetirer(){return repasStatus;};
    public void traiterRepas(){
        if ( !this.repasStatus){
            this.repasStatus=true;
            return;
        }
        this.repasStatus=false;
    };
}
