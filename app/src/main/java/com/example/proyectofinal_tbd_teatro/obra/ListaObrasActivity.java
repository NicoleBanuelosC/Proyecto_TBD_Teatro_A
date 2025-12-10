package com.example.proyectofinal_tbd_teatro.obra;

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

public class ListaObrasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ObraAdapter adapter;
    protected AppDatabase db;
    protected ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_obras);

        db = AppDatabase.getDatabase(this);
        recyclerView = findViewById(R.id.recyclerViewObras);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // BOTÓN PARA AGREGAR
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, FormObraActivity.class));
        });

        cargarObras();
    }

    protected void cargarObras() {
        executor.execute(() -> {
            List<Obra> obras = db.obraDao().getAll();
            runOnUiThread(() -> {
                adapter = new ObraAdapter(obras, this);
                recyclerView.setAdapter(adapter);
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarObras();
    }
}