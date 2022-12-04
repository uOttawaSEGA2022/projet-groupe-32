package com.example.mealerapp.ui.gallery;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mealerapp.Client;
import com.example.mealerapp.Demande;
import com.example.mealerapp.DemandeListe;
import com.example.mealerapp.R;
import com.example.mealerapp.Repas;
import com.example.mealerapp.RepasListRecherche;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;


public class PanierFragment extends Fragment {
    ArrayList<Repas> repasArrayList;
    ListView listViewRecherche;
    DatabaseReference myRef;
    Client client;
    String uid;
@Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState){
    View view=inflater.inflate(R.layout.fragment_panier, container, false);
    return  view;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRef = FirebaseDatabase.getInstance().getReference("Users");
        listViewRecherche = (ListView) view.findViewById(R.id.listViewPanierp);
        listViewRecherche.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Repas repas = (Repas) listViewRecherche.getItemAtPosition(position);
                showHnadleDemande(repas);
                return true;
            }
        });
    }
    public void onStart() {
        super.onStart();
        repasArrayList=new ArrayList<Repas>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(data.toString())) {
                        client = data.getValue(Client.class);
                        repasArrayList=client.getPanier();
                    }
                }
                DemandeListe demandeListe = new DemandeListe(getActivity(), repasArrayList);
                if(repasArrayList!=null){
                    Log.i("PanierFragment","la taille de demandeArralist est   "+repasArrayList.size());
                    listViewRecherche.setAdapter(demandeListe);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void showHnadleDemande(Repas repas) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_to_order_dialog, null);
        dialogBuilder.setView(dialogView);
        final Button buttonAdd = (Button) dialogView.findViewById(R.id.addToOrders);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deletefromPanier);
        final TextView textViewName = (TextView) dialogView.findViewById(R.id.texviewnaame);
        final TextView textViewPrice = dialogView.findViewById(R.id.textViewPriice);
        textViewName.setText(repas.getRepasName());
        textViewPrice.setText(String.valueOf(repas.getPrice()));

        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
                AddtoOrders(repas);
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
                deleteDemande(repas);
            }
        });
    }

    private void AddtoOrders(Repas repas) {
        Demande demande = new Demande(FirebaseAuth.getInstance().getCurrentUser().getUid(), repas);
        demande.addDemandeDatabase();
    }


    private void deleteDemande(Repas repas) {
        repasArrayList.remove(repas);
        client.setPanier(repasArrayList);
    }
}