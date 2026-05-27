package com.weatherApp.repository;

import com.weatherApp.entity.Session;
import com.weatherApp.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Session save(Session session){
        if(session.getId() == null){
            em.persist(session);
            return session;
        } else {
            return em.merge(session);
        }
    }

    public Optional<Session> findById(UUID id) {
        Session session = em.find(Session.class, id);
        return Optional.ofNullable(session);
    }

    public Optional<Session> findByUser(User user){
        TypedQuery<Session> query = em.createQuery("SELECT s FROM Session s WHERE s.user = :user", Session.class);
        query.setParameter("user", user);
        return query.getResultStream().findFirst();
    }

    @Transactional
    public void delete(Session session) {
        em.remove(em.contains(session) ? session : em.merge(session));
    }


}
