CREATE TABLE questions
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    topic       VARCHAR(50)  NOT NULL,
    difficulty  VARCHAR(20)  NOT NULL,
    company_tag VARCHAR(100),
    status      VARCHAR(30)  NOT NULL DEFAULT 'NOT_ATTEMPTED',
    created_at  TIMESTAMP    NOT NULL
);