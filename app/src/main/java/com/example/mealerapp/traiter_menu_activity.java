package com.example.mealerapp;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class traiter_menu_activity extends AppCompatActivity {

    ListView listViewRepas;

    List<Repas> repasArrayList;
    FirebaseAuth mAuth;
    DatabaseReference databaseRepas;
    public ImageButton LogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traiter_menu);
        mAuth = FirebaseAuth.getInstance();
        LogOut = (ImageButton) findViewById(R.id.logout_Button);
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentL = new Intent(traiter_menu_activity.this, MainActivity.class);
                startActivity(intentL);
                finish();
                Toast.makeText(traiter_menu_activity.this, "Succesfully Logout", Toast.LENGTH_LONG).show();
            }
        });

        listViewRepas = (ListView) findViewById(R.id.listViewRepas);
        databaseRepas = FirebaseDatabase.getInstance().getReference("Repas");
        repasArrayList = new ArrayList<>();

        listViewRepas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Repas repas = repasArrayList.get(i);
                Log.i("Repas cliqué", repas.getRepasStatus() + " id : " + repas.getIdRepas());
                showOffrirRetirer(repas);
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
                    if (repas.getRepasStatus().equals("true") && uid.equals(repasUid)) {
                        repasArrayList.add(repas);
                    }
                    //repasArrayList.add(repas);
                }

                RepasList repasAdapter = new RepasList(traiter_menu_activity.this, repasArrayList);
                listViewRepas.setAdapter(repasAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void showOffrirRetirer(Repas repas) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.traiter_menu_dialogue, null);
        dialogBuilder.setView(dialogView);


        final Button buttonChangerStatusRepas = (Button) dialogView.findViewById(R.id.buttonChangerStatusRepas);

        dialogBuilder.setTitle("");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonChangerStatusRepas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offrirdesoffrirRepas(repas);
                b.dismiss();
            }
        });
    }

    private void offrirdesoffrirRepas(Repas repas) {
        if(repas.traiterRepas(repas)==true) {
            ;
            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Retirer", Toast.LENGTH_LONG).show();
        }
    }

}
