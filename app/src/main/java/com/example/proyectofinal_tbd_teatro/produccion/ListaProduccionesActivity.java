package com.example.proyectofinal_tbd_teatro.produccion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectofinal_tbd_teatro.R;
import com.example.proyectofinal_tbd_teatro.DB.AppDatabase;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListaProduccionesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProduccionAdapter adapter;
    protected AppDatabase db;
    protected ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producciones);

        db = AppDatabase.getDatabase(this);
        recyclerView = findViewById(R.id.recyclerViewProducciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // BOTÓN PARA AGREGAR
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, FormProduccionActivity.class));
        });

        cargarProducciones();
    }

    protected void cargarProducciones() {
        executor.execute(() -> {
            List<Produccion> producciones = db.produccionDao().getAll();
            runOnUiThread(() -> {
                adapter = new ProduccionAdapter(producciones, this);
                recyclerView.setAdapter(adapter);
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarProducciones();
    }
}