package com.cotrav.cotravspoc.local_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cotrav.cotravspoc.models.show_taxi_model.show_taxi.TaxiBooking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TaxiBooking.class}, version = 1, exportSchema = false)

public abstract class SpocRoomDatabase extends RoomDatabase
{
    public abstract DaoAccess daoAccess();


    private static volatile SpocRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static SpocRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SpocRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SpocRoomDatabase.class, "spoc_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
