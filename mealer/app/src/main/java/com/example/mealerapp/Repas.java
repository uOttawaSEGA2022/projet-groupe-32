package com.example.mealerapp;

import java.util.UUID;

public class Repas {
    private String idRepas;
    private String repasName;
    private String repasIngredients;
    private String descriptionRepas;
    private String cuisineType;
    private String repasType;
    private int price;

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

    public String getRepasName() {return repasName;}
    public String getRepasIngredients() {return repasIngredients;}
    public String getRepasDescription() {return descriptionRepas;}
    public String getCuisineType() {return cuisineType;}
    public String getRepasType() {return repasType;}
    public String getId(){return idRepas;}
    public int getPrice() {return price;}
}
