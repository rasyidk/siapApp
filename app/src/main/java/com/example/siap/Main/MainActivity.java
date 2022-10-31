package com.example.siap.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.siap.R;
import com.example.siap.dataModel;
import com.example.siap.adminLogin.adminLogin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tv_admin, tv_refresh;
    RecyclerView recyclerView;
    SearchView searchView;
    mainAdapter adapter;
    DatabaseReference mbase;
    private ArrayList<mainModel> mahasiswaArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_admin =  findViewById(R.id.main_tv_admin);
        tv_refresh = findViewById(R.id.main_tv_refresh);
        recyclerView =  findViewById(R.id.main_recycler);
        searchView =  findViewById(R.id.main_search);

        mahasiswaArrayList = new ArrayList<>();
        mbase = FirebaseDatabase.getInstance().getReference("arsipDataBaru2");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tv_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(MainActivity.this, adminLogin.class);
                startActivity(i);
            }
        });

        tv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromDB();
            }
        });

        searchData();
        getDataFromDB();
    }

    private void searchData() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s.toString());
                return false;
            }
        });
    }

    private void getDataFromDB() {

        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mahasiswaArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Log.d("key", ds.getKey());
                    mainModel person = ds.getValue(com.example.siap.Main.mainModel.class);
                    person.key = ds.getKey();
                    mahasiswaArrayList.add(person);
                }

                adapter = new mainAdapter(mahasiswaArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}