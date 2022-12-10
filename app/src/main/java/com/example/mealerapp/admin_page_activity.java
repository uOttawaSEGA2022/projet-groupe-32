package com.example.mealerapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin_page_activity extends AppCompatActivity {

    ListView plaintsListView;
    ArrayList<Plainte> plaintsArrayList;
    DatabaseReference myRef;
    private Activity context;
    Plainte plainte;
    String plainteId;
    String idCuisinierEnCause;
    String suspensionEndTime;
    ImageButton logOut;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page);
        mAuth=FirebaseAuth.getInstance();
        plaintsListView = (ListView) findViewById(R.id.listViewPlaints);
        logOut=findViewById(R.id.logout_Button);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(admin_page_activity.this, MainActivity.class));
            }
        });
        ///initialiser les textView findViewById()
        myRef = FirebaseDatabase.getInstance().getReference("Plaintes");
        plaintsArrayList = new ArrayList<>();
        plaintsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Plainte actualPlainte = (Plainte) plaintsListView.getItemAtPosition(i);
                plainte = actualPlainte;
                plainteId = actualPlainte.getidPlainte();
                // Log.i("admin_page_activity","CLE "+plainteId);
                idCuisinierEnCause = plainte.getIdCuisinier();
                //Log.i("admin_page_activity","la cle de plainte est "+plainte.tString());
                showRejectSuspendDialog(plainteId, actualPlainte);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plaintsArrayList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Plainte plainte = data.getValue(Plainte.class);
                    plainte.setIdPlainte(data.getKey());
                    // Log.i("admin_page_activity","la cle de plainte est "+plainte.tString());
                    plaintsArrayList.add(plainte);
                }
                plainteListe plaitesAdapter = new plainteListe(admin_page_activity.this, plaintsArrayList) ;
                plaintsListView.setAdapter(plaitesAdapter) ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void suspendre_Cook(String id, String SuspensionType) {
        Log.i("admin_page_activity", "la date est " + suspensionEndTime);
        Log.i("admin_page_activity", "le type est " + SuspensionType);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Plaintes").child(plainteId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String cook_email = String.valueOf(databaseReference.child("idCuisinier").getKey());
                String cook_email = idCuisinierEnCause;
                // Log.i("admin_page_activity","Ca prend comme email $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+cook_email);
                DatabaseReference data = FirebaseDatabase.getInstance().getReference("Users");

                data.addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Log.i("admin_page_activity", "la date est " + postSnapshot.getKey());
                            deletePlainte(plainteId);//remove plainte from database

                            //going through the database of users to find the right cooker by comparing their email adress

                            if (postSnapshot != null) {
                                String chemin = postSnapshot.getKey();
                                Log.i("admin_page_activity","le chemin est"+chemin);
                                Cooker cook = postSnapshot.getValue(Cooker.class);
                                Log.i("admin_page_activity","Ca aurait pu prendre comme email *****************************"+cook.getCourriel());
                                String ckk = "Cooker";
                                if (cook.getCourriel().equals(cook_email)) {
                                    data.child(chemin).child("suspension").setValue(SuspensionType);
                                    data.child(chemin).child("suspensionEndTime").setValue(suspensionEndTime);
                                    // data.child(chemin).setValue(new_Cook_Status); // replace the cook with his suspended status
                                    deletePlainte(plainteId);//remove plainte from databade
                                    break;
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showSuspensionTypeDialog(String id) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.suspension_type_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Durée de suspension");
        final Button buttonTemporaire = (Button) dialogView.findViewById(R.id.buttonTemporaire);
        final Button buttonIndefiniment = (Button) dialogView.findViewById(R.id.buttonIndefiniment);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonIndefiniment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                suspendre_Cook(id, "oui Indefinement");
                b.dismiss();
            }
        });
        buttonTemporaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendarDialog(id);
                b.dismiss();
            }
        });
    }

    private void showCalendarDialog(String id) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.calendar_to_get_suspension_period, null);
        dialogBuilder.setView(dialogView);
        final CalendarView calendarView = dialogView.findViewById(R.id.calendarView);
        final AlertDialog b = dialogBuilder.create();
        final String[] date = new String[1];
        b.show();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                date[0] = year + "/" + (month+1) + "/" + day;
                suspensionEndTime = date[0];
                suspendre_Cook(id, "oui Temporairement");
                b.dismiss();
            }
        });
    }



    private void showRejectSuspendDialog(String id, Plainte plainte) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.suspend_cook_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonReject = (Button) dialogView.findViewById(R.id.rejeterPlainteButton);
        final Button buttonSuspend = (Button) dialogView.findViewById(R.id.accepterPlainteButton);

        final TextView editTextPlaintTitle = (TextView) dialogView.findViewById(R.id.titrePlainte);
        final TextView editTextClientId = (TextView) dialogView.findViewById(R.id.idClient);
        final TextView editTextCookerId = (TextView) dialogView.findViewById(R.id.idCuisinier);
        final TextView editTextPlaintDescription = (TextView) dialogView.findViewById(R.id.descriptionPlainte);

        editTextPlaintTitle.setText(plainte.getTitrePlainte());
        editTextClientId.setText(plainte.getIdClient());
        editTextCookerId.setText(plainte.getIdCuisinier());
        editTextPlaintDescription.setText(plainte.getDescriptionPlainte());

        /////////////////////////////////////////////////////////////////////detail de la plaite a afficher
        dialogBuilder.setTitle("Accepter et suspendre ou rejetter");
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            //DIALOG SUSPENSIONTYPE RETUR UN STRING
            public void onClick(View view) {
                b.dismiss();
                showSuspensionTypeDialog(id);
            }
        });
        buttonReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePlainte(id);
                b.dismiss();
            }
        });
    }

    public void deletePlainte(String id) {
        /*
         * delete a complaint after it has been proccessed
         * */
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Plaintes").child(id);
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                database.removeValue();
                Toast.makeText(getApplicationContext(), "Plainte supprime de la base de donnee", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

}