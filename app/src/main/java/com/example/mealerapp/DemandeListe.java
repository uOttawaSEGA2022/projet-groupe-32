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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DemandeListe extends ArrayAdapter<Demande> {

    private final Activity context;
    List<Demande> demande;

    public DemandeListe(Activity context, List<Demande> demande) {
        super(context, R.layout.item_panier_view, demande);
        this.context = context;
        this.demande = demande;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.item_panier_view, null, true);


        TextView textViewName = (TextView) listViewItem.findViewById(R.id.panier_item_name);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.panier_item_Prix);

        Demande demande1 = demande.get(position);
        textViewName.setText(demande1.getRepas().getRepasName());
        textViewPrice.setText(String.valueOf(demande1.getRepas().getPrice()));
        return listViewItem;
    }
}
