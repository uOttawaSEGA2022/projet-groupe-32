package com.example.mealerapp.ui.gallery;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mealerapp.Demande;
import com.example.mealerapp.DemandeListe;
import com.example.mealerapp.Plainte;
import com.example.mealerapp.R;
import com.example.mealerapp.Repas;
import com.example.mealerapp.RepasListRecherche;
import com.example.mealerapp.admin_page_activity;
import com.example.mealerapp.databinding.FragmentPanierBinding;
import com.example.mealerapp.plainteListe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PanierFragment extends Fragment {
    ArrayList<Demande> demandeArrayList;
    ListView listViewRecherche;
    DatabaseReference myRef;
    private FragmentPanierBinding binding;
    private TextView itemName, itemPrice;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PanierViewModel panierViewModel =
                new ViewModelProvider(this).get(PanierViewModel.class);

        binding = FragmentPanierBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        myRef = FirebaseDatabase.getInstance().getReference("Demandes");
        demandeArrayList = new ArrayList<Demande>();
        listViewRecherche = root.findViewById(R.id.listViewPanier);
        listViewRecherche.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Demande demande = (Demande) listViewRecherche.getItemAtPosition(position);
                showHnadleDemande(demande);
                return true;
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                demandeArrayList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Demande demande = data.getValue(Demande.class);
                    String clientId = demande.getIdClient();
                    final String[] currentUserID = new String[1];
                    FirebaseDatabase.getInstance().getReference("Users").
                            child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("courriel").
                            addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    currentUserID[0] = snapshot.getValue(String.class);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                    if (clientId.equals(currentUserID)) {
                        demandeArrayList.add(demande);
                    }
                }
                DemandeListe demandesAdapter = new DemandeListe(getActivity(), demandeArrayList);
                listViewRecherche.setAdapter(demandesAdapter);
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
        FirebaseDatabase.getInstance().getReference("Achats").setValue(demande);
    }
    private void deleteDemande(Demande demande) {
        DatabaseReference data= FirebaseDatabase.getInstance().getReference("Achats").
                child(demande.getIdDemande());
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}