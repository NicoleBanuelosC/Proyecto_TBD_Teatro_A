package com.example.proyectofinal_tbd_teatro.produccion;

import android.os.Bundle;
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
    protected AppDatabase db; //portected para que se pueda acceder
    protected ExecutorService executor = Executors.newSingleThreadExecutor(); // protected para el acceso

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producciones);

        db = AppDatabase.getDatabase(this); //inicializa db

        recyclerView = findViewById(R.id.recyclerViewProducciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarProducciones();
    }//onCreate

    protected void cargarProducciones() {
        executor.execute(() -> {
            List<Produccion> producciones = db.produccionDao().getAll();
            runOnUiThread(() -> {
                adapter = new ProduccionAdapter(producciones, this);
                recyclerView.setAdapter(adapter);
            });
        });
    }//cargarProducciones

    @Override
    protected void onResume() {
        super.onResume();
        cargarProducciones();
    }//onResume

}//public class