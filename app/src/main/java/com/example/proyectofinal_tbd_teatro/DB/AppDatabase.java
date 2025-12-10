package com.example.proyectofinal_tbd_teatro.DB;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.proyectofinal_tbd_teatro.obra.Obra;
import com.example.proyectofinal_tbd_teatro.produccion.Produccion;

@Database(
        entities = {Obra.class, Produccion.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract com.example.proyectofinal_tbd_teatro.obra.ObraDao obraDao();
    public abstract com.example.proyectofinal_tbd_teatro.produccion.ProduccionDao produccionDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "teatro_pleasantville_db"
                            )
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}