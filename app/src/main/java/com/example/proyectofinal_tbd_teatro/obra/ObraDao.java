package com.example.proyectofinal_tbd_teatro.obra;

import androidx.room.*;
import java.util.List;

@Dao
public interface ObraDao {
    @Query("SELECT * FROM obras ORDER BY nombre ASC")
    List<Obra> getAll();

    @Query("SELECT * FROM obras WHERE id = :id")
    Obra findById(int id);

    @Insert
    void insert(Obra obra);

    @Update
    void update(Obra obra);

    @Delete
    void delete(Obra obra);
}//oublic interface
