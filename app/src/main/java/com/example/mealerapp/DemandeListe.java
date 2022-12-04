package com.example.mealerapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DemandeListe extends ArrayAdapter<Repas> {

    private Activity context;
    ArrayList<Repas> repasArrayList;

    public DemandeListe(Activity context, ArrayList<Repas> repasArrayList) {
        super(context, R.layout.item_panier_view, repasArrayList);
        this.context = context;
        this.repasArrayList = repasArrayList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.item_panier_view, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.panier_item_name);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.panier_item_Prix);

        Repas repas =repasArrayList.get(position);
        textViewName.setText(repas.getRepasName());
        textViewPrice.setText(String.valueOf(repas.getPrice()));
        return listViewItem;
    }
}
