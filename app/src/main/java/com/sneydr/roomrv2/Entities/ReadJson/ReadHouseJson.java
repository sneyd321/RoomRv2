package com.sneydr.roomrv2.Entities.ReadJson;

import android.util.JsonReader;

import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.House.Location.RentalUnitLocation;
import com.sneydr.roomrv2.Entities.ReadJson.State.JsonFieldTypeState;

import java.io.IOException;
import java.lang.reflect.Field;

public class ReadHouseJson extends ReadJson<House> {
    public ReadHouseJson(Class<House> clazz) {
        super(clazz);
        addState("com.sneydr.roomrv2.Entities.House.Location.RentalUnitLocation", new RentalUnitLocationTypeState());
    }

    private static class RentalUnitLocationTypeState implements JsonFieldTypeState<House> {

        @Override
        public void setField(Field field, JsonReader reader, House house) throws IOException {
            reader.beginObject();
            ReadJson<RentalUnitLocation> readRentalUnitLocationJson = new ReadRentalUnitLocationJson(RentalUnitLocation.class);
            RentalUnitLocation rentalUnitLocation = readRentalUnitLocationJson.read(reader, new RentalUnitLocation());
            house.setRentalUnitLocation(rentalUnitLocation);
            reader.endObject();
        }
    }




}
