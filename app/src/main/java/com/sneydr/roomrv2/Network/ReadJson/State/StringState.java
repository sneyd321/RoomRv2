package com.sneydr.roomrv2.Network.ReadJson.State;

import android.util.JsonReader;

import java.io.IOException;
import java.lang.reflect.Field;

public class StringState<T> implements JsonFieldTypeState<T> {


    @Override
    public void setField(Field field, JsonReader reader, T t) throws IOException {
        try {
            field.set(t, reader.nextString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
