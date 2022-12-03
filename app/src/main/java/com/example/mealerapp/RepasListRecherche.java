package com.example.mealerapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RepasListRecherche extends ArrayAdapter<Repas> {

    private Activity context;
    List<Repas> repas;

    public RepasListRecherche(Activity context, List<Repas> repas) {
        super(context, R.layout.repas_list_recherche, repas);
        this.context = context;
        this.repas = repas;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.repas_list_recherche, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName1);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice1);
        TextView textViewNote=listViewItem.findViewById(R.id.textViewNote);

        Repas repa = repas.get(position);
        textViewName.setText(repa.getRepasName());
        textViewPrice.setText(String.valueOf(repa.getPrice()));
        FirebaseDatabase.getInstance().getReference("Users").
                child(repa.getIdCuisinier()).child("moyenne").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String note=snapshot.getValue(String.class);
                        textViewNote.setText(note);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return listViewItem;
    }
}
