package com.example.trainpedia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;


public class Home extends AppCompatActivity {
    private SessionManager session;
    ListView myListView;
    RecyclerView recyclerView;
    ArrayList<Tiket> list;
    MyAdapter myAdapter;
    DatabaseReference dRef;
    SearchView searchView;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        session = new SessionManager(this);
        if(!session.isLoggedIn()){
            logout();
        }

        recyclerView = findViewById(R.id.listView);
        dRef = FirebaseDatabase.getInstance("https://trainpedia-a76fd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Tiket");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        searchView =findViewById(R.id.searchTiket);
        searchView.clearFocus();
        recyclerView.setAdapter(myAdapter);

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Tiket tiket = dataSnapshot.getValue(Tiket.class);
                    list.add(tiket);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });


        Button button2 = findViewById(R.id.button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform your desired action when the button is clicked
                // For example, you can start a new activity:
                startActivity(new Intent(Home.this, Checkout.class));
            }
        });

        Button btnLogout = (Button)findViewById(R.id.logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }


    private void logout(){
        session.setLogin(false);
        finish();
        startActivity(new Intent(Home.this,Login.class));
    }
    public void searchList(String text){
        ArrayList<Tiket> searchList = new ArrayList<>();
        for(Tiket tiket: list){
            if (tiket.getArgo().toLowerCase(Locale.ROOT).contains(text.toLowerCase())){
                searchList.add(tiket);
            }
            else if (tiket.getHarga().toLowerCase(Locale.ROOT).contains(text.toLowerCase())){
                searchList.add(tiket);
            }
            else if (tiket.getJurusan().toLowerCase(Locale.ROOT).contains(text.toLowerCase())){
                searchList.add(tiket);
            }
            else if (tiket.getKelas().toLowerCase(Locale.ROOT).contains(text.toLowerCase())){
                searchList.add(tiket);
            }
        }
        myAdapter.searchDataList(searchList);
    }
}
