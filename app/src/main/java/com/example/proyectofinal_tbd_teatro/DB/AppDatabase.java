package com.example.proyectofinal_tbd_teatro.DB;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.ForeignKey;
import com.example.proyectofinal_tbd_teatro.Obra.Obra;
import com.example.proyectofinal_tbd_teatro.Produccion.Produccion;

@Database(
        entities = {Obra.class, Produccion.class},
        version = 1,
        exportSchema = false
)//database

public abstract class AppDatabase extends RoomDatabase {
    public abstract com.example.proyectofinal_tbd_teatro.Obra.ObraDao obraDao();
    public abstract com.example.proyectofinal_tbd_teatro.Produccion.ProduccionDao produccionDao();

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
                }//if

            }//synchronized

        }//if

        return INSTANCE;

    }//public static

}//appDatabase
