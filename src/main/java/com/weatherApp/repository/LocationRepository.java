package com.weatherApp.repository;

import com.weatherApp.entity.Location;
import com.weatherApp.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LocationRepository {
    Location save(Location location);

    Optional<Location> findById(long id);

    List<Location> findByUser(User user);

    void delete(Location location);

    void deleteById(long id);
}
