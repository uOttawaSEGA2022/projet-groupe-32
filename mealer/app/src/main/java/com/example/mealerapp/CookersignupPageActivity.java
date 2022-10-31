package com.example.mealerapp;

import static android.util.Patterns.EMAIL_ADDRESS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CookersignupPageActivity extends AppCompatActivity implements View.OnClickListener{
    private Button SignUp;
    public EditText editTextPrenom, editTextNom, editTextAdresseCourriel, editTextMotDePasse, editTextAdresse, editTextConfirm;
    private FirebaseAuth mAuth;

    //@SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cookersignup_page);
        mAuth = FirebaseAuth.getInstance();
        SignUp = findViewById(R.id.signUpBttn1);
        SignUp.setOnClickListener(this);

        editTextPrenom = findViewById(R.id.Prenom1);
        editTextNom = findViewById(R.id.Nom1);
        editTextAdresseCourriel = findViewById(R.id.AdresseCourriel1);
        editTextMotDePasse = findViewById(R.id.SignInPassword);
        editTextConfirm = findViewById(R.id.confirm_password1);
        editTextAdresse = findViewById(R.id.address);

    }
@Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpBttn1:
                resisterUser();
                break;
        }

    }

    private void resisterUser() {

        String Prenom1 = editTextPrenom.getText().toString().trim();
        String Nom1 = editTextNom.getText().toString().trim();
        String adressecourriel1 = editTextAdresseCourriel.getText().toString().trim();
        String MotDePasse1 = editTextMotDePasse.getText().toString().trim();
        String Adresse1 = editTextAdresse.getText().toString().trim();
        String MotDePasseConfirm1 = editTextConfirm.getText().toString().trim();

        if (Prenom1.isEmpty()) {
            editTextPrenom.setError("Prenom est requis");
            editTextPrenom.requestFocus();
            return;
        }
        if (Nom1.isEmpty()) {
            editTextNom.setError(" Nom est requis");
            editTextNom.requestFocus();
            return;
        }
        if (adressecourriel1.isEmpty()) {
            editTextAdresseCourriel.setError(" Adresse couuriel est requis");
            editTextAdresseCourriel.requestFocus();
            return;
        }
        if (!EMAIL_ADDRESS.matcher(adressecourriel1).matches()) {
            editTextAdresseCourriel.setError(" Adresse couuriel non valide");
            editTextAdresseCourriel.requestFocus();
            return;
        }
        if (MotDePasse1.isEmpty()) {
            editTextMotDePasse.setError(" Mot de passse est requis");
            editTextMotDePasse.requestFocus();
          return;
        }

        if (MotDePasse1.length() < 8) {
            editTextMotDePasse.setError(" Mot de passse a une longeur de 8 characteres");
            editTextMotDePasse.requestFocus();
            return;
        }

        if (MotDePasseConfirm1.isEmpty()) {
            editTextConfirm.setError(" Confirmer votre mot de passe");
            editTextConfirm.requestFocus();

            return;
        } else {
            if (!MotDePasse1.equals(MotDePasseConfirm1)) {
                editTextConfirm.setError(" Champs ne matche pas Mot de passe");
                editTextConfirm.requestFocus();
                return;
            }
        }
        if (Adresse1.isEmpty()) {
            editTextAdresse.setError(" Une adresse est requis");
            editTextAdresse.requestFocus();
            return;
        }

        User user=new Cooker(Prenom1,Nom1,adressecourriel1,MotDePasse1,Adresse1,"Cooker","je suis cuisinier");
        mAuth.createUserWithEmailAndPassword(user.getCourriel() , user.getMotDePasse()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User Itulisateur =new Cooker();
                    Itulisateur.setPrenom(Prenom1);
                    Itulisateur.setAdresse(Adresse1);
                    Itulisateur.setCourriel(adressecourriel1);
                    Itulisateur.setUserType("Cooker");
                    Itulisateur.setNom(Nom1);
                    Itulisateur.setMotDePasse(MotDePasse1);
                    FirebaseDatabase.getInstance().getReference("Cookers").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(Itulisateur).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(CookersignupPageActivity.this,"user registered success",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(CookersignupPageActivity.this, WelcomeCookActivity.class));
                                    }else{
                                        Toast.makeText(CookersignupPageActivity.this,"Failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(CookersignupPageActivity.this,"Faileddddd!!!",Toast.LENGTH_LONG).show();
                }
            }
        });




    }
}