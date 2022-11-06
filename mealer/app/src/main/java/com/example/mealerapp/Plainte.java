package com.example.mealerapp;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Plainte {
    String titrePlainte;
    String idClient;
    String idCuisinier;
    String datePlainte;
    String descriptionPlainte;
    boolean plainteTraitee=false;//Par défaut toutes les plaintes sont non résolues


    public Plainte(){
    }

    public Plainte(String titre_Plainte, String id_Client, String id_Cuisinier,String date_Plainte){
        //On appelle le constructeur de la classe parent User
        titrePlainte = titre_Plainte;
        idClient = id_Client;
        idCuisinier = id_Cuisinier;
        datePlainte = date_Plainte;//Touver comment stocker une date
    }

    public boolean getPlainteTraitee(){
        return plainteTraitee;
    }

    public void setPlainteTraitee(){
        plainteTraitee = true;
    }

    public void writeNewPlainte(String titre_Plainte, String id_Client, String id_Cuisinier,String date_Plainte) {
        Plainte plainte = new Plainte(titre_Plainte, id_Client, id_Cuisinier,date_Plainte);
        FirebaseDatabase.getInstance().getReference().child("Plainte").setValue(plainte);
    }

}
