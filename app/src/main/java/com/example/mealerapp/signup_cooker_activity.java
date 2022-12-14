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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signup_cooker_activity extends AppCompatActivity implements View.OnClickListener{
    private Button SignUp;
    public EditText editTextPrenom, editTextNom, editTextAdresseCourriel, editTextMotDePasse, editTextAdresse, editTextConfirm;
    private FirebaseAuth mAuth;
    //@SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_cooker);
        mAuth = FirebaseAuth.getInstance();

        SignUp = findViewById(R.id.signUpBttn1);
        SignUp.setOnClickListener(this);

        editTextPrenom = findViewById(R.id.Prenom1);
        editTextNom = findViewById(R.id.Nom1);
        editTextAdresseCourriel = findViewById(R.id.AdresseCourriel1);
        editTextMotDePasse = findViewById(R.id.SignInPassword);
        editTextConfirm = findViewById(R.id.confirm_password1);
        editTextAdresse = findViewById(R.id.new_description);

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
            editTextAdresseCourriel.getText().clear();
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
            editTextMotDePasse.getText().clear();
            return;
        }

        if (MotDePasseConfirm1.isEmpty()) {
            editTextConfirm.setError(" Confirmer votre mot de passe");
            editTextConfirm.requestFocus();

            return;
        } else {
            if (!MotDePasse1.equals(MotDePasseConfirm1)) {
                editTextConfirm.setError(" Le mot de passe ne correspond pas ?? celui entr?? plus haut");
                editTextConfirm.requestFocus();
                editTextConfirm.getText().clear();
                return;
            }
        }
        if (Adresse1.isEmpty()) {
            editTextAdresse.setError("Votre adresse est requise");
            editTextAdresse.requestFocus();
            return;
        }

        //Administrator user=new Administrator(Prenom1,Nom1,adressecourriel1,MotDePasse1,Adresse1);
       Cooker user=new Cooker(Prenom1,Nom1,adressecourriel1,MotDePasse1,"Administrator",Adresse1);
        mAuth.createUserWithEmailAndPassword(user.getCourriel() , user.getMotDePasse()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
         @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("succes", "is connnected");
                    //user.setUserType("Administrator");
                    //cooker.setUserType("Cooker");
                    //Cooker Itulisateur =cooker; //On lui affecte l'objet plus haut
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            //.setValue(cooker).addOnCompleteListener(new OnCompleteListener<Void>() {
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(signup_cooker_activity.this,"user registered success",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(signup_cooker_activity.this, signup_cooker_suite_activity.class));
                                    }else{
                                        Toast.makeText(signup_cooker_activity.this,"Failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(signup_cooker_activity.this,"Faileddddd!!!",Toast.LENGTH_LONG).show();
                }
            }
        });




    }
}