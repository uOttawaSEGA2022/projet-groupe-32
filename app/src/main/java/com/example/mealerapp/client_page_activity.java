package com.example.mealerapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class client_page_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ArrayList <Repas>repasArrayList;
    ListView listViewResearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_page);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener((view)->{
            Snackbar.make(view,"Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action",null).show();
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        listViewResearch=findViewById(R.id.listViewResearch);
        DatabaseReference databaseRepas = FirebaseDatabase.getInstance().getReference("Repas");
        repasArrayList = new ArrayList<Repas>();

        listViewResearch.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Repas repas = repasArrayList.get(i);
                Toast.makeText(client_page_activity.this, "element de la liste de recherche", Toast.LENGTH_LONG).show();
                return true;
            }
        });


    }



    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id==R.id.search){
            Toast.makeText(client_page_activity.this, "Failed to login check your credentials", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){

        int id = item.getItemId();
        if(id==R.id.nav_menu){
        }else if (id==R.id.nav_panier){
        }else if (id==R.id.nav_orders){
        }else if (id==R.id.nav_log_out){
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.home,menu);
        MenuItem searchViewItem = findViewById(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        Log.i("client_page_activity","la cle recherche est  " +" je suis lance");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Log.i("client_page_activity","la cle recherche est  " +query.toString());
//                searchView.clearFocus();
//                repasArrayList=RechercherTypeCuisineAndNom(query);
//                RepasList repasAdapter = new RepasList(client_page_activity.this, repasArrayList);
//                listViewResearch.setAdapter(repasAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("client_page_activity","la cle recherche est  " +newText.toString());
                searchView.clearFocus();
                repasArrayList=RechercherTypeCuisineAndNom(newText);
                RepasList repasAdapter = new RepasList(client_page_activity.this, repasArrayList);
                listViewResearch.setAdapter(repasAdapter);

                return false;
            }
        });
        return true;
    }


    public ArrayList<Repas> RechercherTypeCuisineAndNom(String rechercheCle) {
        //recupuer les resultats d'une recherche selon le typeDecuisine
        ArrayList<Repas> repasList = null;
        DatabaseReference dataRepas;
        rechercheCle = rechercheCle.trim().toLowerCase();
        dataRepas = FirebaseDatabase.getInstance().getReference("Repas");
        String finalRechercheCle = rechercheCle;
        // recupere tout les repas ayant ce mot dans leur type de cuisine
        dataRepas.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot data : snapshot.getChildren()) {
                            Repas repas = data.getValue(Repas.class);
                            String repasCuisineType = repas.getCuisineType().toString().trim().toLowerCase();
                            String[] repasType = repasCuisineType.split(" ");
                            for (String s : repasType) {
                                if (s.equals(finalRechercheCle)) {
                                    repasList.add(repas);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
        dataRepas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Repas repas = data.getValue(Repas.class);
                    String repasNom = repas.getRepasName().toString().trim().toLowerCase();
                    String[] repasNom2 = repasNom.split(" ");
                    String[] rechercheCler = finalRechercheCle.split(" ");
                    for (String str : rechercheCler) {
                        if (str.length() > 2) {
                            for (String s : repasNom2) {
                                if (s.equals(str)) {
                                    if (!repasList.contains(repas)){
                                        repasList.add(repas);
                                    }
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
        //retirer les repas appartenant aux cuisinier qui sont suspendu du resultats de la recherche
        if (!repasList.isEmpty()) {
            for (Repas repa : repasList) {
                final String[] suspension = new String[1];
                String repasCuisinierId = repa.getIdCuisinier();
                FirebaseDatabase.getInstance().getReference("Users").
                        child(repasCuisinierId).child("suspension").
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                suspension[0] = snapshot.getValue(String.class).trim();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                if (suspension[0].equals("oui Temporairement") || suspension[0].equals("oui Indefinement")) {
                    repasList.remove(repa);
                }
            }
        }
        return repasList;
    }


//    public ArrayList<Repas> RechercherRepasParNom(String rechercheCle) {
//        //recupere la liste des repas dont le nom correspond aux mots de recherche
//        ArrayList<Repas> repasList = null;
//        DatabaseReference dataRepas;
//        rechercheCle = rechercheCle.toLowerCase();
//        dataRepas = FirebaseDatabase.getInstance().getReference("Repas");
//        String finalRechercheCle = rechercheCle;
//        dataRepas.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot data : snapshot.getChildren()) {
//                    Repas repas = data.getValue(Repas.class);
//                    String repasNom = repas.getRepasName().toString().trim().toLowerCase();
//                    String[] repasNom2 = repasNom.split(" ");
//                    String[] rechercheCler = finalRechercheCle.split(" ");
//                    for (String str : rechercheCler) {
//                        if (str.length() > 2) {
//                            for (String s : repasNom2) {
//                                if (s.equals(str)) {
//                                    if (!repasList.contains(repas)){
//                                        repasList.add(repas);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        if (!repasList.isEmpty()) {
//            for (Repas repa : repasList) {
//                final String[] suspension = new String[1];
//                String repasCuisinierId = repa.getIdCuisinier();
//                FirebaseDatabase.getInstance().getReference("Users").
//                        child(repasCuisinierId).child("suspension").
//                        addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                suspension[0] = snapshot.getValue(String.class).trim();
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                if (suspension[0].equals("oui Temporairement") || suspension[0].equals("oui Indefinement")) {
//                    repasList.remove(repa);
//                }
//            }
//        }
//        return repasList;
//    }
}