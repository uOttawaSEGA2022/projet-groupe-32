package com.example.mealerapp;

import static android.util.Patterns.EMAIL_ADDRESS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ajouter_Repas_Activity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    Button ajouterRepas;
    EditText editTextNom, editTextIngredients, editTextTypedeCuisine, editTextPrice, editTextDescription;
    FirebaseAuth mAuth;
    String idConnectedCooker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        idConnectedCooker = mAuth.getUid();
        Log.i("connected cooker is : ", idConnectedCooker);
        setContentView(R.layout.activity_ajouter_repas);
        databaseReference = FirebaseDatabase.getInstance().getReference("Repas");
        ajouterRepas = findViewById(R.id.AjouterRepas);

        editTextNom = findViewById(R.id.editTextRepasName);
        editTextPrice = findViewById(R.id.editTextRepasPrice);
        editTextDescription = findViewById(R.id.editTextRepasDescription);
        editTextIngredients = findViewById(R.id.editTextRepasIngredient);
        editTextTypedeCuisine = findViewById(R.id.editTextRepasTypeCuisine);


        ajouterRepas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AjouterUnRepas();
            }
        });
    }


    public void AjouterUnRepas() {
        /*
         * cette fonction ajoute un repas de la liste des repas du cuisinier*/
        String nomRepas = editTextNom.getText().toString().trim();
        String Ingredients = editTextIngredients.getText().toString().trim();
        String typeDeCuisine = editTextTypedeCuisine.getText().toString().trim();
        String price = editTextPrice.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        double price1 = 0;

        if (nomRepas.isEmpty()) {
            editTextNom.setError("Nomdu repas est requis");
            editTextNom.requestFocus();
            return;
        }
        if (Ingredients.isEmpty()) {
            editTextIngredients.setError("Entrez au moin un ingrédient");
            editTextIngredients.requestFocus();
            return;
        }
        if (typeDeCuisine.isEmpty()) {
            editTextTypedeCuisine.setError("Type de cuisine est requis");
            editTextTypedeCuisine.requestFocus();
            return;
        }

        if (price.isEmpty()) {
            editTextPrice.setError("Le prix est requis");
            editTextPrice.requestFocus();
            return;
        } else {
            try {
                // "price" is not a number
                price1 = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                editTextPrice.setError("Enter a number");
                editTextPrice.requestFocus();
                editTextPrice.getText().clear();
            }
        }


        if (description.isEmpty()) {
            editTextDescription.setError("La description est requise");
            editTextDescription.requestFocus();
            return;
        }
        Repas repas = new Repas(nomRepas, Ingredients, description, typeDeCuisine, price1, idConnectedCooker);
        editTextIngredients.getText().clear();
        editTextPrice.getText().clear();
        editTextNom.getText().clear();
        editTextTypedeCuisine.getText().clear();
        editTextDescription.getText().clear();


        if (!(repas == null)) {
            databaseReference.child(repas.getIdRepas()).setValue(repas);
            Toast.makeText(this, "repas ajouter", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Repas non ajouter Reessayer", Toast.LENGTH_LONG).show();
        }

    }

//    public void retirerUnRepas(Repas repas) {
//        /*
//         * cette fonction retire un repas de la liste des repas du cuisinier*/
//        if (!(repas == null)) {
//
//            databaseReference.child(repas.getId()).
//                    addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            databaseReference.removeValue();
//                            Toast.makeText(ajouter_Repas_Activity.this, "repas supprimer", Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//        } else {
//            Toast.makeText(this, "Repas non supprimer Reessayer", Toast.LENGTH_LONG).show();
//        }
//    }
}