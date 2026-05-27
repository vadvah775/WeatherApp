package com.weatherApp.services;

import com.weatherApp.models.Location;
import com.weatherApp.models.User;
import com.weatherApp.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    LocationRepository locationRepository;

    public boolean addLocationOfUser(User user, double lat, double lon, String name) {
        boolean exists = locationRepository.findByUser(user).stream().anyMatch(
                l -> l.getLatitude().doubleValue() == lat && l.getLongitude().doubleValue() == lon);

        if (exists) return false;

        Location location = new Location(name, user, BigDecimal.valueOf(lat), BigDecimal.valueOf(lon));
        locationRepository.save(location);

        return true;
    }

    public boolean deleteLocationOfUser(User user, long locationId) {
        Optional<Location> optLocation = locationRepository.findById(locationId);
        if (optLocation.isPresent() && optLocation.get().getUser().getId() == user.getId()){
            locationRepository.deleteById(locationId);
            return true;
        }
        return false;
    }

    public List<Location> getLocationByUser(User user) {
        return locationRepository.findByUser(user);
    }

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
}
