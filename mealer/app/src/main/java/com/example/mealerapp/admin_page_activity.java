package com.example.mealerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class admin_page_activity extends AppCompatActivity {

    ListView plaintsListView ;
    ArrayList<Plainte> plaintsArrayList ;
    DatabaseReference myRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page);

        plaintsListView = (ListView) findViewById(R.id.listViewPlaints) ;
        plaintsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Plainte intentedPlaint = plaintsArrayList.get(i) ;
                startActivity(new Intent(admin_page_activity.this, item_activity.class).putExtra("numeroPlainte", intentedPlaint.getID()));
            }
        });

        myRef = FirebaseDatabase.getInstance().getReference("Plaintes") ;

        plaintsArrayList = new ArrayList<>() ;
    }

    @Override
    protected void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {

                    Plainte plainte = data.getValue(Plainte.class) ;
                    plaintsArrayList.add(plainte) ; }

                ArrayAdapter<Plainte> plaintsAdapter = new ArrayAdapter<Plainte>(admin_page_activity.this, android.R.layout.simple_list_item_1, plaintsArrayList) ;
                plaintsListView.setAdapter(plaintsAdapter) ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}