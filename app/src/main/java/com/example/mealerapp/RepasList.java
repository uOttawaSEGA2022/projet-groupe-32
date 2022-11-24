package com.example.mealerapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RepasList extends ArrayAdapter<Repas> {

    private Activity context;
    List<Repas> repas;

    public RepasList(Activity context, List<Repas> repas) {
        super(context, R.layout.repas_list, repas);
        this.context = context;
        this.repas = repas;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.repas_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);
        TextView textViewStatus = (TextView) listViewItem.findViewById(R.id.textViewStatus);

        Repas repa = repas.get(position);
        textViewName.setText(repa.getRepasName());
        textViewPrice.setText(String.valueOf(repa.getPrice()));
        if ( repa.getRepasStatus().equals("true")){
            textViewStatus.setText("Repas offert dans le menu");
        }
        else {
            textViewStatus.setText("Repas non offert dans le menu");
        }
        return listViewItem;
    }
}