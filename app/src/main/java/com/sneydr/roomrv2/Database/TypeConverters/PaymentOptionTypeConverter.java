package com.sneydr.roomrv2.Database.TypeConverters;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sneydr.roomrv2.Entities.House.Amenity.Amenity;
import com.sneydr.roomrv2.Entities.RentDetails.PaymentOption;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PaymentOptionTypeConverter {

    private Gson gson;

    public PaymentOptionTypeConverter() {
        gson = new Gson();
    }

    @TypeConverter
    public List<PaymentOption> stringToList(String value) {
        Type typeToken = new TypeToken<ArrayList<PaymentOption>>(){}.getType();
        return gson.fromJson(value, typeToken);
    }

    @TypeConverter
    public String listToString(List<PaymentOption> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

}
