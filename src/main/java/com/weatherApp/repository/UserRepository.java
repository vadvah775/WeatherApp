package com.weatherApp.repository;

import com.weatherApp.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public User save(User user) {
        if (user.getId() == 0) {
            em.persist(user);
            return user;
        } else {
            return em.merge(user);
        }
    }

    public Optional<User> findById(long id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    public Optional<User> findByLogin(String login) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class);
        query.setParameter("login", login);
        return query.getResultStream().findFirst();
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Transactional
    public void delete(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }

    @Transactional
    public void deleteById(long id) {
        findById(id).ifPresent(this::delete);
    }

}
