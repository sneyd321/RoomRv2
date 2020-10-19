package com.sneydr.roomrv2.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.sneydr.roomrv2.Database.Homeowner.HomeownerDao;
import com.sneydr.roomrv2.Database.House.HouseDao;
import com.sneydr.roomrv2.Database.Tenant.TenantDao;
import com.sneydr.roomrv2.Database.TypeConverters.AmenityTypeConverter;
import com.sneydr.roomrv2.Database.TypeConverters.PaymentOptionTypeConverter;
import com.sneydr.roomrv2.Database.TypeConverters.UtilityTypeConverter;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.House.Utility.Utility;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;

@Database(entities = {Homeowner.class, House.class, Tenant.class}, version = 5)
@TypeConverters({AmenityTypeConverter.class, UtilityTypeConverter.class, PaymentOptionTypeConverter.class})
public abstract class RoomRDatabase extends RoomDatabase {

    public abstract HomeownerDao homeownerDao();

    public abstract HouseDao houseDao();

    public abstract TenantDao tenantDao();

    private static RoomRDatabase instance;

    public static synchronized RoomRDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RoomRDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


    public void purge() {
        new PurgeDatabase().execute();
    }


    private static class PurgeDatabase extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (instance != null){
                instance.clearAllTables();
            }
            return null;
        }
    }



}
