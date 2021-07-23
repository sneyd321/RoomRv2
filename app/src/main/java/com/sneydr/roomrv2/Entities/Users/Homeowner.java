package com.sneydr.roomrv2.Entities.Users;


import android.util.JsonReader;
import android.util.JsonToken;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Homeowner extends AccessibleObject {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String authToken;
    @Nullable
    private String imageURL;
    @Nullable
    private String taskId;



    public Homeowner(String firstName, String lastName, String email, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.imageURL = null;
        this.taskId = null;
    }


    public Homeowner(String firstName, String lastName, String email, String phoneNumber, String authToken, @Nullable String imageURL, @Nullable String taskId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = "";
        this.phoneNumber = phoneNumber;
        this.authToken = authToken;
        this.imageURL = imageURL;
        this.taskId = taskId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Nullable
    public String getImageURL() {
        return imageURL;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAuthToken() {
        return authToken;
    }

    @Nullable
    public String getTaskId() {
        return taskId;
    }

    public Homeowner readJsonStream(InputStream inputStream) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            reader.beginObject();
            return readHomeowner(reader);

        }
    }

    private Field getField(String name) {
        try {
            return Homeowner.class.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setField(Field field, String value) {
        try {
            field.set(this, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }


    public Homeowner readHomeowner(JsonReader reader) throws IOException {
        if (!reader.hasNext() || reader.peek() == JsonToken.END_OBJECT) {
            return this;
        }
        if (reader.peek() == JsonToken.NULL) {
            reader.skipValue();
            return readHomeowner(reader);
        }
        String name = reader.nextName();
        if (name == null) return readHomeowner(reader);

        Field field = getField(name);
        if (field == null) return readHomeowner(reader);

        if (!name.equals(field.getName()) || reader.peek() == JsonToken.NULL) return readHomeowner(reader);
        setField(field, reader.nextString());

        return readHomeowner(reader);
    }



}
