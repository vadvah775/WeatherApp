CREATE TABLE IF NOT EXISTS locations
(
    id        BIGSERIAL PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    user_id   BIGINT       NOT NULL,
    latitude  DECIMAL,
    longitude DECIMAL,
    CONSTRAINT fk_locations_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);