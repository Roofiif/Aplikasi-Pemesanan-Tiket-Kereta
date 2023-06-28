package com.example.trainpedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    EditText email, firstName, lastName, username, password;
    Button singup;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://trainpedia-a76fd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.usernameemail);
        firstName = findViewById(R.id.usernamefirst);
        lastName = findViewById(R.id.usernamelast);
        username = findViewById(R.id.usernameUser);
        password = findViewById(R.id.passwordUser);
        singup = findViewById(R.id.btnDaftar);

        firebaseAuth = FirebaseAuth.getInstance();

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String LastName = lastName.getText().toString().trim();
                String FirstName = firstName.getText().toString().trim();
                String Username = username.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();

                if(TextUtils.isEmpty(FirstName)){
                    firstName.setError("Masukkan Nama Awal anda");
                    return;
                }
                if(TextUtils.isEmpty(LastName)){
                    lastName.setError("Masukkan Nama Akhir anda");
                    return;
                }
                if(TextUtils.isEmpty(Username)){
                    username.setError("Masukkan username anda");
                    return;
                }
                if(TextUtils.isEmpty(Email)){
                    email.setError("Masukkan Email anda");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    password.setError("Masukkan Password anda");
                    return;
                }
                if(Password.length() < 8){
                    password.setError("Password tidak boleh kurang dari 8 karakter");
                    return;
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(Username)){
                                Toast.makeText(SignUp.this, "Username Telah Ada", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("users").child(Username).child("firstName").setValue(FirstName);
                                databaseReference.child("users").child(Username).child("lastName").setValue(LastName);
                                databaseReference.child("users").child(Username).child("email").setValue(Email);
                                databaseReference.child("users").child(Username).child("password").setValue(Password);
                                Toast.makeText(SignUp.this, "Anda Telah Berhasil Mendaftar", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

    }
}