package com.sneydr.roomrv2.Database.TypeConverters;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sneydr.roomrv2.Entities.House.Amenity.Amenity;
import com.sneydr.roomrv2.Entities.House.Utility.Utility;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UtilityTypeConverter  {

    private Gson gson;

    public UtilityTypeConverter() {
        gson = new Gson();
    }

    @TypeConverter
    public List<Utility> stringToList(String value) {
        Type typeToken = new TypeToken<ArrayList<Utility>>(){}.getType();
        return gson.fromJson(value, typeToken);
    }

    @TypeConverter
    public String listToString(List<Utility> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
