package com.sneydr.roomrv2.Network.ReadJson;

import android.util.JsonReader;

import com.sneydr.roomrv2.Entities.House.Location.HomeownerLocation;
import com.sneydr.roomrv2.Network.ReadJson.State.JsonFieldTypeState;
import com.sneydr.roomrv2.Entities.Users.Homeowner;

import java.io.IOException;
import java.lang.reflect.Field;

public class ReadHomeownerJson extends ReadJson<Homeowner> {
    public ReadHomeownerJson(Class<Homeowner> clazz) {
        super(clazz);
        addState("com.sneydr.roomrv2.Entities.House.Location.HomeownerLocation", new HomeownerLocationState());
    }

    private static class HomeownerLocationState implements JsonFieldTypeState<Homeowner> {

        @Override
        public void setField(Field field, JsonReader reader, Homeowner homeowner) throws IOException {
            reader.beginObject();
            ReadJson<HomeownerLocation> homeownerLocationReadJson = new ReadHomeownerLocationJson(HomeownerLocation.class);
            HomeownerLocation homeownerLocation = homeownerLocationReadJson.read(reader, new HomeownerLocation());
            homeowner.setHomeownerLocation(homeownerLocation);
            reader.endObject();
        }
    }

}
