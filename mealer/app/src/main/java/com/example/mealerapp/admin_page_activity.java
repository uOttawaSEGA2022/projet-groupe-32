package com.example.mealerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class admin_page_activity extends AppCompatActivity {
    ListView plaintsListView;
    ArrayList<String> plaintsArrayList = new ArrayList<>();
    DatabaseReference myRef;
    Plainte plainte;
    String suspensionEndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page);
        myRef = FirebaseDatabase.getInstance().getReference("Plaintes");
        ArrayAdapter<String> plaintsArrayAdapter = new ArrayAdapter<String>(admin_page_activity.this, android.R.layout.simple_list_item_1, plaintsArrayList);
        plaintsListView = findViewById(R.id.listViewPlaints);
        plaintsListView.setAdapter(plaintsArrayAdapter);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Plainte plainte = snapshot.getValue(Plainte.class);
                    if (plainte!= null) {
                        plaintsArrayList.add(plainte.getPlainteDescription());
                    }
                }
                plaintsArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        plaintsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String actualPlainte = plaintsArrayList.get(i);
                showRejectSuspendDialog(plainte);
                return true;
            }
        });
    }


    private void suspendre_Cook(Plainte actual_plainte,String SuspensionType) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Plaintes").child(actual_plainte.getId());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Plainte plaint = snapshot.getValue(Plainte.class);
                String cook_email = plaint.getIdCuisinier();
                DatabaseReference data = FirebaseDatabase.getInstance().getReference("Users");
                data.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            //going through the databes of users to find the right cooker by comparing their email adress
                            if (postSnapshot != null) {
                                Cooker cook = postSnapshot.getValue(Cooker.class);
                                if (cook.getCourriel().equals(cook_email)) {
                                    Cooker new_Cook_Status;
                                    new_Cook_Status = new Cooker(cook.getPrenom(),
                                            cook.getNom(),
                                            cook.getCourriel(),
                                            cook.getMotDePasse(),
                                            cook.getAdresse(),
                                            cook.getDescription(),
                                            showCalendarDialog()[0],
                                            suspensionEndTime,
                                            cook.getList());
                                    data.setValue(new_Cook_Status); // replace the cook with his suspended status
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                databaseReference.removeValue();//remove plainte from databade

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void showSuspensionTypeDialog(Plainte plainte1){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.suspension_type_dialog, null);
        dialogBuilder.setView(dialogView);
        final Button buttonTemporaire = (Button) dialogView.findViewById(R.id.buttonTemporaire);
        final Button buttonIndefiniment = (Button) dialogView.findViewById(R.id.buttonIndefiniment);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonIndefiniment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                suspendre_Cook(plainte1,"oui Indefinement");
                b.dismiss();
            }
        });
        buttonTemporaire.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String suspens_Date[]=showCalendarDialog();
                suspensionEndTime=suspens_Date[0];
                suspendre_Cook(plainte1,"oui Temporairement");
                b.dismiss();
            }
        });
    }

    private String[] showCalendarDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.calendar_to_get_suspension_period, null);
        dialogBuilder.setView(dialogView);
        final CalendarView calendarView = dialogView.findViewById(R.id.calendarView);
        final EditText editTextDate = dialogView.findViewById(R.id.editTextDate);
        final AlertDialog b = dialogBuilder.create();
        final String[] date = {" "};
        b.show();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                 date[0] = (month+1)+"/"+month+"/"+day;
                editTextDate.setText(date[0]);
                b.dismiss();
            }
        });
    return date;
    }



    private void showRejectSuspendDialog(Plainte plainte) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.suspend_cook_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonReject = (Button) dialogView.findViewById(R.id.buttonRejectPlaite);
        final Button buttonSuspend = (Button) dialogView.findViewById(R.id.buttonSuspendCook);

        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            //DIALOG SUSPENSIONTYPE RETUR UN STRING
            public void onClick(View view) {
                b.dismiss();
                showSuspensionTypeDialog(plainte);

                }
        });
        buttonReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePlainte(plainte.getId());
               b.dismiss();
            }
        });
    }



    public void deletePlainte(String id){
            DatabaseReference database=FirebaseDatabase.getInstance().getReference("Plaintes").child(id);
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                database.removeValue();
                Toast.makeText(getApplicationContext(),"Plainte supprime de la base de donnee", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }
}
