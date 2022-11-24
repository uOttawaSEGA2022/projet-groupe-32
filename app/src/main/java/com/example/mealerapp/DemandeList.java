package com.example.mealerapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DemandeList extends ArrayAdapter<Demande> {


    private Activity context;
    List<Demande> demandes;

    public DemandeList(Activity context, List<Demande> demandes) {
        super(context, R.layout.demande_list_layout, demandes);
        this.context = context;
        this.demandes = demandes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.demande_list_layout, null, true);

        TextView textViewRepas = (TextView) listViewItem.findViewById(R.id.textViewRepas);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);

        Demande demande = demandes.get(position);
        textViewRepas.setText(demande.getRepas().getRepasName());
        textViewDate.setText(demande.getDate());
        return listViewItem;
    }
}
