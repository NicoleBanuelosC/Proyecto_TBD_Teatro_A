package com.example.proyectofinal_tbd_teatro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPass = findViewById(R.id.etPass);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if ("admin@teatro.com".equals(email) && "1234".equals(pass)) {
                irAMain("administrador");
            } else if ("productor@teatro.com".equals(email) && "1234".equals(pass)) {
                irAMain("productor");
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }//Else
        });
    }//void

    private void irAMain(String rol) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("ROL", rol);
        startActivity(i);
        finish();
    }//irMain

}//Login
