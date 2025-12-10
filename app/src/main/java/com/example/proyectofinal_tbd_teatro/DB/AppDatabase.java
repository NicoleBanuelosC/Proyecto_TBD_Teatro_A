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

    //SINGLETON
    private static volatile AppDatabase INSTANCE; //variable estatica unica

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) { //verifica si existe
            synchronized (AppDatabase.class) { //eviat problemas con los hilos
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder( //crea la instancia SOLO UNA VEZ
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "teatro_pleasantville_db"
                            )
                            .build();
                }//if
            }//synchronizad
        }//if afuera

        return INSTANCE;
    }//public static

}//public class