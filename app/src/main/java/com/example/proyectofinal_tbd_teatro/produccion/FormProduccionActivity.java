package com.example.proyectofinal_tbd_teatro.produccion;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyectofinal_tbd_teatro.R;
import com.example.proyectofinal_tbd_teatro.DB.AppDatabase;
import com.example.proyectofinal_tbd_teatro.obra.Obra;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FormProduccionActivity extends AppCompatActivity {

    private EditText etLugar, etFecha, etPresupuesto;
    private Spinner spObra;
    private Button btnGuardar;
    private AppDatabase db; // ← Declara db aquí
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Produccion produccionActual = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_produccion);

        db = AppDatabase.getDatabase(this); // inicializa db

        etLugar = findViewById(R.id.etLugar);
        etFecha = findViewById(R.id.etFecha);
        etPresupuesto = findViewById(R.id.etPresupuesto);
        spObra = findViewById(R.id.spObra);
        btnGuardar = findViewById(R.id.btnGuardar);

        cargarObrasEnSpinner();

        produccionActual = (Produccion) getIntent().getSerializableExtra("PRODUCCION");
        if (produccionActual != null) {
            etLugar.setText(produccionActual.lugar);
            etFecha.setText(produccionActual.fecha);
            etPresupuesto.setText(String.valueOf(produccionActual.presupuesto));
            setTitle("Editar Producción");
        } else {
            setTitle("Nueva Producción");
        }//else

        btnGuardar.setOnClickListener(v -> guardarProduccion());
    }//onCreate

    private void cargarObrasEnSpinner() {
        executor.execute(() -> {
            List<Obra> obras = db.obraDao().getAll();
            runOnUiThread(() -> {
                String[] titulosObras = new String[obras.size()];
                for (int i = 0; i < obras.size(); i++) {
                    titulosObras[i] = obras.get(i).nombre;
                }//for

                spObra.setAdapter(new android.widget.ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, titulosObras));
            });
        });
    }//cargarObrasSpinner

    private void guardarProduccion() {
        String lugar = etLugar.getText().toString().trim();
        String fecha = etFecha.getText().toString().trim();
        String presupuestoStr = etPresupuesto.getText().toString().trim();

        // alidación de campos vacíos
        if (lugar.isEmpty() || fecha.isEmpty() || presupuestoStr.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }//if

        // validación de formato de fecha (dd/mm/yyyy)
        if (!fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
            Toast.makeText(this, "La fecha debe ser en formato dd/mm/yyyy", Toast.LENGTH_SHORT).show();
            etFecha.requestFocus();
            return;
        }//if

        // validación de presupuesto
        double presupuesto;
        try {
            presupuesto = Double.parseDouble(presupuestoStr);
            if (presupuesto <= 0) {
                Toast.makeText(this, "El presupuesto debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                return;
            }//if
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Presupuesto inválido", Toast.LENGTH_SHORT).show();
            return;
        }//try catchc

        // guardar producción
        Produccion produccion = new Produccion();
        produccion.lugar = lugar;
        produccion.fecha = fecha;
        produccion.presupuesto = presupuesto;

        executor.execute(() -> {
            List<Obra> obras = db.obraDao().getAll();
            Obra obraSeleccionada = obras.get(spObra.getSelectedItemPosition());
            produccion.obraId = obraSeleccionada.id;

            if (produccionActual != null) {
                produccion.id = produccionActual.id;
                db.produccionDao().update(produccion);
            } else {
                db.produccionDao().insert(produccion);
            }//if

            runOnUiThread(() -> {
                Toast.makeText(this, "Producción guardada", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }//guardarProduccion

}//public class