CREATE TABLE mock_interviews (
                                 id BIGSERIAL PRIMARY KEY,
                                 user_id BIGINT NOT NULL REFERENCES users(id),
                                 company VARCHAR(255) NOT NULL,
                                 type VARCHAR(50) NOT NULL,
                                 status VARCHAR(50) NOT NULL DEFAULT 'SCHEDULED',
                                 scheduled_at TIMESTAMP NOT NULL,
                                 notes TEXT,
                                 created_at TIMESTAMP NOT NULL
);