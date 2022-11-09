package com.example.mealerapp;

public class Repas {
    private String repasName;
    private String repasIngredients;
    private String descriptionRepas;
    private String cuisineType;
    private String repasType;
    private int price;
    private String id;

    public Repas(String name, String repasIngredients, String descriptionRepas, String cuisineType, String repasType, int price, String id){
        repasName = name;
        this.repasIngredients = repasIngredients;
        this.descriptionRepas = descriptionRepas;
        this.cuisineType = cuisineType;
        this.repasType = repasType;
        this.price = price;
        this.id = id;
    }

    public String getRepasName() {return repasName;}
    public String getRepasIngredients() {return repasIngredients;}
    public String getRepasDescription() {return descriptionRepas;}
    public String getCuisineType() {return cuisineType;}
    public String getRepasType() {return repasType;}
    public int getPrice() {return price;}
    public String getId() {return id;}
}
