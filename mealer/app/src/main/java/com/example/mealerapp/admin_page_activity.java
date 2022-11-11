package com.example.mealerapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
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
    private Activity context ;
    String suspensionEndTime;
    Plainte plainte;
    String plainteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // String Id=FirebaseDatabase.getInstance().getReference("Plaintes").push().getKey();

        //String Id1=FirebaseDatabase.getInstance().getReference("Plaintes").push().getKey();
        //Plainte plainte1 = new Plainte(Id,"Indigeste", "amin_nna@gmail.com", "2@gmail.com","03/11/2022","oui");
       // Plainte plainte2 = new Plainte(Id1,"Moisissure", "aichalfakir@gmail.com", "aguigma@gmail.com","03/11/2022","Il y'avait de la moisissure dans le repas que j'ai reçu");
       // FirebaseDatabase.getInstance().getReference("Plaintes").child(Id).setValue(plainte1);
       // FirebaseDatabase.getInstance().getReference("Plaintes").child(Id1).setValue(plainte2);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page);

        plaintsListView = (ListView) findViewById(R.id.listViewPlaints) ;
        plaintsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Plainte actualPlainte = (Plainte) plaintsListView.getItemAtPosition(i);
                plainte=actualPlainte;
                plainteId=actualPlainte.getId().trim();
                Log.i("admin_page_activity","CLE "+plainteId);
                Log.i("admin_page_activity","la cle de plainte est "+plainte.tString());
                showRejectSuspendDialog(actualPlainte.getId(),actualPlainte);
                return true;
            }
        });
//        plaintsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                startActivity(new Intent(context, item_activity.class));
//            }
//        });

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
    private void suspendre_Cook(String id,String SuspensionType) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Plaintes").child(plainteId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cook_email = plainte.getIdCuisinier();
                DatabaseReference data = FirebaseDatabase.getInstance().getReference("Users");
                data.addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            //going through the database of users to find the right cooker by comparing their email adress
                            if (postSnapshot != null) {
                                //Cooker cook = postSnapshot.getValue(Cooker.class);
                                if (postSnapshot.getValue(Cooker.class).getCourriel().equals(cook_email)) {
                                    Cooker new_Cook_Status;
                                    new_Cook_Status = new Cooker(
                                            postSnapshot.getValue(Cooker.class).getPrenom(),
                                            postSnapshot.getValue(Cooker.class).getNom(),
                                            postSnapshot.getValue(Cooker.class).getCourriel(),
                                            postSnapshot.getValue(Cooker.class).getMotDePasse(),
                                            "Cooker",
                                            postSnapshot.getValue(Cooker.class).getAdresse(),
                                            postSnapshot.getValue(Cooker.class).getDescription(),
                                            showCalendarDialog()[0],
                                            suspensionEndTime,
                                            postSnapshot.getValue(Cooker.class).getList());
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
            public void onCancelled(@NonNull DatabaseError error){

            }

        });
    }

    private void showSuspensionTypeDialog(String id){
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
                suspendre_Cook(id,"oui Indefinement");
                b.dismiss();
            }
        });
        buttonTemporaire.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String suspens_Date[]=showCalendarDialog();
                suspensionEndTime=suspens_Date[0];
                suspendre_Cook(id,"oui Temporairement");
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



    private void showRejectSuspendDialog(String id,Plainte plainte) {

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