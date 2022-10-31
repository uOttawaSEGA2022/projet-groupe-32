package com.example.mealerapp;

public class Administrator extends User{

        //Constructeur
        //On a en paramettre les paramètres nécessaires à la création de l'objet
        //Ce constructeur n'est pas vraiment nécessaire car l'admin se trouvera déjà dans la base de données

        public Administrator(String prenom,String nom, String courriel, String motDePasse,String adresse,String typeUser){
        super(prenom, nom, courriel, motDePasse, adresse);
            typeUser= "Admin";
            //On appelle le constructeur de la classe parent User

    }
}