package com.example.proyectofinal_tbd_teatro.obra;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;
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

    //protected porque permite que las clases dentro del mismo paquete accedan a ellos
    protected ExecutorService executor = Executors.newSingleThreadExecutor();
    protected AppDatabase db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_obras);

        db = AppDatabase.getDatabase(this);
        recyclerView = findViewById(R.id.recyclerViewObras);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarObras();
    }//onCreate

    void cargarObras() {
        executor.execute(() -> {
            List<Obra> obras = db.obraDao().getAll();
            runOnUiThread(() -> {
                adapter = new ObraAdapter(obras, this);
                recyclerView.setAdapter(adapter);
            });
        });
    }//cargarObras

    @Override
    protected void onResume() {
        super.onResume();
        cargarObras();
    }//onResume

}//public class