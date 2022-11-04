package com.example.mealerapp;
public  class User{
    String Prenom;
    String Nom;
    String Courriel;
    String MotDePasse;
    String Adresse;
    String UserType;

    public User (String prenom,String nom, String courriel, String motDePasse,String UserType, String adresse){
        this.Prenom = prenom;
        this.Nom = nom;
        this.Courriel = courriel;
        this.MotDePasse = motDePasse;
        this.Adresse = adresse;
        this.UserType= UserType;
    }

    public String getPrenom() {
        return Prenom;
    }

    public String getAdresse() {
        return Adresse;
    }

    public String getCourriel() {
        return Courriel;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public String getNom() {
        return Nom;
    }

    public String getUserType() {
        return UserType;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public void setCourriel(String courriel) {
        Courriel = courriel;
    }

    public void setMotDePasse(String motDePasse) {
        MotDePasse = motDePasse;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    //S'inscrire
    public void SignUp(){

    }

    //Se connecter
    public void SignIn(){

    }

    //Se déconnecter
    public void SignOut(User user){

    }
}
