package com.example.mealerapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class orderListe extends ArrayAdapter<Demande> {
    private Activity context;
    List<Demande> demandes;
    public orderListe(Activity context, List<Demande> demandes) {
        super(context, R.layout.order_layout, demandes);
        this.context = context;
        this.demandes = demandes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.order_layout, null, true);

        TextView repasName = (TextView) listViewItem.findViewById(R.id.repasName);
        TextView OrderStatus = (TextView) listViewItem.findViewById(R.id.order_status);

        Demande demande = demandes.get(position);
        repasName.setText(demande.getRepas().getRepasName());
        //OrderStatus.setText(demande.getDemandeStatus());

        if ( demande.getDemandeExists().equals("false")){
            OrderStatus.setText("Commande non acceptee");
        }

        else{
            if(demande.getDemandeTraitee().equals("false")) {
                OrderStatus.setText("En cours");
            }
            else{
                OrderStatus.setText("Commande acceptee");
            }

        }
        return listViewItem;
    }
}
