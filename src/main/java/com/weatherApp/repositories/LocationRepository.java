package com.weatherApp.repositories;

import com.weatherApp.models.Location;
import com.weatherApp.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Location save(Location location) {
        if (location.getId() == 0) {
            em.persist(location);
            return location;
        } else {
            return em.merge(location);
        }
    }

    public Optional<Location> findById(long id) {
        Location location = em.find(Location.class, id);
        return Optional.ofNullable(location);
    }

    public List<Location> findByUser(User user) {
        TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l WHERE l.user = :user", Location.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Transactional
    public void delete(Location location) {
        em.remove(em.contains(location) ? location : em.merge(location));
    }

    @Transactional
    public void deleteById(long id) {
        findById(id).ifPresent(this::delete);
    }
}
