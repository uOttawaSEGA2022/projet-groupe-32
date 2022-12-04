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

import com.example.mealerapp.Demande;
import com.example.mealerapp.DemandeListe;
import com.example.mealerapp.R;
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
    ArrayList<Demande> demandeArrayList;
    ListView listViewRecherche;
    DatabaseReference myRef;
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
        myRef = FirebaseDatabase.getInstance().getReference("Demandes");
        listViewRecherche = (ListView) view.findViewById(R.id.listViewPanierp);
        listViewRecherche.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Demande demande = (Demande) listViewRecherche.getItemAtPosition(position);
                showHnadleDemande(demande);
                return true;
            }
        });
    }

    public void onStart() {
        super.onStart();
        demandeArrayList = new ArrayList<Demande>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                demandeArrayList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Demande demande1 = data.getValue(Demande.class);
                    String clientId = demande1.getIdClient();
                    if (clientId.equals(uid)) {
                        Log.i("PanierFragment","je suis egale a "+uid+"   "+clientId);
                        demandeArrayList.add(demande1);
                    }
                }
                DemandeListe demandeListe = new DemandeListe(getActivity(), demandeArrayList);
                if(demandeArrayList!=null){
                    Log.i("PanierFragment","la taille de demandeArralist est   "+demandeArrayList.size());
                    listViewRecherche.setAdapter(demandeListe);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void showHnadleDemande(Demande demande) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_to_order_dialog, null);
        dialogBuilder.setView(dialogView);
        final Button buttonAdd = (Button) dialogView.findViewById(R.id.addToOrders);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.deletefromPanier);
        final TextView textViewName = (TextView) dialogView.findViewById(R.id.texviewnaame);
        final TextView textViewPrice = dialogView.findViewById(R.id.textViewPriice);
        textViewName.setText(demande.getRepas().getRepasName());
        textViewPrice.setText(String.valueOf(demande.getRepas().getPrice()));

        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
                AddtoOrders(demande);
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
                deleteDemande(demande);
            }

        });
    }

    private void AddtoOrders(Demande demande) {
        FirebaseDatabase.getInstance().getReference("Achats").child(UUID.randomUUID().toString()).setValue(demande);
        deleteDemande(demande);
    }


    private void deleteDemande(Demande demande) {
        DatabaseReference data = FirebaseDatabase.getInstance().getReference("Demandes").child(demande.getIdDemande());
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}