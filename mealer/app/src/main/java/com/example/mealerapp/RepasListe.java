package com.example.mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RepasListe extends ArrayAdapter<Repas> {

    private Activity context;
    List<Repas> repas;

    public RepasListe(Activity context, List<Repas> repas) {
        super(context, R.layout.activity_repas_liste, repas);
        this.context = context;
        this.repas = repas;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_repas_liste, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);

        Repas repa = repas.get(position);
        textViewName.setText(repa.getRepasName());
        textViewPrice.setText(String.valueOf(repa.getPrice()));
        return listViewItem;
    }




}