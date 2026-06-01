package com.weatherApp.repository.impl;

import com.weatherApp.entity.Session;
import com.weatherApp.entity.User;
import com.weatherApp.repository.SessionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionRepositoryImpl implements SessionRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Session save(Session session) {
        if (session.getId() == null) {
            em.persist(session);
            return session;
        } else {
            return em.merge(session);
        }
    }

    @Override
    public Optional<Session> findById(UUID id) {
        Session session = em.find(Session.class, id);
        return Optional.ofNullable(session);
    }

    @Override
    public Optional<Session> findByUser(User user) {
        TypedQuery<Session> query = em.createQuery("SELECT s FROM Session s WHERE s.user = :user", Session.class);
        query.setParameter("user", user);
        return query.getResultStream().findFirst();
    }

    @Transactional
    @Override
    public void delete(Session session) {
        em.remove(em.contains(session) ? session : em.merge(session));
    }

    @Transactional
    @Override
    public int deleteExpiredSessions() {
        return em.createQuery("DELETE FROM Session s WHERE s.expiresAt < :now")
                .setParameter("now", LocalDateTime.now())
                .executeUpdate();
    }
}
