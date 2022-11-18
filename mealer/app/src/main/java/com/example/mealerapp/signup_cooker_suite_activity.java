package com.example.mealerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class signup_cooker_suite_activity extends AppCompatActivity implements View.OnClickListener{

private Button continuE;
    private static final int RESULT_LOAD_IMAGE = 1000 ;
Button uploadImage ;
ImageView chequePicture ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_cooker_suite);
        continuE= (Button) findViewById(R.id.Continue_Btn);
        continuE.setOnClickListener(this);

        uploadImage = (Button) findViewById(R.id.pictureButton);
        uploadImage.setOnClickListener(this);

        chequePicture=(ImageView) findViewById(R.id.image) ;
    }
    public void onClick(View view){
        if(view.getId()==R.id.Continue_Btn){
            startActivity(new Intent(this, signup_cooker_activity.class));
        }

        if (view.getId()==R.id.pictureButton) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK) ;
            galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI) ;
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        }
    }

     @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RESULT_LOAD_IMAGE)  {
            chequePicture.setImageURI(data.getData());
        }
     }
}