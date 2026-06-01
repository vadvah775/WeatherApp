package com.weatherApp.repository;

import com.weatherApp.entity.Session;
import com.weatherApp.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository {
    Session save(Session session);

    Optional<Session> findById(UUID id);

    Optional<Session> findByUser(User user);

    void delete(Session session);

    int deleteExpiredSessions();
}
