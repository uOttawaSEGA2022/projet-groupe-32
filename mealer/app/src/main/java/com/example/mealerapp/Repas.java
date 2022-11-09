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
        repasIngredients = repasIngredients;
        descriptionRepas = descriptionRepas;
        cuisineType = cuisineType;
        repasType = repasType;
        price = price;
        id = id;
    }

    public String getRepasName() {return repasName;}
    public String getRepasIngredients() {return repasIngredients;}
    public String getDescriptionRepas() {return descriptionRepas;}
    public String getCuisineType() {return cuisineType;}
    public String getRepasType() {return repasType;}
    public int getPrice() {return price;}
    public String getId() {return id;}
}
