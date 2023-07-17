package com.example.trainpedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://trainpedia-a76fd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
    SharedPreferences sharedPreferences;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText username = findViewById(R.id.usernameUser);
        final EditText password = findViewById(R.id.passwordUser);
        final Button loginBtn = findViewById(R.id.loginuser);
        final TextView signup = findViewById(R.id.asadmin);
        session = new SessionManager(this);
        if(session.isLoggedIn()){
            startActivity(new Intent(Login.this,Home.class));
            finish();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Username = username.getText().toString().trim();
                final String Password = password.getText().toString().trim();

                if(Username.isEmpty()){
                    username.setError("Masukkan Username");
                    return;
                }
                if(Password.isEmpty()){
                    password.setError("Masukkan Password");
                    return;
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(Username)){
                                sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                final String getPassword = snapshot.child(Username).child("password").getValue(String.class);
                                String firstname = snapshot.child(Username).child("firstName").getValue(String.class);
                                String lastname = snapshot.child(Username).child("lastName").getValue(String.class);
                                String email = snapshot.child(Username).child("email").getValue(String.class);
                                String fullName = firstname + " " + lastname;

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("firstname", firstname);
                                editor.putString("lastname", lastname);
                                editor.putString("fullname", fullName);
                                editor.putString("email", email);
                                editor.apply();
                                if (getPassword.equals(Password)){
                                    Toast.makeText(Login.this, "Sukses Login", Toast.LENGTH_SHORT).show();
                                    SessionManager sessionManager = new SessionManager(getApplicationContext());
                                    sessionManager.setLogin(true);
                                    startActivity(new Intent(Login.this, Home.class));
                                }else{
                                    Toast.makeText(Login.this, "Terdapat Kesalahan Inputan", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Login.this, "Terdapat Kesalahan Inputan", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });
    }
}
