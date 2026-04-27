CREATE TABLE IF NOT EXISTS sessions
(
    id         UUID PRIMARY KEY,
    user_id    BIGINT    NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_sessions_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);