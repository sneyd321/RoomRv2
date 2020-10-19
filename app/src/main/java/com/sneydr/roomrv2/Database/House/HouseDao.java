package com.sneydr.roomrv2.Database.House;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sneydr.roomrv2.Entities.House.House;

import java.util.List;

@Dao
public interface HouseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(House house);

    @Update
    void update(House house);

    @Delete
    void delete(House house);


    @Query("SELECT * FROM house_table WHERE houseId = :id")
    LiveData<House> getHouse(int id);

    @Query("SELECT * FROM house_table WHERE houseId = :id")
    House getHouseForLease(int id);

    @Query("SELECT * FROM house_table")
    LiveData<List<House>> getHouses();




}
