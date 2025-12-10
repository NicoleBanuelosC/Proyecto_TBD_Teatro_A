package com.example.proyectofinal_tbd_teatro.obra;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyectofinal_tbd_teatro.R;
import com.example.proyectofinal_tbd_teatro.DB.AppDatabase;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
aqui es donde el usuario podra hace rel altas y el cambio, ademas muestra un diseño muy agradable y entendible
muestra los campos, guarda datos y carga sus datos para editar
 */

public class FormObraActivity extends AppCompatActivity {
    private EditText etNombre, etAutor, etDuracion;
    private Spinner spGenero;
    private Button btnGuardar;
    private AppDatabase db;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Obra obraActual = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_obras);

        db = AppDatabase.getDatabase(this);

        etNombre = findViewById(R.id.etNombre);
        etAutor = findViewById(R.id.etAutor);
        etDuracion = findViewById(R.id.etDuracion);
        spGenero = findViewById(R.id.spGenero);
        btnGuardar = findViewById(R.id.btnGuardar);

        // generos disponibles
        String[] generos = {"Drama", "Comedia", "Tragedia", "Musical", "Farsa"};
        spGenero.setAdapter(new android.widget.ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, generos));

        // si se recibe una obra es modo edición
        obraActual = (Obra) getIntent().getSerializableExtra("OBRA");
        if (obraActual != null) {
            etNombre.setText(obraActual.nombre);
            etAutor.setText(obraActual.autor);
            for (int i = 0; i < generos.length; i++) {
                if (generos[i].equals(obraActual.genero)) {
                    spGenero.setSelection(i);
                    break;
                }//if

            }//for

            etDuracion.setText(String.valueOf(obraActual.duracionMinutos));
            setTitle("Editar Obra");
        } else {
            setTitle("Nueva Obra");

        }//else

        btnGuardar.setOnClickListener(v -> guardarObra());
    }//OnCreate

    private void guardarObra() {
        String nombre = etNombre.getText().toString().trim();
        String autor = etAutor.getText().toString().trim();
        String genero = spGenero.getSelectedItem().toString();
        String duracionStr = etDuracion.getText().toString().trim();

        //validacion de batos
        if (nombre.isEmpty() || autor.isEmpty() || duracionStr.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }//if

        int duracion;
        try {
            duracion = Integer.parseInt(duracionStr);
            if (duracion <= 0) {
                Toast.makeText(this, "La duración debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                return;
            }//if

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Duración inválida", Toast.LENGTH_SHORT).show();
            return;
        }//catch

        // se crea el objeto Obra
        Obra obra = new Obra();
        obra.nombre = nombre;
        obra.autor = autor;
        obra.genero = genero;
        obra.duracionMinutos = duracion;

        if (obraActual != null) {
            obra.id = obraActual.id; //edicion
        }//if

        //guardar en la bd
        executor.execute(() -> {
            if (obraActual != null) {
                db.obraDao().update(obra);
            } else {
                db.obraDao().insert(obra);
            }//else

            runOnUiThread(() -> {
                Toast.makeText(this, "Obra guardada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }//guardarObra

}//Ppublic class
