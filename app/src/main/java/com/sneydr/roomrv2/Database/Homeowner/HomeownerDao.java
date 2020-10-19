package com.sneydr.roomrv2.Database.Homeowner;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sneydr.roomrv2.Entities.Users.Homeowner;


@Dao
public interface HomeownerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Homeowner homeowner);

    @Update
    void update(Homeowner homeowner);

    @Delete
    void delete(Homeowner homeowner);

    @Query("SELECT * FROM homeowner_table LIMIT 1")
    LiveData<Homeowner> getHomeowner();

    @Query("SELECT * FROM homeowner_table WHERE homeownerId = :id")
    Homeowner getHomeownerForLease(int id);




}
