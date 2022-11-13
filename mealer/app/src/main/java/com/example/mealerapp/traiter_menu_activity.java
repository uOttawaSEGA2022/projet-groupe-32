package com.example.mealerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

    DatabaseReference databaseRepas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traiter_menu);

        listViewRepas = (ListView) findViewById(R.id.listViewRepas);
        databaseRepas = FirebaseDatabase.getInstance().getReference("Repas");
        repasArrayList = new ArrayList<>();

        listViewRepas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Repas repas = repasArrayList.get(i);
                showOffrirRetirer(repas);
                return true;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseRepas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                repasArrayList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Repas repas = data.getValue(Repas.class) ;
                    assert repas != null;
                    if ( !repas.repasRetirer()) {
                        repasArrayList.add(repas);
                    }
                }

                ArrayAdapter<Repas> repasAdapter = new RepasList(traiter_menu_activity.this, repasArrayList) ;
                listViewRepas.setAdapter(repasAdapter) ;
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


        final Button buttonOffrirRepas = (Button) dialogView.findViewById(R.id.buttonOffrirRepas);
        final Button buttonRetirerRepas = (Button) dialogView.findViewById(R.id.buttonRetirerRepas);

        dialogBuilder.setTitle("");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonOffrirRepas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offrirRepas(repas);
                b.dismiss();
            }
        });

        buttonRetirerRepas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retirerRepas(repas);
                b.dismiss();
            }
        });

    }

    private void offrirRepas(Repas repas) {

        repas.traiterRepas();
        Toast.makeText(getApplicationContext(), "Repas offert", Toast.LENGTH_LONG).show();
    }

    private void retirerRepas(Repas repas) {

        repas.traiterRepas();
        Toast.makeText(getApplicationContext(), "Repas retiré", Toast.LENGTH_LONG).show();
    }




}