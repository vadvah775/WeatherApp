package com.weatherApp.service.impl;

import com.weatherApp.entity.Location;
import com.weatherApp.entity.User;
import com.weatherApp.repository.LocationRepository;
import com.weatherApp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    LocationRepository locationRepository;

    @Override
    public boolean addLocationOfUser(User user, double lat, double lon, String name) {
        boolean exists = locationRepository.findByUser(user).stream().anyMatch(
                l -> l.getLatitude().doubleValue() == lat && l.getLongitude().doubleValue() == lon);

        if (exists) return false;

        Location location = new Location(name, user, BigDecimal.valueOf(lat), BigDecimal.valueOf(lon));
        locationRepository.save(location);

        return true;
    }

    @Override
    public boolean deleteLocationOfUser(User user, long locationId) {
        Optional<Location> optLocation = locationRepository.findById(locationId);
        if (optLocation.isPresent() && optLocation.get().getUser().getId() == user.getId()) {
            locationRepository.deleteById(locationId);
            return true;
        }
        return false;
    }

    @Override
    public List<Location> getLocationByUser(User user) {
        return locationRepository.findByUser(user);
    }

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
}
