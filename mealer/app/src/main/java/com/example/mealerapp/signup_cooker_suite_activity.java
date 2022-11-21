package com.example.mealerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Objects;

public class signup_cooker_suite_activity extends AppCompatActivity implements View.OnClickListener{

private Button continuE;
    private static final int RESULT_LOAD_IMAGE = 1000 ;
    private static final String TAG = "signup_cooker_suite_activity" ;
Button uploadImage ;
ImageView chequePicture ;
FirebaseAuth mAuth ;
String uid ;
DatabaseReference reference ;
Cooker loggedCooker ;
EditText cookerBio ;
String descriptionInput ;
Uri imageURL ;
StorageReference storageRef ;
ProgressDialog myProgressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_cooker_suite);

        storageRef=FirebaseStorage.getInstance().getReference("CookersCheques");
        reference = FirebaseDatabase.getInstance().getReference("Users") ;
        cookerBio = (EditText) findViewById(R.id.new_description) ;
        continuE= (Button) findViewById(R.id.Continue_Btn);
        continuE.setOnClickListener(this);

        uploadImage = (Button) findViewById(R.id.pictureButton);
        uploadImage.setOnClickListener(this);

        chequePicture=(ImageView) findViewById(R.id.image) ;
    }
    public void onClick(View view){

        if(view.getId()==R.id.Continue_Btn){

            uid= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

            if (!uid.isEmpty()) {
                updateCookerData() ;
                uploadPicture() ; }

            startActivity(new Intent(this, signup_cooker_activity.class));
        }

        if (view.getId()==R.id.pictureButton) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK) ;
            galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI) ;
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        }
    }

    private void uploadPicture() {
        if (imageURL!= null) {
            storageRef=FirebaseStorage.getInstance().getReference();

            storageRef.putFile(imageURL).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    chequePicture.setImageURI(null) ;
                    Toast.makeText(signup_cooker_suite_activity.this, "Succesfully uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (myProgressDialog.isShowing()){
                        myProgressDialog.dismiss() ; }
                    Toast.makeText(signup_cooker_suite_activity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RESULT_LOAD_IMAGE && data != null & data.getData()!= null)  {
            chequePicture.setImageURI(data.getData());
        }
     }

    private void updateCookerData() {
        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Update note et nombre de repas vendus
                loggedCooker=snapshot.getValue(Cooker.class) ;
                assert loggedCooker != null;

                loggedCooker.setChequeImageURL(imageURL);
                descriptionInput=cookerBio.getText().toString().trim();

                if (descriptionInput.equals("")) {
                    loggedCooker.setDescription("No description yet") ; }
                else {
                    loggedCooker.setDescription(descriptionInput) ; }

                reference.child(uid).child("description").setValue(loggedCooker.getDescription());
                reference.child(uid).child("chequeImageUrl").setValue(loggedCooker.getChequeImageURL());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}