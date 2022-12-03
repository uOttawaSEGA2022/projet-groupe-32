package com.example.mealerapp;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.FractionRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.example.mealerapp.ui.Recherche_fragment;
import com.example.mealerapp.ui.gallery.PanierFragment;
import com.example.mealerapp.ui.home.OrderFragment;
import com.example.mealerapp.ui.slideshow.NotificationFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;

import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class client_page_activity extends AppCompatActivity {
//    ArrayList<Repas> repasArrayList;
//    ListView listViewRecherche;
//    private SearchView searchView;
    private TextView textViewUserConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();;
        setContentView(R.layout.client_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        textViewUserConnected = headerView.findViewById(R.id.txtFullName);
        createNotificationChannelRef();
        createNotificationChannelAcc();

        FirebaseDatabase.getInstance().getReference("Demandes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot){
                for (DataSnapshot data : snapshot.getChildren()) {
                    Demande demande = data.getValue(Demande.class) ;
                    String idClient=demande.getIdClient();

                    if(idClient.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        if ( demande.getDemandeTraitee().equals("true")){
                            NotificationCompat.Builder builderAcc ;
                            builderAcc = new NotificationCompat.Builder(client_page_activity.this, "Acc").setSmallIcon(R.drawable.ic_baseline_notifications_24).setContentTitle("Notification").setContentText("Votre demande a été acceptée").setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(client_page_activity.this);
                            notificationManager.notify(0, builderAcc.build());
                            Log.i("notification acceptée", "visible");
                        }
                        else if ( demande.getDemandeExists().equals("false")){
                            NotificationCompat.Builder builderRef ;
                            builderRef = new NotificationCompat.Builder(client_page_activity.this, "Ref").setSmallIcon(R.drawable.ic_baseline_notifications_24).setContentTitle("Notification").setContentText("Votre demande a été refusée").setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(client_page_activity.this);
                            notificationManager.notify(1, builderRef.build());
                            Log.i("notification rejettée", "visible");
                        }
                    }
                    //repasArrayList.add(repas);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = "", prenom = "";
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals("nom")) {
                        name = dataSnapshot.getValue(String.class);
                    }
                    if (dataSnapshot.getKey().equals("prenom")) {
                        prenom = dataSnapshot.getValue(String.class);
                    }
                }
                textViewUserConnected.setText(name + " " + prenom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        searchView = findViewById(R.id.searchRecherche);
//        searchView.clearFocus();
//        listViewRecherche = findViewById(R.id.listViewRecherche);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener((view) -> {
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int id = item.getItemId();
                item.setChecked(true);
                drawer.closeDrawer(GravityCompat.START);
                if (id == R.id.nav_menu) {
                    replaceFragments(new NotificationFragment());
                    // startActivity(new Intent(client_page_activity.this,. class));
                } if (id == R.id.nav_panier) {
                    //startActivity(new Intent(client_page_activity.this, panier_page_activity.class));
                    replaceFragments(new PanierFragment());
                } if (id == R.id.nav_orders) {
                    replaceFragments(new OrderFragment());
                    // startActivity(new Intent(client_page_activity.this,.class));
                } if (id == R.id.nav_log_out) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(client_page_activity.this, MainActivity.class));
                }else if(id==R.id.searchAmeal){
                    replaceFragments(new Recherche_fragment());
                }
                return true;
            }
        });

//        DatabaseReference databaseRepas = FirebaseDatabase.getInstance().getReference("Repas");
//        repasArrayList = new ArrayList<Repas>();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            boolean a = false;
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                searchView.clearFocus();
//                Log.i("client_page_activity", "la cle recherche esttttttttttttttttttttttt  " + query.toString());
//                repasArrayList = RechercherTypeCuisineAndNom(query);
//                RepasList repasAdapter = new RepasList(client_page_activity.this, repasArrayList);
//                if (!(repasArrayList == null)) {
//                    listViewRecherche.setAdapter(repasAdapter);
//                    a = true;
//                } else {
//                    Toast.makeText(client_page_activity.this, "Aucun produit ne correspond a votre recherche", Toast.LENGTH_LONG).show();
//                }
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (a == true) {
//                    repasArrayList = RechercherTypeCuisineAndNom(newText);
//                    RepasListRecherche repasAdapter = new RepasListRecherche(client_page_activity.this, repasArrayList);
//                    if (!(repasArrayList == null)) {
//                        listViewRecherche.setAdapter(repasAdapter);
//                    } else {
//                        Toast.makeText(client_page_activity.this, "Aucun produit ne correspond a votre recherche", Toast.LENGTH_LONG).show();
//                    }
//                }
//                return false;
//            }
//        });
//        listViewRecherche.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Repas repas = (Repas) listViewRecherche.getItemAtPosition(position);
//                showAddToPanierDialog(repas);
//                return true;
//            }
//        });
    }
    private void createNotificationChannelRef() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Ref","Notification", importance);
            channel.setDescription("description");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void createNotificationChannelAcc() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Acc","Notification", importance);
            channel.setDescription("description");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//
