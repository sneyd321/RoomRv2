package com.sneydr.roomrv2;

import android.app.Application;
import android.content.Context;

import com.sneydr.roomrv2.Entities.Database.HomeownerDao;
import com.sneydr.roomrv2.Entities.Database.HouseDao;
import com.sneydr.roomrv2.Entities.Database.RoomRDatabase;
import com.sneydr.roomrv2.Entities.Database.TenantDao;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.House.Location.HomeownerLocation;
import com.sneydr.roomrv2.Entities.House.Location.RentalUnitLocation;
import com.sneydr.roomrv2.Entities.House.RentDetails.RentDetails;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import org.json.JSONException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class TestDatabaseActions {
    private HomeownerDao homeownerDao;
    private HouseDao houseDao;
    private TenantDao tenantDao;
    private RoomRDatabase db;
    private Context context;
    private JSONParser jsonParser;

    @Before
    public void createDb() {
        context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RoomRDatabase.class).build();
        homeownerDao = db.homeownerDao();
        houseDao = db.houseDao();
        tenantDao = db.tenantDao();
        jsonParser = new JSONParser((Application) context);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertHomeowner() throws Exception {
        HomeownerLocation homeownerLocation = new HomeownerLocation(11, "Bronte. Rd", "Oakville", "Onatario", "L6L 0E1", "207", "207");
        Homeowner homeowner = new Homeowner("Ryan", "Sneyd", "rts1234567@hotmail.com", "aaaaaa", "1234567890", homeownerLocation);
        homeownerDao.insert(homeowner);
        Homeowner testQuery = homeownerDao.testSelectByEmail(homeowner.getEmail());
        Assert.assertTrue(testQuery.equals(homeowner));
    }

    @Test
    public void insertDuplicateHomeowner() throws Exception {
        HomeownerLocation homeownerLocation = new HomeownerLocation(11, "Bronte. Rd", "Oakville", "Onatario", "L6L 0E1", "207", "207");
        Homeowner homeowner = new Homeowner("Ryan", "Sneyd", "rts1234567@hotmail.com", "aaaaaa", "1234567890", homeownerLocation);
        homeownerDao.insert(homeowner);
        homeownerDao.insert(homeowner);
        int testQuery = homeownerDao.getCount();
        Assert.assertEquals(testQuery, 1);
    }

    @Test
    public void insertHouse() throws Exception {
        HomeownerLocation homeownerLocation = new HomeownerLocation(11, "Bronte. Rd", "Oakville", "Onatario", "L6L 0E1", "207", "207");
        Homeowner homeowner = new Homeowner("Ryan", "Sneyd", "rts1234567@hotmail.com", "aaaaaa", "1234567890", homeownerLocation);
        RentalUnitLocation rentalUnitLocation = new RentalUnitLocation(11, "Bronte. Rd", "Oakville", "Onatario", "L6L 0E1", "Basement", false, 2);
        RentDetails rentDetails = new RentDetails("First", 200, 20);
        House house = new House(homeowner, rentalUnitLocation, rentDetails);
        houseDao.insert(house);
        List<House> testQuery = houseDao.testSelectAllHouses();
        Assert.assertNotNull(testQuery.get(0));
    }

    @Test
    public void insertTenantAndRetrieveList() throws Exception {
        HomeownerLocation homeownerLocation = new HomeownerLocation(11, "Bronte. Rd", "Oakville", "Onatario", "L6L 0E1", "207", "207");
        Homeowner homeowner = new Homeowner("Ryan", "Sneyd", "rts1234567@hotmail.com", "aaaaaa", "1234567890", homeownerLocation);
        RentalUnitLocation rentalUnitLocation = new RentalUnitLocation(11, "Bronte. Rd", "Oakville", "Onatario", "L6L 0E1", "Basement", false, 2);
        RentDetails rentDetails = new RentDetails("First", 200, 20);
        House house = new House(homeowner, rentalUnitLocation, rentDetails);
        Tenant tenant = new Tenant("Timmy", "Tenant", "sneydr@sheridancollege.ca", "aaaaaa", 1, true, house);
        tenantDao.insert(tenant);
        List<Tenant> testQuery = tenantDao.testSelectAllTenants();
        Assert.assertNotNull(testQuery.get(0));
    }

    @Test
    public void insertTenant() throws Exception {
        HomeownerLocation homeownerLocation = new HomeownerLocation(11, "Bronte. Rd", "Oakville", "Onatario", "L6L 0E1", "207", "207");
        Homeowner homeowner = new Homeowner("Ryan", "Sneyd", "rts1234567@hotmail.com", "aaaaaa", "1234567890", homeownerLocation);
        RentalUnitLocation rentalUnitLocation = new RentalUnitLocation(11, "Bronte. Rd", "Oakville", "Onatario", "L6L 0E1", "Basement", false, 2);
        RentDetails rentDetails = new RentDetails("First", 200, 20);
        House house = new House(homeowner, rentalUnitLocation, rentDetails);
        Tenant tenant = new Tenant("Timmy", "Tenant", "sneydr@sheridancollege.ca", "aaaaaa", 1, true, house);
        tenantDao.insert(tenant);
        Tenant testQuery = tenantDao.testSelectByEmail(tenant.getEmail());
        Assert.assertEquals(testQuery, tenant);
    }

    @Test
    public void parseLogin() throws JSONException {
        String response = getLoginResponse();
        String token = jsonParser.parseLogin(response);
        Assert.assertNotNull(token);
    }
    @Test
    public void parseHomeowner() {
        jsonParser.insertHomeowner(getHomeownerResponse());
        Homeowner testQuery = homeownerDao.testSelectByEmail("rts1234567@hotmail.com");
        Assert.assertNotNull(testQuery);

    }

    private String getLoginResponse() {
        return "{'token': 'eyJhbGciOiJIUzUxMiIsImlhdCI6MTU5ODAzNDkwNCwiZXhwIjoxNTk4MDM4NTA0fQ.eyJjb25maXJtIjoicnRzMTIzNDU2N0Bob3RtYWlsLmNvbSJ9.32VPU7Amy3Nh36U5j5LHvgrA0XG3XZcZQvY0vTW3I_I1dERrSRWkQUW-_SYYNVptUwI3-_NeINoe4-3fafWM9A'}";
    }

    private String getHomeownerResponse() {
        return "{'firstName': 'Ryan', 'lastName': 'Sneyd', 'email': 'rts1234567@hotmail.com', 'password': 'pbkdf2:sha256:150000$HLuAxaq1$d5e5c8ff7db6fa6458a00c9b439bb50cd7edf25a9c177ce7681911827cedfe67', 'phoneNumber': '4168186015', 'homeownerLocation': {'streetNumber': 11, 'streetName': 'Bronte', 'city': 'Oakville', 'province': 'Ontario', 'postalCode': 'L6L 0E1', 'unitNumber': '123', 'poBox': '123'}, 'houses': []}";
    }

}

