package com.example.mealerapp;
import android.os.Bundle;
import android.text.TextUtils;
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


public class Plainte {

    String titrePlainte;
    String idClient;
    String id;
    String idCuisinier;
    String datePlainte;
    String descriptionPlainte;
    boolean plainteTraitee=false;//Par défaut toutes les plaintes sont non résolues

    public Plainte(){
    }
    public Plainte(String id,String titre_Plainte, String id_Client, String id_Cuisinier,String date_Plainte,String description_Plainte){
        //On appelle le constructeur de la classe parent User
        this.titrePlainte = titre_Plainte;
        this.idClient = id_Client;
        this.idCuisinier = id_Cuisinier;
        this.datePlainte = date_Plainte;//Touver comment stocker une date
        this.descriptionPlainte = description_Plainte;
        /*Plainte plainte1 = new Plainte("Indigeste", "amin_nna@gmail.com", "aguigma@gmail.com","03/11/2022","Le souci a été le tajine au veau et au miel avec abricots et pruneaux. La viande trop séche hélas et le tout trop sucré beaucoup trop. J ai donné ce que j ai pu à mon mari et laissé le reste. Puis j ai commandé une assiette de 3 fromages hélas non savoyards les 3. Et nous sommes en Savoie pays du fromage. Enfin mon mari a mangé mon dessert au marron et le sien hélas trop écoeurants surtout cette tarte à la praline.");
        Plainte plainte2 = new Plainte("Moisissure", "aichalfakir@gmail.com", "aguigma@gmail.com","03/11/2022","Il y'avait de la moisissure dans le repas que j'ai reçu");
        Plainte plainte3 = new Plainte("Brulé", "ydjido@gmail.com", "aguigma@gmail.com","03/11/2022","Je suis très déçu: un énorme goût de brulé . Je n’ai pas fini mon plat qui a fini à la poubelle. En espérant que ce soit juste une erreur qui sera vite réparée.");
        Plainte plainte4 = new Plainte("Intoxiqué", "imaneL@gmail.com", "aguigma@gmail.com","03/11/2022","Plus jamais je ne recommanderais chez ce cuisnier! J'ai passé une semaine à l'hopital pour intoxiquation alimentaire!");
        Plainte plainte5 = new Plainte("Inmangeable", "bertrand@gmail.com", "aguigma@gmail.com","03/11/2022","Le souci a été le tajine au veau et au miel avec abricots et pruneaux. La viande trop séche hélas et le tout trop sucré beaucoup trop. J ai donné ce que j ai pu à mon mari et laissé le reste. Puis j ai commandé une assiette de 3 fromages hélas non savoyards les 3. Et nous sommes en Savoie pays du fromage. Enfin mon mari a mangé mon dessert au marron et le sien hélas trop écoeurants surtout cette tarte à la praline.");
        Plainte depose = plainte1;
        FirebaseDatabase.getInstance().getReference("Plaintes").child("tDFGgfhcvh3FDCBhg").setValue(depose);
        FirebaseDatabase.getInstance().getReference("Plaintes").child("S0DFfGshVkj5tfbghnjk").setValue(plainte2);
        FirebaseDatabase.getInstance().getReference("Plaintes").child("Dertdt3DGGVB3D45").setValue(plainte3);
        FirebaseDatabase.getInstance().getReference("Plaintes").child("HDFVG4544HVGCHBPIJhvh").setValue(plainte4);
        FirebaseDatabase.getInstance().getReference("Plaintes").child("qQWEr2345wqrwet4356kjg8").setValue(plainte5);
        //FirebaseDatabase.getInstance().getReference("Plaintes").child(FirebaseAuth.getInstance().getCurrentUser().getEmail()).setValue(plainte5);
         */
    }

    public String getTitrePlainte() {
        return titrePlainte;
    }

    public boolean getPlainteTraitee(){
        return plainteTraitee;
    }

    public String getIdCuisinier() {
        return idCuisinier;
    }

    public String getId() {
        return id;
    }

    public void setPlainteTraitee(){
        plainteTraitee = true;
    }

    public String getPlainteDescription() {
        return descriptionPlainte;
    }
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Plaintes");
    public void addPlainte(){

        if(!TextUtils.isEmpty(this.getTitrePlainte())){

            String id =databaseReference.push().getKey();
            Plainte plainte =new Plainte(id,)
        }

    }
    /*
    public static void writeNewPlainte(String titre_Plainte, String id_Client, String id_Cuisinier,String date_Plainte,String description_Plainte) {
        Plainte plainte = new Plainte(titre_Plainte, id_Client, id_Cuisinier,date_Plainte,description_Plainte);
        FirebaseDatabase.getInstance().getReference().child("Plaintes").setValue(plainte);
    }
     */
}
