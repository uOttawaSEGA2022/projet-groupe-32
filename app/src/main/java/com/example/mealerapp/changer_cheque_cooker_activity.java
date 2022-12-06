package com.example.mealerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class changer_cheque_cooker_activity extends AppCompatActivity {
    private Button change;
    private static final int RESULT_LOAD_IMAGE = 1000;
    private static final String TAG = "signup_cooker_suite_activity";
    Button uploadImage;
    ImageView chequePicture1;
    ImageView chequePicture2;
    FirebaseAuth mAuth;
    String uid;
    DatabaseReference reference;
    Cooker loggedCooker;
    EditText cookerBio;
    String descriptionInput;
    Uri imageURL;
    String imageURLD;
    StorageReference storageRef;
    ProgressDialog myProgressDialog;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.changer_cheque_cooker);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        change = (Button) findViewById(R.id.mettre_a_jour);



        chequePicture1 = (ImageView) findViewById(R.id.imageold);
        if (uid!=null) {
            updateCookerData();
        }

        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    //Log.e("firebase", "load succes");
                    loggedCooker=task.getResult().getValue(Cooker.class);
                    //imageURL= Uri.parse(loggedCooker.getChequeImageURL());
                    //Uri myUri = Uri.parse(Uri.decode(loggedCooker.getChequeImageURL()));
                    //chequePicture1.setImageURI(myUri);


                    }
            }
        });
        updateCookerData();
    }










    public void onClick(View view) {

        if (view.getId() == R.id.mettre_a_jour) {

            //updateCookerData();
            //startActivity(new Intent(changer_cheque_cooker_activity.this, changer_cheque_cooker_activity.class));

        }

        if (view.getId() == R.id.imageold) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        }
    }
    private void updateCookerData() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loggedCooker=snapshot.getValue(Cooker.class) ;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //startActivity(new Intent(changer_cheque_cooker_activity.this, changer_cheque_cooker_activity.class));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && data != null & data.getData() != null) {
            reference.child("chequeImageURL").setValue(data.getData().toString());
            //startActivity(new Intent(changer_cheque_cooker_activity.this, changer_cheque_cooker_activity.class));
            chequePicture1.setImageURI(data.getData());

        }
    }
}