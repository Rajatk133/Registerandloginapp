package com.google.loginassessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NameActivity extends AppCompatActivity {
    Button Homebutton;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

     Homebutton=findViewById(R.id.Button);
     username= findViewById(R.id.username);


     username.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             if(charSequence.toString().length()>0){
                 Homebutton.setEnabled(true);
             }else{
                 Homebutton.setEnabled(false);
             }
         }

         @Override
         public void afterTextChanged(Editable editable) { }
     });

          Homebutton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  String UserName=username.getText().toString();
                  Intent i=new Intent(NameActivity.this,HomeActivity.class);
                  i.putExtra("Username1",UserName);
                  startActivity(i);
                  finish();
              }
          });
      }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        return true;
    }
}