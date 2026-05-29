package com.weatherApp.service;

import com.weatherApp.dto.CurrentUserDto;
import com.weatherApp.entity.Location;
import com.weatherApp.entity.User;

import java.util.List;

public interface LocationService {
    boolean addLocationOfUser(CurrentUserDto currentUserDto, double lat, double lon, String name);

    boolean deleteLocationOfUser(CurrentUserDto currentUserDto, long locationId);

    List<Location> getLocationByUser(User user);
}
