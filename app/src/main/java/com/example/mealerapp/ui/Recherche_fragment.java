package com.example.mealerapp.ui;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealerapp.Client;
import com.example.mealerapp.Cooker;
import com.example.mealerapp.Demande;
import com.example.mealerapp.DemandeListe;
import com.example.mealerapp.R;
import com.example.mealerapp.Repas;
import com.example.mealerapp.RepasList;
import com.example.mealerapp.RepasListRecherche;
import com.example.mealerapp.client_page_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Recherche_fragment extends Fragment {
    ArrayList<Repas> repasArrayList;
    ListView listViewRecherche;
    Client client;
    DatabaseReference myRef;
    private SearchView searchView;
    View root;
    String suspension = "";
    DatabaseReference connectedClientpanier;

    public Recherche_fragment() {
        // Required empty public constructor
    }

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_recherche_fragment, container, false);
        searchView = root.findViewById(R.id.searchRecherche);
        searchView.clearFocus();
        myRef = FirebaseDatabase.getInstance().getReference("Users");
        listViewRecherche = root.findViewById(R.id.listViewRecherche);
        connectedClientpanier = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("panier");
        ;
        DatabaseReference databaseRepas = FirebaseDatabase.getInstance().getReference("Repas");
        repasArrayList = new ArrayList<Repas>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            boolean a = false;

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                Log.i("client_page_activity", "la cle recherche esttttttttttttttttttttttt  " + query.toString());
                repasArrayList = RechercherTypeCuisineAndNom(query);
                RepasListRecherche repasAdapter = new RepasListRecherche(getActivity(), repasArrayList);
                if (!(repasArrayList == null)) {
                    listViewRecherche.setAdapter(repasAdapter);
                    a = true;
                } else {
                    Toast.makeText(getActivity(), "Aucun produit ne correspond a votre recherche", Toast.LENGTH_LONG).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (a == true) {
                    repasArrayList = RechercherTypeCuisineAndNom(newText);
                    RepasListRecherche repasAdapter = new RepasListRecherche(getActivity(), repasArrayList);
                    if (!(repasArrayList == null)) {
                        listViewRecherche.setAdapter(repasAdapter);
                    } else {
                        Toast.makeText(getActivity(), "Aucun produit ne correspond a votre recherche", Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });
        listViewRecherche.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Repas repas = (Repas) listViewRecherche.getItemAtPosition(position);
                showAddToPanierDialog(repas);
                return true;
            }
        });
        return root;
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_recherche_fragment, container, false);
//    }


    public ArrayList<Repas> RechercherTypeCuisineAndNom(String rechercheCle) {
        //recupuer les resultats d'une recherche selon le typeDecuisine
        ArrayList<Repas> repasList = new ArrayList<Repas>();
        DatabaseReference dataRepas;
        rechercheCle = rechercheCle.trim().toLowerCase();
        dataRepas = FirebaseDatabase.getInstance().getReference("Repas");
        String finalRechercheCle = rechercheCle;
        // recupere tout les repas ayant ce mot dans leur type de cuisine
        Log.i("client_page_activity", "la cle recherche est dans rechercheeeeeeeeeeeeeeee " + rechercheCle);
        dataRepas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {
                    Repas repas = data.getValue(Repas.class);
                    String repasCuisineType = repas.getCuisineType().trim().toLowerCase();
                    String[] repasType = repasCuisineType.split(" ");
                    for (String s : repasType) {
                        if (s.equals(finalRechercheCle)) {
                            if (!(repasList == null)) {
                                if (!(repasList.contains(repas))) {
                                    Log.i("client_page_activity", "la liste contient ttttttttttttttt " + finalRechercheCle);
                                    repasList.add(repas);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        dataRepas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Repas repas = data.getValue(Repas.class);
                    String repasNom = repas.getRepasName().toString().trim().toLowerCase();
                    String[] repasNom2 = repasNom.split(" ");
                    String[] rechercheCler = finalRechercheCle.split(" ");
                    for (String str : rechercheCler) {
                        if (str.length() >= 2) {
                            for (String s : repasNom2) {
                                if (s.equals(str)) {
                                    if (!(repasList == null)) {
                                        if (!(repasList.contains(repas))) {
                                            Log.i("client_page_activity", "la liste contient 3333333333333333333333333 " + finalRechercheCle);
                                            repasList.add(repas);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!(repasList == null)) {
                    if (!repasList.isEmpty()) {
                        for (int i = 0; i< repasList.size();i++) {
                            Repas repa = repasList.get(i);
                            String status = repa.getRepasStatus();
                            String repasCuisinierId = repa.getIdCuisinier();
                            FirebaseDatabase.getInstance().getReference("Users").child(repasCuisinierId).
                                    addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Cooker cooker = snapshot.getValue(Cooker.class);
                                            suspension = cooker.getSuspension().trim().toLowerCase();
                                            Log.i("client_page_activity", "suspension " + suspension);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                            if ((suspension.equals("oui temporairement")) || (suspension.equals("oui indefinement")) ) {
                                repasList.remove(repa);
                                Log.i("client_page_activity", "la liste contient 444444444444444444444444 " + status);
                            }
                            if((status.equals("false"))){
                                repasList.remove(repa);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //retirer les repas appartenant aux cuisinier qui sont suspendu du resultats de la recherche

        return repasList;
    }


    private void showAddToPanierDialog(Repas repas) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_to_panier_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonAdd = (Button) dialogView.findViewById(R.id.addtopanier);
        final TextView textViewName = (TextView) dialogView.findViewById(R.id.textViewName11);
        final TextView textViewPrice = (TextView) dialogView.findViewById(R.id.textViewPrice11);
        final TextView textViewCuisineType = dialogView.findViewById(R.id.textViewCuisineType);
        final TextView textViewIngredient = dialogView.findViewById(R.id.textViewIngredients);
        final TextView textViewDescription = dialogView.findViewById(R.id.textViewDescription);
        final TextView textViewNote = dialogView.findViewById(R.id.textViewNote1);
        final TextView textViewAdresse = dialogView.findViewById(R.id.textViewAdresse);
        final TextView textViewCuisinierCouriel = dialogView.findViewById(R.id.cuisinierCouriel);
        final TextView textViewCuisinierDescription = dialogView.findViewById(R.id.cuisinierDescription);

        textViewName.setText(repas.getRepasName());
        textViewPrice.setText(String.valueOf(repas.getPrice()));
        textViewCuisineType.setText(repas.getCuisineType());
        textViewIngredient.setText(repas.getRepasIngredients());
        textViewDescription.setText(repas.getRepasDescription());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Log.i("Panier a chang??", " ");
                    Log.i("current user", " " + data.toString());
                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(data.getKey())) {
                        client = data.getValue(Client.class);
                        repasArrayList = client.getPanier();
                        Log.i("PanierFragment", "la taille de repas est   " + repasArrayList.size());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        FirebaseDatabase.getInstance().getReference("Users").child(repas.getIdCuisinier()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals("moyenne")) {
                        textViewNote.setText(dataSnapshot.getValue(String.class));
                    }
                    if (dataSnapshot.getKey().equals("adresse")) {
                        textViewAdresse.setText(dataSnapshot.getValue(String.class));
                    }
                    if (dataSnapshot.getKey().equals("")) {
                        textViewCuisinierCouriel.setText(dataSnapshot.getValue(String.class));
                    }
                    if (dataSnapshot.getKey().equals("")) {
                        textViewCuisinierDescription.setText(dataSnapshot.getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dialogBuilder.setTitle("Ajouter au panier");
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
                Addtopanier(repas);

            }
        });

    }

    private void Addtopanier(Repas repas) {
        if (repasArrayList == null) {
            repasArrayList = new ArrayList<Repas>();
        }
        repasArrayList.add(repas);
        connectedClientpanier.setValue(repasArrayList);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("panier").setValue(repasArrayList);
        //Demande demande = new Demande(FirebaseAuth.getInstance().getCurrentUser().getUid(), repas);
        // demande.addDemandeDatabase();
    }
}