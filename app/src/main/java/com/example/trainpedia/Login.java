package com.example.trainpedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button2 = findViewById(R.id.loginuser);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform your desired action when the button is clicked
                // For example, you can start a new activity:
                startActivity(new Intent(Login.this, Home.class));
            }
        });
    }
    public void admin (View v){
        startActivity(new Intent(Login.this, SignUp.class));
    }
}
