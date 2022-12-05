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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private TextView textViewUserConnected;
    //MIse à jour
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



        FirebaseDatabase.getInstance().getReference("Demandes").addValueEventListener(new ValueEventListener() {
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

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
                    replaceFragments(new PanierFragment());
                } if (id == R.id.nav_orders) {
                    Intent orderIntent = new Intent(client_page_activity.this,orderListe.class);
                    startActivity(orderIntent);
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


    private void replaceFragments(Fragment fragment) {
        //remplacer les differents fragment
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentLayout, fragment);
        fragmentTransaction.commit();
    }


}