package com.example.mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class changer_description_cooker_activity extends AppCompatActivity implements View.OnClickListener {
    TextView view_old_description, view_new_description;
    FirebaseAuth mAuth;
    Cooker loggedCooker ;
    String uid ;
    DatabaseReference reference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changer_description_cooker);
        view_old_description = (TextView) findViewById(R.id.old_description);
        view_new_description = (EditText) findViewById(R.id.new_description);
        mAuth=FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference("Users") ;
        uid=mAuth.getCurrentUser().getUid();
        if (!uid.isEmpty()) {
            getUserDescription();
        }
    }



    private void getUserDescription() {
        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Update note et nombre de repas vendus
                loggedCooker=snapshot.getValue(Cooker.class) ;
                view_old_description.setText(loggedCooker.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClick(View view) {
        if (view.getId() == R.id.mettre_a_jour) {
            String description = view_new_description.getText().toString().trim();
            if ( description.isEmpty()){
                Toast.makeText(changer_description_cooker_activity.this, "Votre reste la même", Toast.LENGTH_LONG).show();
            }
            else{
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("description").setValue(description);
                Toast.makeText(changer_description_cooker_activity.this, "Votre description a été changée", Toast.LENGTH_LONG).show();
            }
        }
    }
}