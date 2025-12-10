package com.example.proyectofinal_tbd_teatro.produccion;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "producciones",
        foreignKeys = @ForeignKey(
                entity = com.example.proyectofinal_tbd_teatro.obra.Obra.class,
                parentColumns = "id",
                childColumns = "obraId",
                onDelete = ForeignKey.CASCADE
        )
)

public class Produccion {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int obraId; // aqui esta la relación con Obra
    public String lugar;
    public String fecha; // Formato: "dd/MM/yyyy"
    public double presupuesto;

    public Produccion() {}

    public Produccion(int obraId, String lugar, String fecha, double presupuesto) {
        this.obraId = obraId;
        this.lugar = lugar;
        this.fecha = fecha;
        this.presupuesto = presupuesto;
    }//public Produccion

}//public class
