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

public class signup_client_activity extends AppCompatActivity implements View.OnClickListener {

    public Button SignUp;
    protected EditText editTextPrenom, editTextNom, editTextAdresseCourriel, editTextMotDePasse, editTextCVV, editTextAdresse, editTextConfirm, editTextInformationsCarteCredit;
    //private ProgressBar progressBar;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_client);
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
    }


    private void resisterUser() {

        String Prenom2 = editTextPrenom.getText().toString().trim();
        String Nom2 = editTextNom.getText().toString().trim();
        String adressecourriel2 = editTextAdresseCourriel.getText().toString().trim();
        String MotDePasse2 = editTextMotDePasse.getText().toString().trim();
        String Adresse2 = editTextAdresse.getText().toString().trim();
        String InformationsCarteCredit2 = editTextInformationsCarteCredit.getText().toString().trim();
        String MotDePasseConfirm2 = editTextConfirm.getText().toString().trim();
        String CVC = editTextCVV.getText().toString().trim();

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

        if (MotDePasse2.length() < 8) {
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
        if (CVC.isEmpty()) {
            editTextCVV.setError("Votre cvc est requis");
            editTextCVV.requestFocus();
            return;
        }else if (CVC.length() != 3) {
            editTextCVV.setError("Votre cvc est invalide");
            editTextCVV.requestFocus();
            return;
        }

        Client client =new Client(Prenom2,Nom2,adressecourriel2,MotDePasse2,Adresse2,CVC,InformationsCarteCredit2);

        mAuth.createUserWithEmailAndPassword(client.getCourriel(),client.getMotDePasse()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Client Itulisateur = client;
                    Plainte plainte1 = new Plainte("Indigeste", "amin_nna@gmail.com", "aguigma@gmail.com","03/11/2022","Le souci a été le tajine au veau et au miel avec abricots et pruneaux. La viande trop séche hélas et le tout trop sucré beaucoup trop. J ai donné ce que j ai pu à mon mari et laissé le reste. Puis j ai commandé une assiette de 3 fromages hélas non savoyards les 3. Et nous sommes en Savoie pays du fromage. Enfin mon mari a mangé mon dessert au marron et le sien hélas trop écoeurants surtout cette tarte à la praline.");
                    Plainte plainte2 = new Plainte("Moisissure", "aichalfakir@gmail.com", "aguigma@gmail.com","03/11/2022","Il y'avait de la moisissure dans le repas que j'ai reçu");
                    Plainte plainte3 = new Plainte("Brulée", "ydjido@gmail.com", "aguigma@gmail.com","03/11/2022","Je suis très déçue: un énorme goût de brulé . Je n’ai pas fini mon plat qui a fini à la poubelle. En espérant que ce soit juste une erreur qui sera vite réparée.");
                    Plainte plainte4 = new Plainte("Intoxiqué", "imaneL@gmail.com", "aguigma@gmail.com","03/11/2022","Plus jamais je ne recommanderais chez ce cuisnier! J'ai passé une semaine à l'hopital pour intoxiquation alimentaire!");
                    Plainte plainte5 = new Plainte("Inmangeable", "bertrand@gmail.com", "aguigma@gmail.com","03/11/2022","Le souci a été le tajine au veau et au miel avec abricots et pruneaux. La viande trop séche hélas et le tout trop sucré beaucoup trop. J ai donné ce que j ai pu à mon mari et laissé le reste. Puis j ai commandé une assiette de 3 fromages hélas non savoyards les 3. Et nous sommes en Savoie pays du fromage. Enfin mon mari a mangé mon dessert au marron et le sien hélas trop écoeurants surtout cette tarte à la praline.");

                    FirebaseDatabase.getInstance().getReference("Plaintes").push().setValue(plainte1);
                    FirebaseDatabase.getInstance().getReference("Plaintes").setValue(plainte2);
                    FirebaseDatabase.getInstance().getReference("Plaintes").setValue(plainte3);
                    FirebaseDatabase.getInstance().getReference("Plaintes").setValue(plainte4);
                    FirebaseDatabase.getInstance().getReference("Plaintes").setValue(plainte5);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(Itulisateur).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                            Toast.makeText(signup_client_activity.this, "user registered successefully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(signup_client_activity.this, client_page_activity.class));
                            }else{
                                Toast.makeText(signup_client_activity.this, "Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(signup_client_activity.this, "Faileddd", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}