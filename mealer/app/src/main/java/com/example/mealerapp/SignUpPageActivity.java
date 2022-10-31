package com.example.mealerapp;
import static android.util.Patterns.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignUpPageActivity extends AppCompatActivity implements View.OnClickListener {

    public Button SignUp;
    protected EditText editTextPrenom, editTextNom, editTextAdresseCourriel, editTextMotDePasse, editTextCVV, editTextAdresse, editTextConfirm, editTextInformationsCarteCredit;
    //private ProgressBar progressBar;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);
        SignUp = findViewById(R.id.signupButton);
        SignUp.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        editTextPrenom = findViewById(R.id.Prenom);
        editTextNom = findViewById(R.id.Nom);
        editTextAdresseCourriel = findViewById(R.id.AdresseCourriel);
        editTextMotDePasse = findViewById(R.id.MotDePasse);
        editTextConfirm = findViewById(R.id.confirm_password1);
        editTextAdresse = findViewById(R.id.Adresse);
        editTextInformationsCarteCredit = findViewById(R.id.InformationsCarteCrédit);
        editTextCVV = findViewById(R.id.cvv);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signupButton:
                resisterUser();
                break;
        }
//
    }
//    public void createAccountOnFirebase(User user,String email,String password){
//        email=user.getCourriel();
//        password=user.getMotDePasse();
//        String type=user.getUserType();
//        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                Client Utili;
//                if(type.equals("Client")){
//                    Utili=new Client(user.getPrenom(),user.getNom(),user.getCourriel(),user.getMotDePasse(),user.getAdresse(),user.getUserType(),"");
//                }
//
//                if(task.isSuccessful()){
//                    FirebaseDatabase.getInstance().getReference(type).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                                    Toast.makeText(SignUpPageActivity.this, "L'utilisateur  enregistrer", Toast.LENGTH_LONG).show();
//                                //rediriger vers login layout
//                                                    if(type.equals("Client")){
//                                                        startActivity(new Intent(SignUpPageActivity.this, WelcomeClientActivity.class));
//                                                    }else if(type.equals("Cooker")){
//                                                        startActivity(new Intent(SignUpPageActivity.this, WelcomeCookActivity.class));
//                                                    }
//
//
//
//                                                } else {
//                                                    Toast.makeText(SignUpPageActivity.this, "L'utilisateur  non enregistrer essaiyer encore", Toast.LENGTH_LONG).show();
//                                                    //progresseBar.setVisibility(View.GONE);
//
//                                                }
//
//                        }
//                    });
//                } else {
//                Toast.makeText(SignUpPageActivity.this, "L'utilisateur  non enregistrer", Toast.LENGTH_LONG).show();
//                //progresseBar.setVisibility(View.GONE);
//
//            }
//            }
//        });
//    }
//    public void createAccountOnFirebase(User user,String email,String password){
//        mAuth.createUserWithEmailAndPassword(user.getCourriel(), user.getMotDePasse()).addOnCompleteListener(
//                new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
////                            if (user.UserType.equals("Client")) {
//                                FirebaseDatabase.getInstance().getReference("Cookers")
//                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                if (task.isSuccessful()) {
//                                                    Toast.makeText(SignUpPageActivity.this, "L'utilisateur  enregistrer", Toast.LENGTH_LONG).show();
//
//                                                    //rediriger vers login layout
//                                                    startActivity(new Intent(SignUpPageActivity.this, WelcomeClientActivity.class));
//                                                } else {
//                                                    Toast.makeText(SignUpPageActivity.this, "L'utilisateur  non enregistrer essaiyer encore", Toast.LENGTH_LONG).show();
//                                                    //progresseBar.setVisibility(View.GONE);
//
//                                                }
//                                            }
//
//                                        });
////                            }
////                            else if (user.UserType.equals("Cooker")) {
////                                FirebaseDatabase.getInstance().getReference("Cookers")
////                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
////
////                                            @Override
////                                            public void onComplete(@NonNull Task<Void> task) {
////                                                if (task.isSuccessful()) {
////                                                    Toast.makeText(getApplicationContext(), "L'utilisateur  enregistrer", Toast.LENGTH_LONG).show();
////                                                    //progresseBar.setVisibility(View.GONE);
////                                                    //rediriger vers login layout
////                                                    startActivity(new Intent(getApplicationContext(), WelcomeCookActivity.class));
////                                                } else {
////                                                    Toast.makeText(getApplicationContext(), "L'utilisateur  non enregistrer essaiyer encore", Toast.LENGTH_LONG).show();
////                                                    //progresseBar.setVisibility(View.GONE);
////
////                                                }
////                                            }
////
////                                        });
////                            }
//                        } else {
//                            Toast.makeText(getApplicationContext(), "L'utilisateur  non enregistrer essaiyer encore", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                        });
    //   }


    private void resisterUser() {

        String Prenom2 = editTextPrenom.getText().toString().trim();
        String Nom2 = editTextNom.getText().toString().trim();
        String adressecourriel2 = editTextAdresseCourriel.getText().toString().trim();
        String MotDePasse2 = editTextMotDePasse.getText().toString().trim();
        String Adresse2 = editTextAdresse.getText().toString().trim();
        String InformationsCarteCredit2 = editTextInformationsCarteCredit.getText().toString().trim();
        String MotDePasseConfirm2 = editTextConfirm.getText().toString().trim();
        String cvv2 = editTextCVV.getText().toString().trim();
//Getting TextViews text
        if (Prenom2.isEmpty()) {
            editTextPrenom.setError("Prenom est requis");
            editTextPrenom.requestFocus();
            return;
        }
        if (Nom2.isEmpty()) {
            editTextNom.setError(" Nom est requis");
            editTextNom.requestFocus();
            return;
        }
        if (adressecourriel2.isEmpty()) {
            editTextAdresseCourriel.setError(" Adresse couuriel est requis");
            editTextAdresseCourriel.requestFocus();
            return;
        }
        if (!EMAIL_ADDRESS.matcher(adressecourriel2).matches()) {
            editTextAdresseCourriel.setError(" Adresse couuriel non valide");
            editTextAdresseCourriel.requestFocus();
            return;
        }
        if (MotDePasse2.isEmpty()) {
            editTextMotDePasse.setError(" Mot de passse est requis");
            editTextMotDePasse.requestFocus();
            return;
        }

        if (MotDePasse2.length() < 4) {
            editTextMotDePasse.setError(" Mot de passse a une longeur de 8 characteres");
            editTextMotDePasse.requestFocus();
            return;
        }

        if (MotDePasseConfirm2.isEmpty()) {
            editTextConfirm.setError(" Confirmer votre mot de passe");
            editTextConfirm.requestFocus();
            return;
        } else {
            if (!MotDePasse2.equals(MotDePasseConfirm2)) {
                editTextConfirm.setError(" Champs ne matche pas Mot de passe");
                editTextConfirm.requestFocus();
                return;
            }
        }
        if (Adresse2.isEmpty()) {
            editTextAdresse.setError(" Une adresse est requis");
            editTextAdresse.requestFocus();
            return;
        }


        if (InformationsCarteCredit2.isEmpty()) {
            editTextInformationsCarteCredit.setError(" InformationsCarteCrédit est requis");
            editTextInformationsCarteCredit.requestFocus();
            return;
        }
        if (cvv2.isEmpty()) {
            editTextCVV.setError("Prenom est requis");
            editTextCVV.requestFocus();
            return;
        }
//        if(getApplicationContext()==SignUpPageActivity.this){
//            user=new Client(Prenom,Nom,adressecourriel,MotDePasse,Adresse,"Client",InformationsCarteCredit);
//        }else{
//            user=new Cooker(Prenom,Nom,adressecourriel,MotDePasse,Adresse,"Cooker","Moi je suis cuisinier");
//        }
//        if(user.UserType.equals("Client")){
//
//        }else if (user.UserType.equals("Client")){
//
//        }
//        createAccountOnFirebase(user,user.getCourriel(),user.getMotDePasse());
//Sending user to firebase
//        Client user = new Client(Prenom2, Nom2, adressecourriel2, MotDePasse2, Adresse2, "Client",InformationsCarteCredit2);
//        mAuth.createUserWithEmailAndPassword(user.getCourriel(), user.getMotDePasse()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Client Itulisateur = new Client();
//                    Itulisateur.setPrenom(Prenom2);
//                    Itulisateur.setAdresse(Adresse2);
//                    Itulisateur.setCourriel(adressecourriel2);
//                    Itulisateur.setUserType("Client");
//                    Itulisateur.setNom(Nom2);
//                    Itulisateur.setMotDePasse(MotDePasse2);
//                    Itulisateur.setInformationsCarteCredit(InformationsCarteCredit2);
//
//
//                    FirebaseDatabase.getInstance().getReference("Client").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                            .setValue(Itulisateur).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(SignUpPageActivity.this, "user registered success", Toast.LENGTH_LONG).show();
//                                        startActivity(new Intent(SignUpPageActivity.this, WelcomeClientActivity.class));
//                                    } else {
//                                        Toast.makeText(SignUpPageActivity.this, "Failed", Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                            });
//                } else {
//                    Toast.makeText(SignUpPageActivity.this, "Faileddddd!!!", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        User user=new Client(Prenom2,Nom2,adressecourriel2,MotDePasse2,Adresse2,"Client",InformationsCarteCredit2);
        mAuth.createUserWithEmailAndPassword(user.getCourriel(),user.getMotDePasse()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Client Itulisateur = new Client();
                    Itulisateur.setPrenom(Prenom2);
                    Itulisateur.setAdresse(Adresse2);
                    Itulisateur.setCourriel(adressecourriel2);
                    Itulisateur.setUserType("Client");
                    Itulisateur.setNom(Nom2);
                    Itulisateur.setMotDePasse(MotDePasse2);
                    Itulisateur.setInformationsCarteCredit(InformationsCarteCredit2);

                    FirebaseDatabase.getInstance().getReference("Clients").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(Itulisateur).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                            Toast.makeText(SignUpPageActivity.this, "user registered success", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUpPageActivity.this, WelcomeClientActivity.class));
                            }else{
                                Toast.makeText(SignUpPageActivity.this, "Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(SignUpPageActivity.this, "Faileddd", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}