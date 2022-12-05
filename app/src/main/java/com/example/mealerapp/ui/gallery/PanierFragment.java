package com.example.mealerapp.ui.gallery;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mealerapp.Client;
import com.example.mealerapp.Demande;
import com.example.mealerapp.DemandeListe;
import com.example.mealerapp.R;
import com.example.mealerapp.Repas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PanierFragment extends Fragment {
    ArrayList<Repas> repasArrayList;
    ListView listViewPanier;
    DatabaseReference myRef;
    Client client;
    String uid;
    DemandeListe repasAdapter;
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
        listViewPanier = (ListView) view.findViewById(R.id.listViewPanierp);
        repasArrayList=new ArrayList<Repas>();

        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    client=task.getResult().getValue(Client.class);
                    repasArrayList=client.getPanier();
                    Log.i("PanierFragment create","la taille de repas est   "+repasArrayList.size());
                    repasAdapter = new DemandeListe(getActivity(), repasArrayList);
                    if(repasArrayList!=null){
                        Log.i("create not null","la taille de demandeArralist est   "+repasArrayList.size());
                        listViewPanier.setAdapter(repasAdapter);
                    }
                    Log.i("PanierFragment","la taille de repas est   "+repasArrayList.size());
                }
            }
        });
        listViewPanier.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Repas repas = repasArrayList.get(position);
                showHnadleDemande(repas);
                return true;
            }
        });

    }
    public void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Log.i("current user"," "+FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Log.i("current user"," "+data.toString());
                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(data.getKey())) {
                        client = data.getValue(Client.class);
                        repasArrayList=client.getPanier();
                        if (repasAdapter==null ){
                            Log.i("respas adapter","est null "+repasArrayList.size());
                        }
                        if (repasArrayList==null ){
                            Log.i("respas list","est null "+repasArrayList.size());
                        }

                        Log.i("PanierFragment change","la taille de repas est   "+repasArrayList.size());
                    }
                }
                repasAdapter.notifyDataSetChanged();




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
        repasArrayList.remove(repas);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("panier").setValue(repasArrayList);
        replaceFragments(new PanierFragment());
    }


    private void deleteDemande(Repas repas) {
        repasArrayList.remove(repas);
        if (repasArrayList==null){
            repasArrayList=new ArrayList<Repas>();
        }

        //client.setPanier(repasArrayList);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("panier").setValue(repasArrayList);
        replaceFragments(new PanierFragment());
    }
    private void replaceFragments(Fragment fragment) {
        //remplacer les differents fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentLayout, fragment);
        fragmentTransaction.commit();
    }



}