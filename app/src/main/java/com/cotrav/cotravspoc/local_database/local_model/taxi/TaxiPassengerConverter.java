package com.cotrav.cotravspoc.local_database.local_model.taxi;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.ArrayList;

public class TaxiPassengerConverter
{

    @TypeConverter
    public static String toString(ArrayList<String> passengers) {
        if (passengers == null) return null;
        Gson gson = new Gson();
        String screenshotsJson = gson.toJson(passengers);
        return screenshotsJson;
    }
}

