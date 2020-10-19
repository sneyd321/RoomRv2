package com.sneydr.roomrv2.Database.TypeConverters;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sneydr.roomrv2.Entities.House.Amenity.Amenity;
import com.sneydr.roomrv2.Entities.House.House;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AmenityTypeConverter {


    private Gson gson;

    public AmenityTypeConverter() {
        gson = new Gson();
    }

    @TypeConverter
    public List<Amenity> stringToList(String value) {
        Type typeToken = new TypeToken<ArrayList<Amenity>>(){}.getType();
        return gson.fromJson(value, typeToken);
    }

    @TypeConverter
    public String listToString(List<Amenity> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

}
