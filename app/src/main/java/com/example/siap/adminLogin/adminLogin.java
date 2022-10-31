package com.example.siap.adminLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siap.Main.MainActivity;
import com.example.siap.R;
import com.example.siap.adminInput.adminInput;
import com.example.siap.splashScreen.splashScreen;

public class adminLogin extends AppCompatActivity {


    EditText et_username,et_password;
    Button btn_masuk;
    String username, password;

    String myusername, mypassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        et_username = findViewById(R.id.login_edtUsername);
        et_password =  findViewById(R.id.login_edtPassword);
        btn_masuk =  findViewById(R.id.login_btn_masuk);

        myusername ="admin";
        mypassword = "10Yogyakarta";

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = et_username.getText().toString();
                password = et_password.getText().toString();

                if (username.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(), "Data harus diisi semua", Toast.LENGTH_LONG).show();
                }else {
                    if (username.equals(myusername) && password.equals(mypassword)){
                        Intent i =  new Intent(adminLogin.this, adminInput.class);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Akun Tidak Terdaftar", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}