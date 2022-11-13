package com.example.mealerapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;


public class Plainte {
    private String idPlainte;
    private String titrePlainte;
    private String idClient;
    private String idCuisinier;
    private String datePlainte;
    private String descriptionPlainte;
    boolean plainteTraitee = false;//Par défaut toutes les plaintes sont non résolues

    public Plainte() {
    }

    public Plainte(String idPlainte, String titre_Plainte, String id_Client, String id_Cuisinier, String date_Plainte, String description_Plainte) {
        //On appelle le constructeur de la classe parent User;
        this.titrePlainte = titre_Plainte;
        this.idClient = id_Client;
        this.idCuisinier = id_Cuisinier;
        this.datePlainte = date_Plainte;//Touver comment stocker une date
        this.descriptionPlainte = description_Plainte;
        this.idPlainte = idPlainte;
        //UUID.randomUUID().toString();
    }

    public void setPlainteTraitee() {
        plainteTraitee = true;
    }

    public boolean getPlainteTraitee() {
        return plainteTraitee;
    }

    public String getDescriptionPlainte() {
        return descriptionPlainte;
    }

    public String getTitrePlainte() {
        return titrePlainte;
    }


    public void addPlainteDatabase() {
        FirebaseDatabase.getInstance().getReference("Plaintes").child(this.idPlainte).setValue(this);
    }


    public String getidPlainte() {
        return this.idPlainte;
    }

    public void setIdPlainte(String idPlainte) {
        this.idPlainte = idPlainte;
    }

    public String getIdClient() {
        return idClient;
    }

    public String getIdCuisinier() {
        return idCuisinier;
    }

    public String getDatePlainte() {
        return datePlainte;
    }

    public String toString() {
        return "Titre:" + titrePlainte + "\n" + "description : " + getDescriptionPlainte() + "\n";
    }

}

//        Plainte plainte1 = new Plainte(Id,"Indigeste", "amin_nna@gmail.com", "aguigma@gmail.com","03/11/2022","Le souci a été le tajine au veau et au miel avec abricots et pruneaux. La viande trop séche hélas et le tout trop sucré beaucoup trop. J ai donné ce que j ai pu à mon mari et laissé le reste. Puis j ai commandé une assiette de 3 fromages hélas non savoyards les 3. Et nous sommes en Savoie pays du fromage. Enfin mon mari a mangé mon dessert au marron et le sien hélas trop écoeurants surtout cette tarte à la praline.");
//        Plainte plainte2 = new Plainte(Id1,"Moisissure", "aichalfakir@gmail.com", "aguigma@gmail.com","03/11/2022","Il y'avait de la moisissure dans le repas que j'ai reçu");
//        Plainte plainte3 = new Plainte(Id2,"Brulé", "ydjido@gmail.com", "aguigma@gmail.com","03/11/2022","Je suis très déçu: un énorme goût de brulé . Je n’ai pas fini mon plat qui a fini à la poubelle. En espérant que ce soit juste une erreur qui sera vite réparée.");
//        Plainte plainte4 = new Plainte(Id3,"Intoxiqué", "imaneL@gmail.com", "aguigma@gmail.com","03/11/2022","Plus jamais je ne recommanderais chez ce cuisnier! J'ai passé une semaine à l'hopital pour intoxiquation alimentaire!");
//        Plainte plainte5 = new Plainte(Id4,"Inmangeable", "bertrand@gmail.com", "aguigma@gmail.com","03/11/2022","Le souci a été le tajine au veau et au miel avec abricots et pruneaux. La viande trop séche hélas et le tout trop sucré beaucoup trop. J ai donné ce que j ai pu à mon mari et laissé le reste. Puis j ai commandé une assiette de 3 fromages hélas non savoyards les 3. Et nous sommes en Savoie pays du fromage. Enfin mon mari a mangé mon dessert au marron et le sien hélas trop écoeurants surtout cette tarte à la praline.");
//        /
