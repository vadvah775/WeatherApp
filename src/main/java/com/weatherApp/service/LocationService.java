package com.weatherApp.service;

import com.weatherApp.entity.Location;
import com.weatherApp.entity.User;

import java.util.List;

public interface LocationService {
    public boolean addLocationOfUser(User user, double lat, double lon, String name);

    public boolean deleteLocationOfUser(User user, long locationId);

    public List<Location> getLocationByUser(User user);
}
