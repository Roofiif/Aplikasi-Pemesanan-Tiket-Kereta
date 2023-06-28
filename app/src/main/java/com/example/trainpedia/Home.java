package com.example.trainpedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Home extends AppCompatActivity {
    private SessionManager session;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        session = new SessionManager(this);
        if(!session.isLoggedIn()){
            logout();
        }
        Button button2 = findViewById(R.id.button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform your desired action when the button is clicked
                // For example, you can start a new activity:
                startActivity(new Intent(Home.this, Checkout.class));
            }
        });
    }
    private void logout(){
        session.setLogin(false);
        finish();
        startActivity(new Intent(Home.this,Login.class));
    }
}
