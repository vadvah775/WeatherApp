package com.weatherApp.service.impl;

import com.weatherApp.dto.CurrentUserDto;
import com.weatherApp.entity.Location;
import com.weatherApp.entity.User;
import com.weatherApp.exception.UserNotFoundException;
import com.weatherApp.repository.LocationRepository;
import com.weatherApp.repository.UserRepository;
import com.weatherApp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    LocationRepository locationRepository;

    UserRepository userRepository;

    @Override
    @Transactional
    public boolean addLocationOfUser(CurrentUserDto currentUserDto, double lat, double lon, String name) {
        Optional<User> optUser = userRepository.findByLogin(currentUserDto.getLogin());
        if(optUser.isEmpty()){
            throw new UserNotFoundException("User with this username was not found");
        }
        User user = optUser.get();
        boolean exists = locationRepository.findByUser(user).stream().anyMatch(
                l -> l.getLatitude().doubleValue() == lat && l.getLongitude().doubleValue() == lon);

        if (exists) {
            return false;
        }

        Location location = new Location(name, user, BigDecimal.valueOf(lat), BigDecimal.valueOf(lon));
        locationRepository.save(location);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteLocationOfUser(CurrentUserDto currentUserDto, long locationId) {
        Optional<Location> optLocation = locationRepository.findById(locationId);
        Optional<User> optUser = userRepository.findByLogin(currentUserDto.getLogin());
        if(optUser.isEmpty()){
            throw new UserNotFoundException("User with this username was not found");
        }
        if (optLocation.isPresent() && optLocation.get().getUser().getId() == optUser.get().getId()) {
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

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
