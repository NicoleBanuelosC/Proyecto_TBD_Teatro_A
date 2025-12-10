package com.example.proyectofinal_tbd_teatro.obra;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "obras")
public class Obra implements Serializable {
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
