package com.example.mealerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class signup_cooker_suite_activity extends AppCompatActivity implements View.OnClickListener{

private Button continuE;
    private static final int RESULT_LOAD_IMAGE = 1000 ;
Button uploadImage ;
ImageView chequePicture ;
FirebaseAuth mAuth ;
String uid ;
DatabaseReference reference ;
Cooker loggedCooker ;
EditText cookerBio ;
String descriptionInput ;
/*Uri imageURL ;
StorageReference storageRef ;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_cooker_suite);

        //storageRef=StorageReference.getInstance().getReference("CookersCheques");
        reference = FirebaseDatabase.getInstance().getReference("Users") ;
        cookerBio = (EditText) findViewById(R.id.new_description) ;
        continuE= (Button) findViewById(R.id.Continue_Btn);
        continuE.setOnClickListener(this);

        uploadImage = (Button) findViewById(R.id.pictureButton);
        uploadImage.setOnClickListener(this);

        chequePicture=(ImageView) findViewById(R.id.image) ;

        mAuth=FirebaseAuth.getInstance();
    }
    public void onClick(View view){
        if(view.getId()==R.id.Continue_Btn){
            uid=mAuth.getCurrentUser().getUid();
            if (!uid.isEmpty()) {
                updateCookerData() ; }
            //uploadPicture() ;
            startActivity(new Intent(this, signup_cooker_activity.class));
        }

        if (view.getId()==R.id.pictureButton) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK) ;
            galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI) ;
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        }
    }

    private String getFileExtension (Uri uri) {
        ContentResolver cR = getContentResolver() ;
        MimeTypeMap mime = MimeTypeMap.getSingleton() ;

        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    /*private void uploadPicture() {
        if (imageURL!= null) {
            StorageReference fileRef = storageRef.child(System.currentTimeMillis()+"."+getFileExtension(imageURL)) ;

            fileRef.putFile(imageURL).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(signup_cooker_suite_activity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress =(100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount()) ;
                }
            };
        }
    }*/

    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RESULT_LOAD_IMAGE)  {
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
                descriptionInput=cookerBio.getText().toString().trim();
                if (descriptionInput.equals("")) {
                    loggedCooker.setDescription("No description yet") ; }
                else {
                    loggedCooker.setDescription(descriptionInput) ; }

                reference.child(uid).child("description").setValue(loggedCooker.getDescription());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}