package com.sneydr.roomrv2.Entities.Users;


import androidx.annotation.Nullable;

import com.sneydr.roomrv2.Entities.House.Location.HomeownerLocation;

public final class Homeowner extends User {

    private HomeownerLocation homeownerLocation;

    public void setHomeownerLocation(HomeownerLocation homeownerLocation) { this.homeownerLocation = homeownerLocation; }

}
