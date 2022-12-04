package com.example.mealerapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DemandeListe extends ArrayAdapter<Demande> {

    private Activity context;
    ArrayList<Demande> demandeArrayList;

    public DemandeListe(Activity context, ArrayList<Demande> demandeArrayList) {
        super(context, R.layout.item_panier_view, demandeArrayList);
        this.context = context;
        this.demandeArrayList = demandeArrayList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.item_panier_view, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.panier_item_name);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.panier_item_Prix);

        Demande demande = demandeArrayList.get(position);
        textViewName.setText(demande.getRepas().getRepasName());
        textViewPrice.setText(String.valueOf(demande.getRepas().getPrice()));
        return listViewItem;
    }
}
