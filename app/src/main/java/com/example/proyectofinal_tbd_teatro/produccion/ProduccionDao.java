package com.example.proyectofinal_tbd_teatro.produccion;

import androidx.room.*;
import java.util.List;

@Dao
public interface ProduccionDao {
    @Query("SELECT * FROM producciones ORDER BY fecha DESC")
    List<Produccion> getAll();

    @Query("SELECT * FROM producciones WHERE obraId = :obraId")
    List<Produccion> getByObraId(int obraId);

    @Insert
    void insert(Produccion produccion);

    @Update
    void update(Produccion produccion);

    @Delete
    void delete(Produccion produccion);
}//public interface