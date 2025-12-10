package com.example.proyectofinal_tbd_teatro.Obra;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "obras")
public class Obra {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;
    public String autor;
    public String genero;
    public int duracionMinutos; // en minutos

    public Obra() {}

    public Obra(String nombre, String autor, String genero, int duracionMinutos) {
        this.nombre = nombre;
        this.autor = autor;
        this.genero = genero;
        this.duracionMinutos = duracionMinutos;
    }//public obra

}//Obra
