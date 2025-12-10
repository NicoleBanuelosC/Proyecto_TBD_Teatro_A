package com.example.proyectofinal_tbd_teatro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.proyectofinal_tbd_teatro.obra.ListaObrasActivity;
import com.example.proyectofinal_tbd_teatro.produccion.ListaProduccionesActivity;

/*
Menu principal y muestra los botones segun el rol que ingrese
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // obtener el rol que viene del login
        String rol = getIntent().getStringExtra("ROL");

        // hcaer el enlace de botons
        Button btnObras = findViewById(R.id.btnObras);
        Button btnProducciones = findViewById(R.id.btnProducciones);

        // todos pueden ver obras
        btnObras.setOnClickListener(v ->
                startActivity(new Intent(this, ListaObrasActivity.class))
        );

        // unicamente y solo el admin y productor pueden ver producciones
        if ("administrador".equals(rol) || "productor".equals(rol)) {
            btnProducciones.setVisibility(Button.VISIBLE);
            btnProducciones.setOnClickListener(v ->
                    startActivity(new Intent(this, ListaProduccionesActivity.class))
            );
        } else {
            btnProducciones.setVisibility(Button.GONE);
        }//if-else

        // configuración de Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }//onCreate

}//public class