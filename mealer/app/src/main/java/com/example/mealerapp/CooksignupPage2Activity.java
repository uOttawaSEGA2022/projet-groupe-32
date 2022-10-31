package com.example.mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CooksignupPage2Activity extends AppCompatActivity implements View.OnClickListener{
private Button continuE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cooksignup_page2);
        continuE= (Button) findViewById(R.id.Continue_Btn);
        continuE.setOnClickListener(this);
    }
    public void onClick(View view){
        if(view.getId()==R.id.Continue_Btn){
            startActivity(new Intent(this, CookersignupPageActivity.class));
        }
    }
}