CREATE TABLE question_attempts (
       id BIGSERIAL PRIMARY KEY,
       question_id BIGINT NOT NULL REFERENCES questions(id),
       status VARCHAR(50) NOT NULL,
       time_taken INTEGER,
       approach TEXT,
       mistakes TEXT,
       notes TEXT,
       attempted_at TIMESTAMP NOT NULL,
       confidence_score INTEGER
);

