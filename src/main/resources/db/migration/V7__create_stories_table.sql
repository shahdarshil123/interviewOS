CREATE TABLE stories (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL REFERENCES users(id),
                         title VARCHAR(255) NOT NULL,
                         situation TEXT NOT NULL,
                         task TEXT NOT NULL,
                         action TEXT NOT NULL,
                         result TEXT NOT NULL,
                         category VARCHAR(50) NOT NULL,
                         tags VARCHAR(255),
                         created_at TIMESTAMP NOT NULL
);