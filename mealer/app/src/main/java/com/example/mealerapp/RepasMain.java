package com.example.mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RepasMain extends AppCompatActivity {


    ListView listViewRepas;
    List<Repas> repas;
    DatabaseReference databaseRepas ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repas_main);

        listViewRepas = (ListView) findViewById(R.id.listViewRepas);
        databaseRepas = FirebaseDatabase.getInstance().getReference("repas") ;

        repas = new ArrayList<>();


        listViewRepas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Repas repa = repas.get(i);
                showUpdateDeleteDialog(repa.getId(), repa.getRepasName());
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

                repas.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Repas repa = data.getValue(Repas.class) ;
                    repas.add(repa) ; }

                RepasListe repasAdapter = new RepasListe(RepasMain.this, repas) ;
                listViewRepas.setAdapter(repasAdapter) ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void showUpdateDeleteDialog(final String repasId, String RepasName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.offrir_retirer_repas, null);
        dialogBuilder.setView(dialogView);


        final Button buttonOffrir = (Button) dialogView.findViewById(R.id.buttonOffrirRepas);
        final Button buttonRetirer = (Button) dialogView.findViewById(R.id.buttonRetirerRepas);

        dialogBuilder.setTitle(RepasName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonOffrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonRetirer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retirerRepas(repasId);
                b.dismiss();
            }
        });
    }


    private boolean retirerRepas(String id) {

        DatabaseReference dbase = FirebaseDatabase.getInstance().getReference("repas").child(id) ;
        dbase.removeValue();
        Toast.makeText(getApplicationContext(), "Repas retiré", Toast.LENGTH_LONG).show();

        return true ;
    }


}