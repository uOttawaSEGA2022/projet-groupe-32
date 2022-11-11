package com.example.mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class item_activity extends AppCompatActivity {

    Button Accept ;
    Button Decline ;
    TextView editTextPlaintTitle, editTextClientId, editTextCookerId, editTextEmissionDate, editTextPlaintDescription ;
    DatabaseReference dbPlaint = FirebaseDatabase.getInstance().getReference().child("Plaintes") ;
    int ID ;
    Plainte plainte ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

        Intent importedData = getIntent() ;
        if (importedData !=null && importedData.hasExtra("numeroPlainte")) {
            ID=importedData.getIntExtra("numeroPlainte",-1) ;
        }


        Accept = (Button) findViewById(R.id.accepterPlainteButton) ;
        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(item_activity.this, AcceptationplainteActivity.class));
            }
        });

        Decline = (Button) findViewById(R.id.rejeterPlainteButton) ;
        Decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Plainte rejetée" + "/n" + "Redirection vers la boite de réception", Toast.LENGTH_LONG).show() ;
                startActivity(new Intent(item_activity.this, admin_page_activity.class));
            }
        });

        dbPlaint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for ( DataSnapshot data : snapshot.getChildren()) {
                    if (Integer.parseInt(data.child("plaintID").getValue().toString())==ID) {
                        plainte= data.getValue(Plainte.class) ;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editTextPlaintTitle = (TextView) findViewById(R.id.titrePlainte) ;
        editTextClientId = (TextView) findViewById(R.id.idClient) ;
        editTextCookerId = (TextView) findViewById(R.id.idCuisinier) ;
        editTextEmissionDate = (TextView) findViewById(R.id.datePlainte) ;
        editTextPlaintDescription = (TextView) findViewById(R.id.descriptionPlainte) ;

        editTextPlaintTitle.setText(plainte.getTitrePlainte());
        editTextClientId.setText(plainte.getIdClient());
        editTextCookerId.setText(plainte.getIdCuisinier());
        editTextEmissionDate.setText(plainte.getDatePlainte());
        editTextPlaintDescription.setText(plainte.getDescriptionPlainte());
    }
}