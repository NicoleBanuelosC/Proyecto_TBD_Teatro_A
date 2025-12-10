package com.example.proyectofinal_tbd_teatro.Produccion;
import androidx.room.Entity;

import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(
        tableName = "producciones",
        foreignKeys = @ForeignKey(
                entity = com.example.proyectofinal_tbd_teatro.Obra.Obra.class,
                parentColumns = "id",
                childColumns = "obraId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Produccion {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int obraId; // ← Relación con Obra
    public String lugar;
    public String fecha; // Formato: "dd/MM/yyyy"
    public double presupuesto;

    public Produccion() {}

    public Produccion(int obraId, String lugar, String fecha, double presupuesto) {
        this.obraId = obraId;
        this.lugar = lugar;
        this.fecha = fecha;
        this.presupuesto = presupuesto;
    }
}//public class
