package com.example.mealerapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class plainteListe extends ArrayAdapter<Plainte> {

    private Activity context;
    List<Plainte> plaintes;

    public plainteListe(Activity context, List<Plainte> plaintes) {
        super(context, R.layout.plaint_display_layout, plaintes);
        this.context = context;
        this.plaintes = plaintes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.plaint_display_layout, null, true);

        TextView textViewTitre = (TextView) listViewItem.findViewById(R.id.textViewPlaintTitle);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewPlaintDescription);

        Plainte plainte = plaintes.get(position);
        textViewTitre.setText(plainte.getTitrePlainte());
        //textViewDescription.setText(plainte.getDescriptionPlainte());
        return listViewItem;
    }
}
