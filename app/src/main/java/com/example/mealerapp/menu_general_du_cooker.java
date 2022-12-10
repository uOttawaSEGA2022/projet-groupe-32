package com.example.mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class menu_general_du_cooker extends AppCompatActivity {
    ListView listViewRepas;
    FirebaseAuth mAuth;
    List<Repas> repasArrayList;

    DatabaseReference databaseRepas;
    public ImageButton LogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_general_du_cooker);
        mAuth = FirebaseAuth.getInstance();
        LogOut = (ImageButton) findViewById(R.id.logOUt_Button);
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentL = new Intent(menu_general_du_cooker.this, MainActivity.class);
                startActivity(intentL);
                finish();
                Toast.makeText(menu_general_du_cooker.this, "Succesfully Logout", Toast.LENGTH_LONG).show();
            }
        });

        listViewRepas = (ListView) findViewById(R.id.listViewRepasTotal);
        databaseRepas = FirebaseDatabase.getInstance().getReference("Repas");
        repasArrayList = new ArrayList<>();

        listViewRepas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Repas repas = repasArrayList.get(i);
                Log.i("Repas cliqué", repas.getRepasStatus() + " id : " + repas.getIdRepas());
                showRetirerRepas(repas);
                return true;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        String uid = mAuth.getCurrentUser().getUid();
        databaseRepas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                repasArrayList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {

                    Repas repas = data.getValue(Repas.class);
                    String repasUid = repas.getIdCuisinier();
                    Log.i("Repas non offert", repas.getRepasStatus() + " id : " + repas.getIdRepas());
                    if (repasUid.equals(uid)) {
                        repasArrayList.add(repas);
                    }
                    //repasArrayList.add(repas);
                }

                RepasList repasAdapter = new RepasList(menu_general_du_cooker.this, repasArrayList);
                listViewRepas.setAdapter(repasAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void showRetirerRepas(Repas repas) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.retirer_repas_menu_dialogue, null);
        dialogBuilder.setView(dialogView);


        final Button buttonRetirerRepas = (Button) dialogView.findViewById(R.id.button_retirer_Repas);
        final Button buttonChangerStatusRepas = (Button) dialogView.findViewById(R.id.button_changerStatus_Repas);
        dialogBuilder.setTitle("");
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonRetirerRepas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                retirerRepas(repas);
                b.dismiss();
            }
        });
        buttonChangerStatusRepas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offrirdesoffrirRepas(repas);
                b.dismiss();
            }
        });

    }


    private void offrirdesoffrirRepas(Repas repas) {

        repas.traiterRepas(repas);
        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();

    }

    public void retirerRepas(Repas repas) {

        // supprimer un repas de la base des données

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Repas").child(repas.getIdRepas());
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                database.removeValue();
                Toast.makeText(getApplicationContext(), "Repas retiré de la base de donnée", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}