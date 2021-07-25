package com.sneydr.roomrv2.Entities.ReadJson.State;

import android.util.JsonReader;

import java.io.IOException;
import java.lang.reflect.Field;

public interface JsonFieldTypeState<T> {

    void setField(Field field, JsonReader reader, T t) throws IOException;

}
