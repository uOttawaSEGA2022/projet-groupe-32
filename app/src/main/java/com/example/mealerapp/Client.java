package com.example.mealerapp;


import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Client extends User {
    String UserType;
    String CVC;
    String InformationsCarteCredit;
    //Constructeur
    //On a en paramettre les paramètres nécessaires à la création de l'objet

    public Client(String prenom, String nom, String courriel, String motDePasse, String UserType, String adressecourriel) {
        super(prenom, nom, courriel, motDePasse, UserType, adressecourriel);
        this.CVC = "";
        this.InformationsCarteCredit = "";
        this.UserType = "Client";
        //On appelle le constructeur de la classe parent User
    }

    public Client() {

    }

    public String getInformationsCarteCredit() {
        return InformationsCarteCredit;
    }

    public String getTypeUser() {
        return "Client";
    }


    public void setInformationsCarteCredit(String cvv, String informationsCarteCredit) {
        InformationsCarteCredit = informationsCarteCredit + " " + cvv;
    }

    public ArrayList<Repas> RechercherTypeCuisine(String rechercheCle) {
        //recupuer les resultats d'une recherche selon le typeDecuisine
        ArrayList<Repas> repasList = null;
        DatabaseReference dataRepas;
        rechercheCle = rechercheCle.trim().toLowerCase();
        dataRepas = FirebaseDatabase.getInstance().getReference("Repas");
        String finalRechercheCle = rechercheCle;
        // recupere tout les repas ayant ce mot dans leur type de cuisine
        dataRepas.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot data : snapshot.getChildren()) {
                            Repas repas = data.getValue(Repas.class);
                            String repasCuisineType = repas.getCuisineType().toString().trim().toLowerCase();
                            String[] repasType = repasCuisineType.split(" ");
                            for (String s : repasType) {
                                if (s.equals(finalRechercheCle)) {
                                    repasList.add(repas);
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
        //retirer les repas appartenant aux cuisinier qui sont suspendu du resultats de la recherche
        if (!repasList.isEmpty()) {
            for (Repas repa : repasList) {
                final String[] suspension = new String[1];
                String repasCuisinierId = repa.getIdCuisinier();
                FirebaseDatabase.getInstance().getReference("Users").
                        child(repasCuisinierId).child("suspension").
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                suspension[0] = snapshot.getValue(String.class).trim();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                if (suspension[0].equals("oui Temporairement") || suspension[0].equals("oui Indefinement")) {
                    repasList.remove(repa);
                }
            }
        }
        return repasList;
    }

    public ArrayList<Repas> RechercherRepasParNom(String rechercheCle) {
        //recupere la liste des repas dont le nom correspond aux mots de recherche
        ArrayList<Repas> repasList = null;
        DatabaseReference dataRepas;
        rechercheCle = rechercheCle.toLowerCase();
        dataRepas = FirebaseDatabase.getInstance().getReference("Repas");
        String finalRechercheCle = rechercheCle;
        dataRepas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Repas repas = data.getValue(Repas.class);
                    String repasNom = repas.getRepasName().toString().trim().toLowerCase();
                    String[] repasNom2 = repasNom.split(" ");
                    String[] rechercheCler = finalRechercheCle.split(" ");
                    for (String str : rechercheCler) {
                        if (str.length() > 2) {
                            for (String s : repasNom2) {
                                if (s.equals(str)) {
                                    if (!repasList.contains(repas)){
                                        repasList.add(repas);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (!repasList.isEmpty()) {
            for (Repas repa : repasList) {
                final String[] suspension = new String[1];
                String repasCuisinierId = repa.getIdCuisinier();
                FirebaseDatabase.getInstance().getReference("Users").
                        child(repasCuisinierId).child("suspension").
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                suspension[0] = snapshot.getValue(String.class).trim();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                if (suspension[0].equals("oui Temporairement") || suspension[0].equals("oui Indefinement")) {
                    repasList.remove(repa);
                }
            }
        }

        return repasList;
    }

}