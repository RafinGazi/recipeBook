package com.example.recipebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipebook.Adapter.DataAdapter;
import com.example.recipebook.Model.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText emailId, password, username;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth= FirebaseAuth.getInstance();
        username=findViewById(R.id.editTextTextPersonName);
        emailId= findViewById(R.id.editTextTextEmailAddress);
        password= findViewById(R.id.editTextTextPassword);
        btnSignUp=findViewById(R.id.signup);
        tvSignIn = findViewById(R.id.tvSignin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user= username.getText().toString();
                String email= emailId.getText().toString();
                String pass= password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else if(pass.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }
                else if(user.isEmpty()){
                    username.setError("Please enter your name");
                    username.requestFocus();
                }
                else if(email.isEmpty() && pass.isEmpty() && user.isEmpty()){
                    Toast.makeText(MainActivity.this, "fill the fields please", Toast.LENGTH_SHORT).show();
                }
                else if(!email.isEmpty() && !pass.isEmpty() && !user.isEmpty()){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                assert firebaseUser != null;
                                String userID = firebaseUser.getUid();
                                reference= FirebaseDatabase.getInstance().getReference("Users").child(userID);
                                HashMap <String,String> hashMap = new HashMap<>();
                                hashMap.put("id",userID);
                                hashMap.put("username", user);

                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                    }
                                });

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this, "it didnt work", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

    }

}