package com.sneydr.roomrv2.Network.ReadJson;

import android.util.JsonReader;
import android.util.JsonToken;


import com.sneydr.roomrv2.Network.ReadJson.State.BoolState;
import com.sneydr.roomrv2.Network.ReadJson.State.IntState;
import com.sneydr.roomrv2.Network.ReadJson.State.JsonFieldTypeContext;
import com.sneydr.roomrv2.Network.ReadJson.State.JsonFieldTypeState;
import com.sneydr.roomrv2.Network.ReadJson.State.StringState;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class ReadJson<T> {

    protected Class<T> clazz;
    private Map<String, JsonFieldTypeState<T>> map;
    private JsonFieldTypeContext<T> typeContext;


    protected ReadJson(Class<T> clazz) {
        this.clazz = clazz;
        this.map = new HashMap<>();
        this.addState("java.lang.String", new StringState<>());
        this.addState("int", new IntState<>());
        this.addState("boolean", new BoolState<>());
        this.typeContext = new JsonFieldTypeContext<>();
    }


    public T read(JsonReader reader, T t) throws IOException {
        if (!reader.hasNext() || reader.peek() == JsonToken.END_OBJECT ) {
            return t;
        }
        if (reader.peek() == JsonToken.NULL || reader.peek() != JsonToken.NAME) {
            reader.skipValue();
            return read(reader, t);
        }
        String name = reader.nextName();
        if (name == null) return read(reader, t);

        Field field = getField(name);
        if (field == null) return read(reader, t);

        if (!name.equals(field.getName()) || reader.peek() == JsonToken.NULL) return read(reader, t);;
        setField(field, reader, t);
        return read(reader, t);
    }


    protected Field getSuperClassField(String name) {
        try {
            return clazz.getSuperclass().getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            e.getMessage();
        }
        return null;
    }


    protected Field getBaseField(String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            e.getMessage();
        }
        return null;
    }

    protected Field getField(String name) {
        Field baseField = getBaseField(name);
        if (baseField != null)
            return baseField;
        return getSuperClassField(name);
    }


    protected void addState(String name, JsonFieldTypeState<T> state) {
        this.map.put(name, state);
    }

    public JsonFieldTypeState<T> getState(String name) {
        typeContext.setState(map.get(name));
        return typeContext.getState();
    }


    protected void setField(Field field, JsonReader reader, T t) throws IOException {
        field.setAccessible(true);
        JsonFieldTypeState<T> state = getState(field.getType().getName());
        state.setField(field, reader, t);
        field.setAccessible(false);
    }




}
