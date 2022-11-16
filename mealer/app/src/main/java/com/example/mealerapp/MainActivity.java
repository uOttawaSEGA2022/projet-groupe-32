package com.example.mealerapp;

import static android.util.Patterns.EMAIL_ADDRESS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView signUp, logOut;
    private EditText editTextAdresseCourriel, editTextPassword;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {

////      //  String Id="ZDCRr9A2xqSEuV9jBKJ82wVdmrn2";
//   //     String Id1="9ji8VUyh0VNmDYW7CgYRaLU06lE2";
//             String Id2="LPjxf4C8b9TDO8Vy4iNFPtfJU742";
//     String Id3="bDeWI33UPrYCm5RjWaZxqsu56gu1";
////        //User user=new User("Admin","Admin","administrator23@gmail.com","Administrator23","Administrator","12 street");
//  //      Cooker cook1= new Cooker("ht","dhg","aguig@gmail.com","12345678","Cooker","12 wwe","je suis cook","non"," ",null);
//        Cooker cook2= new Cooker("qqww","lhsgchgjh","aguigma@gmail.com","12345678","Cooker","12 wwe","je suis cook","non"," ",null);
//       Cooker cook3= new Cooker("hserfrdsgfg","yhjtfjh","wwe@gmail.com","12345678","Cooker","12 wwe","je suis cook","non"," ",null);
////       // FirebaseDatabase.getInstance().getReference("Users").child(Id).setValue(user);
//  //     FirebaseDatabase.getInstance().getReference("Users").child(Id1).setValue(cook1);
//        FirebaseDatabase.getInstance().getReference("Users").child(Id2).setValue(cook2);
//       FirebaseDatabase.getInstance().getReference("Users").child(Id3).setValue(cook3);
////
////
////
////
//       String Id4=FirebaseDatabase.getInstance().getReference("Plaintes").push().getKey();
////        String Id14=FirebaseDatabase.getInstance().getReference("Plaintes").push().getKey();
//       Plainte plainte1 = new Plainte(Id4,"Indigeste", "amin_nna@gmail.com", "wwe@gmail.com","03/11/2022","oui oui");
////        Plainte plainte2 = new Plainte(Id14,"Moisissure", "aichalfakir@gmail.com", "aguigma@gmail.com","03/11/2022","Il y'avait de la moisissure dans le repas que j'ai reçu");
//       FirebaseDatabase.getInstance().getReference("Plaintes").child(Id4).setValue(plainte1);
////        FirebaseDatabase.getInstance().getReference("Plaintes").child(Id14).setValue(plainte2);


        super.onCreate(savedInstanceState);//C'est quoi saved instances ?
        setContentView(R.layout.main_page);

        signUp = findViewById(R.id.signupButton);
        signUp.setOnClickListener(this);
        logOut = findViewById(R.id.loginButton);
        logOut.setOnClickListener(this);


        editTextAdresseCourriel = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.LogInPassword);
        mAuth = FirebaseAuth.getInstance();
    }


    public void onClick(View view) {
        if (view.getId() == R.id.signupButton) {
            startActivity(new Intent(this, signup_activity.class));
        }


        // Login
        if (view.getId() == R.id.loginButton) {
            UserLogin();
        }

        //Logout
        if (view.getId() == R.id.logout_Button_traiter_demande) {
            UserLogout();
        }
    }

    private void UserLogout() {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }



    private void UserLogin() {
        //Getting information and trimming it to a string
        String MotDePasse = editTextPassword.getText().toString().trim();
        String adressecourriel = editTextAdresseCourriel.getText().toString().trim();

        //Si le champ du courriel est vide
        if (adressecourriel.isEmpty()) {
            editTextAdresseCourriel.setError("Votre email est requis.");
            editTextAdresseCourriel.requestFocus();
            return;
        }
        //Si le courriel est non valide
        if (!EMAIL_ADDRESS.matcher(adressecourriel).matches()) {
            editTextAdresseCourriel.setError("Votre email est non valide.");
            editTextAdresseCourriel.requestFocus();
            return;
        }

        //Si le champ du mot de passe est vide
        if (MotDePasse.isEmpty()) {
            editTextPassword.setError("Votre mot de passse est requis");
            editTextPassword.requestFocus();
            return;
        }
        //Si le mot de passe est non valide
        if (MotDePasse.length() < 8) {
            editTextPassword.setError(" Mot de passse est court: il faut 8 caractères au minimum");
            editTextPassword.requestFocus();
            return;

        }
//Getting current date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();




        //signing In a user using his firebase Credentials
        mAuth.signInWithEmailAndPassword(adressecourriel, MotDePasse).
                addOnCompleteListener(

                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //getting user userType and use it to sign him in

                                    //On va regarder dans la table Users dans firebase

                                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());
                                    database.addListenerForSingleValueEvent(new ValueEventListener() {


                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String idUser = mAuth.getCurrentUser().getUid();
                                            User user = snapshot.getValue(User.class);
                                            Cooker cook =snapshot.getValue(Cooker.class);
                                            //Client client=snapshot.getValue(Client.class);
                                          String suspension =cook.getSuspension();
                                          String suspensionEndTime =cook.getSuspensionEndTime();

                                            if (user == null) {
                                                Toast.makeText(MainActivity.this, "You don't have an account, Please sign up !", Toast.LENGTH_LONG).show();
                                            } else {

                                                //Check le type d'utilisateur et le diriger vers sa page
                                                if (user.getUserType().equals("Client")) {
                                                    //diriger vers profil utilisateur client
                                                    startActivity(new Intent(MainActivity.this, client_page_activity.class));
                                                } else if (user.getUserType().equals("Cooker")) {
                                                    //diriger vers profil utilisateur cooker


                                                    Log.i("MainActivity", "le suspensionqqqqq on datat change est" + suspension);
                                                    Log.i("MainActivity", "le suspensionTimeqqqqqq on datat change est" + suspensionEndTime);


                                                    if (suspension.equals("oui Temporairement")) {
                                                        try {
                                                            //compare current date with suspensionEndTime date and redirect the user to the right profil
                                                            Date date1 = formatter.parse(suspensionEndTime);
                                                            if (date1.after(date)) {
                                                                //redirect him to the suspended page
                                                                Intent intent = new Intent(MainActivity.this, Cook_Temporary_suspension.class);
                                                                intent.putExtra("SuspensionEndTime", suspensionEndTime);
                                                                startActivity(intent);
                                                            } else {
                                                                //modifie user information on firebase and redirect him to his working profil
                                                                database.child("suspension").setValue("non");
                                                                database.child("supensionEndTime").setValue("");
                                                                startActivity(new Intent(MainActivity.this, cooker_page_activity.class));
                                                            }
                                                        } catch (ParseException e) {
                                                            e.printStackTrace();
                                                        }

                                                    } else if (suspension.equals("oui Indefinement")) {
                                                        startActivity(new Intent(MainActivity.this, Cook_Indefinitly_suspension.class));
                                                    } else {
                                                        //redirect him to his working profil
                                                        startActivity(new Intent(MainActivity.this, cooker_page_activity.class));
                                                    }
                                                } else if (user.getUserType().equals("Administrator")) {
                                                    //diriger vers profil utilisateur admin
                                                    startActivity(new Intent(MainActivity.this, admin_page_activity.class));
                                                }
                                            }
                                        }

                                        //Nothing
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });

                                } else {
                                    Toast.makeText(MainActivity.this, "Failed to login check your credentials", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                );
    }
}