package com.example.proyectofinal_tbd_teatro.obra;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectofinal_tbd_teatro.R;
import com.example.proyectofinal_tbd_teatro.DB.AppDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListaObrasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ObraAdapter adapter;
    private List<Obra> allObras; //lista completa de filtar
    protected AppDatabase db;
    protected ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_obras);

        db = AppDatabase.getDatabase(this);
        recyclerView = findViewById(R.id.recyclerViewObras);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //agregar
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, FormObraActivity.class));
        });

        //busqueda
        EditText etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }//onTextChanged

            @Override
            public void afterTextChanged(Editable s) {}
        });

        cargarObras();
    }//onCreate

    protected void cargarObras() {
        executor.execute(() -> {
            List<Obra> obras = db.obraDao().getAll();
            runOnUiThread(() -> {
                allObras = new ArrayList<>(obras); // guarda copia
                adapter = new ObraAdapter(obras, this);
                recyclerView.setAdapter(adapter);
            });
        });
    }//cargarOnras

    private void filter(String text) {
        List<Obra> filteredList = new ArrayList<>();
        for (Obra obra : allObras) {
            if (obra.nombre.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(obra);
            }//if
        }//for
        adapter.updateList(filteredList); // actualiza la lista
    }//filter

    @Override
    protected void onResume() {
        super.onResume();
        cargarObras();
    }//onResume

}//public class