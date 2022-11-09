package com.example.mealerapp;

public class Repas {
    private String RepasName;
    private String RepasIngredients;
    private String DescriptionRepas;
    private String CuisineType;
    private String RepasType;
    private int Price;
    private String Id;

    public Repas(String name, String repasIngredients, String descriptionRepas, String cuisineType, String repasType, int price, String id){
        RepasName = name;
        RepasIngredients = repasIngredients;
        DescriptionRepas = descriptionRepas;
        CuisineType = cuisineType;
        RepasType = repasType;
        Price = price;
        Id = id;
    }

    public String getRepasName() {return RepasName;}
    public String getRepasIngredients() {return RepasIngredients;}
    public String getDescriptionRepas() {return DescriptionRepas;}
    public String getCuisineType() {return CuisineType;}
    public String getRepasType() {return RepasType;}
    public int getPrice() {return Price;}
    public String getId() {return Id;}
}
