package com.weatherApp.services;

import com.weatherApp.models.Location;
import com.weatherApp.models.User;
import com.weatherApp.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LocationService {

    LocationRepository locationRepository;

    public boolean addLocationToUser(User user, double lat, double lon, String name) {
        boolean exists = locationRepository.findByUser(user).stream().anyMatch(
                l -> l.getLatitude().doubleValue() == lat && l.getLongitude().doubleValue() == lon);

        if (exists) return false;

        Location location = new Location();
        location.setUser(user);
        location.setName(name);
        location.setLatitude(BigDecimal.valueOf(lat));
        location.setLongitude(BigDecimal.valueOf(lon));
        locationRepository.save(location);

        return true;
    }

    public List<Location> getLocationByUser(User user) {
        return locationRepository.findByUser(user);
    }

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
}