//        if (id==R.id.search){
//            Toast.makeText(client_page_activity.this, "Failed to login check your credentials", Toast.LENGTH_LONG).show();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    private void replaceFragments(Fragment fragment) {
        //remplacer les differents fragment
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentLayout, fragment);
        fragmentTransaction.commit();
    }


//    public ArrayList<Repas> RechercherTypeCuisineAndNom(String rechercheCle) {
//        //recupuer les resultats d'une recherche selon le typeDecuisine
//        ArrayList<Repas> repasList = new ArrayList<Repas>();
//        DatabaseReference dataRepas;
//        rechercheCle = rechercheCle.trim().toLowerCase();
//        dataRepas = FirebaseDatabase.getInstance().getReference("Repas");
//        String finalRechercheCle = rechercheCle;
//        // recupere tout les repas ayant ce mot dans leur type de cuisine
//        Log.i("client_page_activity", "la cle recherche est dans rechercheeeeeeeeeeeeeeee " + rechercheCle);
//        dataRepas.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot data : snapshot.getChildren()) {
//                    Repas repas = data.getValue(Repas.class);
//                    String repasCuisineType = repas.getCuisineType().toString().trim().toLowerCase();
//                    String[] repasType = repasCuisineType.split(" ");
//                    for (String s : repasType) {
//                        if (s.equals(finalRechercheCle)) {
//                            if (!(repasList == null)) {
//                                if (!repasList.contains(repas)) {
//                                    Log.i("client_page_activity", "la liste contient ttttttttttttttt " + finalRechercheCle);
//                                    repasList.add(repas);
//                                }
//                            } else {
//                                repasList.add(repas);
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
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
//                                    if (!(repasList == null)) {
//                                        if (!repasList.contains(repas)) {
//                                            Log.i("client_page_activity", "la liste contient 111111111111111111111111111111 " + finalRechercheCle);
//                                            repasList.add(repas);
//                                        }
//                                    } else {
//                                        repasList.add(repas);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//        //retirer les repas appartenant aux cuisinier qui sont suspendu du resultats de la recherche
//        if (!(repasList == null)) {
//            if (!repasList.isEmpty()) {
//                for (Repas repa : repasList) {
//                    final String[] suspension = new String[1];
//                    String repasCuisinierId = repa.getIdCuisinier();
//                    FirebaseDatabase.getInstance().getReference("Users").child(repasCuisinierId).child("suspension").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            suspension[0] = snapshot.getValue(String.class).trim();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                    if (suspension[0].equals("oui Temporairement") || suspension[0].equals("oui Indefinement")) {
//                        repasList.remove(repa);
//                    }
//                }
//            }
//        }
//        return repasList;
//    }
//
//
//    private void showAddToPanierDialog(Repas repas) {
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.add_to_panier_dialog, null);
//        dialogBuilder.setView(dialogView);
//
//        final Button buttonAdd = (Button) dialogView.findViewById(R.id.addtopanier);
//
//        final TextView textViewName = (TextView) dialogView.findViewById(R.id.textViewName11);
//        final TextView textViewPrice = (TextView) dialogView.findViewById(R.id.textViewPrice11);
//        final TextView textViewCuisineType = dialogView.findViewById(R.id.textViewCuisineType);
//        final TextView textViewIngredient = dialogView.findViewById(R.id.textViewIngredients);
//        final TextView textViewDescription = dialogView.findViewById(R.id.textViewDescription);
//        final TextView textViewNote = dialogView.findViewById(R.id.textViewNote1);
//        final TextView textViewAdresse = dialogView.findViewById(R.id.textViewAdresse);
//        final TextView textViewCuisinierCouriel = dialogView.findViewById(R.id.cuisinierCouriel);
//        final TextView textViewCuisinierDescription = dialogView.findViewById(R.id.cuisinierDescription);
//
//        textViewName.setText(repas.getRepasName());
//        textViewPrice.setText(String.valueOf(repas.getPrice()));
//        textViewCuisineType.setText(repas.getCuisineType());
//        textViewIngredient.setText(repas.getRepasIngredients());
//        textViewDescription.setText(repas.getRepasDescription());
//
//        FirebaseDatabase.getInstance().getReference("Users").child(repas.getIdCuisinier()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    if (dataSnapshot.getKey().equals("moyenne")) {
//                        textViewNote.setText(dataSnapshot.getValue(String.class));
//                    }
//                    if (dataSnapshot.getKey().equals("adresse")) {
//                        textViewAdresse.setText(dataSnapshot.getValue(String.class));
//                    }
//                    if (dataSnapshot.getKey().equals("")) {
//                        textViewCuisinierCouriel.setText(dataSnapshot.getValue(String.class));
//                    }
//                    if (dataSnapshot.getKey().equals("")) {
//                        textViewCuisinierDescription.setText(dataSnapshot.getValue(String.class));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        dialogBuilder.setTitle("Ajouter au panier");
//        final AlertDialog b = dialogBuilder.create();
//        b.show();
//        buttonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                b.dismiss();
//                Addtopanier(repas);
//            }
//        });
//
//    }
//
//    private void Addtopanier(Repas repas) {
//        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("courriel").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String courriel = snapshot.getValue(String.class);
//                Demande demande = new Demande(courriel, repas);
//                demande.addDemandeDatabase();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }


}