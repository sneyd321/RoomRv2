package com.sneydr.roomrv2.Database.Tenant;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sneydr.roomrv2.Entities.Users.Tenant;

import java.util.List;

@Dao
public interface TenantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Tenant tenant);

    @Update
    void update(Tenant tenant);

    @Delete
    void delete(Tenant tenant);

    @Query("SELECT * FROM tenant_table WHERE tenantId = :id")
    LiveData<Tenant> getTenant(int id);

    @Query("SELECT * FROM tenant_table WHERE houseId = :id")
    LiveData<List<Tenant>> getTenantsByHouseId(int id);

    @Query("SELECT * FROM tenant_table")
    LiveData<List<Tenant>> getTenants();


    @Query("SELECT * FROM tenant_table WHERE houseId = :id AND isApproved = 1")
    List<Tenant> getTenantsByHouseIdForLease(int id);


}
