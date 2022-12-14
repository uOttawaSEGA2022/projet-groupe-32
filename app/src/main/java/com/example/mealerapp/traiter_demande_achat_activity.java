package com.example.mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class traiter_demande_achat_activity extends AppCompatActivity {


    ListView listViewDemandes;

    ArrayList<Demande> demandesArrayList;

    DatabaseReference databaseDemandes;

    FirebaseAuth mAuth;



    String idConnectedCooker;
    public ImageButton LogOut1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.traiter_demande_achat);
        LogOut1 = (ImageButton)findViewById(R.id.logout_Button_traiter_demande);
        LogOut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentL=new Intent(traiter_demande_achat_activity.this,MainActivity.class);
                startActivity(intentL);
                finish();
                Toast.makeText(traiter_demande_achat_activity.this,"Succesfully Logout",Toast.LENGTH_LONG).show();
            }
        });


        mAuth=FirebaseAuth.getInstance();
        idConnectedCooker = mAuth.getCurrentUser().getUid();

        listViewDemandes = (ListView) findViewById(R.id.listViewDemandes);
        databaseDemandes = FirebaseDatabase.getInstance().getReference("Demandes");
        demandesArrayList = new ArrayList<Demande>();


        listViewDemandes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Demande demande = (Demande) listViewDemandes.getItemAtPosition(i);
                Log.i("Demande ajout??e",  demande.getDemandeTraitee() + " id : " + demande.getIdDemande());
                showAccepterRejeter(demande);
                return true;
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        /*
        Repas repas = new Repas("Toast ?? l'avocat et au saumon fum??","Tartinade de cajou ?? l'aneth, ??uf poch??, oignons rouge et graines de s??same","Repas d??licieux et nutritionnel", "Europ??enne", 25, "QBK2NcQWtQWy2y6WmhkpezrbP452");
        Demande demande = new Demande("amin_nna@gmail.com", repas);
        demande.addDemandeDatabase();
         */
        databaseDemandes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                demandesArrayList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Demande demande = data.getValue(Demande.class);
                    Log.i("Demande parcourue ",  demande.getDemandeTraitee() + " id : " + demande.getIdDemande());
                    if (demande.getRepas().getIdCuisinier().equals(idConnectedCooker) && demande.getDemandeExists().equals("true") && demande.getDemandeTraitee().equals("false")) {
                        demandesArrayList.add(demande);
                }
                }
                Log.i("Demande parcourue ",  "La taille de demandeArrayListe est   "+demandesArrayList.size());
                DemandeList demandesAdapter = new DemandeList(traiter_demande_achat_activity.this, demandesArrayList) ;
                listViewDemandes.setAdapter(demandesAdapter) ;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void showAccepterRejeter(Demande demande) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.traiter_demande_dialogue, null);
        dialogBuilder.setView(dialogView);


        final Button buttonAccepterDemande = (Button) dialogView.findViewById(R.id.buttonAccepterDemande);
        final Button buttonRefuserDemande = (Button) dialogView.findViewById(R.id.buttonRefuserDemande);

        dialogBuilder.setTitle("");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonAccepterDemande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accepterDemande(demande);
                b.dismiss();
            }
        });

        buttonRefuserDemande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refuserDemande(demande);
                b.dismiss();



            }
        });

    }

    private void accepterDemande(Demande demande) {

        demande.traiterDemande();

        //FirebaseDatabase.getInstance().getReference("Demandes").child(demande.getIdDemande()).child("demandeTraitee").setValue("true");
        Toast.makeText(getApplicationContext(), "Demande accept??e", Toast.LENGTH_LONG).show();

    }

    private void refuserDemande(Demande demande) {

        demande.rejetterDemande(demande);
        Toast.makeText(getApplicationContext(), "Demande refus??e", Toast.LENGTH_LONG).show();

    }




}