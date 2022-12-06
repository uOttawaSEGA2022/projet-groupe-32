package com.example.mealerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Objects;


public class signup_cooker_suite_activity extends AppCompatActivity implements View.OnClickListener {

    private Button continuE;
    private static final int RESULT_LOAD_IMAGE = 1000;
    private static final String TAG = "signup_cooker_suite_activity";
    Button uploadImage;
    ImageView chequePicture;
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
        setContentView(R.layout.signup_cooker_suite);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();


        reference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        cookerBio = (EditText) findViewById(R.id.new_description);
        continuE = (Button) findViewById(R.id.Continue_Btn);
        continuE.setOnClickListener(this);

        uploadImage = (Button) findViewById(R.id.pictureButton);
        uploadImage.setOnClickListener(this);

        chequePicture = (ImageView) findViewById(R.id.image);
    }

    public void onClick(View view) {

        if (view.getId() == R.id.Continue_Btn) {

            uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();


            startActivity(new Intent(this, cooker_page_activity.class));
        }

        if (view.getId() == R.id.pictureButton) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        }
    }

    private void uploadPicture() {
        if (imageURL != null) {
            //storageRef =  storageRef.child("Cheques" + "/" + uid + ".png");

            /*
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = storageRef.putFile(imageURL.toString());
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //chequePicture.setImageURI(imageURL);
                    Toast.makeText(signup_cooker_suite_activity.this, "Succesfully uploaded", Toast.LENGTH_SHORT).show();
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...

                }
            });

             */

            /*
            storageRef.putFile(imageURL).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    chequePicture.setImageURI(imageURL);
                    Toast.makeText(signup_cooker_suite_activity.this, "Succesfully uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (myProgressDialog.isShowing()) {
                        myProgressDialog.dismiss();
                    }
                    Toast.makeText(signup_cooker_suite_activity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                }
            });

             */
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && data != null & data.getData() != null) {
            chequePicture.setImageURI(data.getData());

            imageURL = data.getData();
            imageURLD = imageURL.getEncodedPath();
            setCookerData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setCookerData() {

        descriptionInput = cookerBio.getText().toString().trim();
        reference.child("chequeImageURL").setValue(imageURL.toString());
        if (descriptionInput.equals("")) {
            reference.child("description").setValue("No description yet");
        } else {
            reference.child("description").setValue(descriptionInput);
        }

    }




    }
