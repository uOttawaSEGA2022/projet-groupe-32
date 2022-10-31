package com.example.mealerapp;
public  class User{
    String Prenom;
    String Nom;
     String Courriel;
     String MotDePasse;
     String Adresse;
     String UserType;

    //Constructeur
    public User(){

    }
    public User (String prenom,String nom, String courriel, String motDePasse, String adresse){
        this.Prenom = prenom;
        this.Nom = nom;
        this.Courriel = courriel;
        this.MotDePasse = motDePasse;
        this.Adresse = adresse;
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
        //Récupérer les entrées de l'utilisateur dans des variables
        //Ajouter un ligne a la table User avec les contenus des variables récupérés


    }

    //Se connecter
    public void SignIn(){
        //Recupérer email entré
        //Récupérer mot de passe entré
        //Regarder dans la table à la ligne où se trouve l'email si le MotDePasse est identique

    }

    //Se déconnecter
    public void SignOut(User user){

    }
}
