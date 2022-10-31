package com.example.siap.adminActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siap.Main.MainActivity;
import com.example.siap.Main.mainAdapter;
import com.example.siap.Main.mainModel;
import com.example.siap.R;
import com.example.siap.adminLogin.adminLogin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adminActivity extends AppCompatActivity {

    TextView tv_logout,tv_refresh;
    RecyclerView recyclerView;
    SearchView searchView;
    adminActivityAdapter adapter;
    DatabaseReference mbase;
    private ArrayList<adminActivityModel> mahasiswaArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        tv_logout =  findViewById(R.id.adminact_tv_logout);
        tv_refresh =  findViewById(R.id.adminact_tv_refresh);
        recyclerView =  findViewById(R.id.adminact_recycler);
        searchView = findViewById(R.id.adminact_search);

        mbase = FirebaseDatabase.getInstance().getReference("arsipDataBaru2");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mahasiswaArrayList = new ArrayList<>();

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(adminActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        tv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromDatabase();
            }
        });

        searchData();
        getDataFromDatabase();
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

    private void getDataFromDatabase() {

        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mahasiswaArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    Log.d("key", ds.getKey());
                    adminActivityModel person = ds.getValue(com.example.siap.adminActivity.adminActivityModel.class);
                    person.key = ds.getKey();
                    mahasiswaArrayList.add(person);
                }

                adapter = new adminActivityAdapter(mahasiswaArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(adminActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();
            }
        });

    }
}