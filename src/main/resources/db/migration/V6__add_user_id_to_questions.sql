ALTER TABLE questions
    ADD COLUMN user_id BIGINT REFERENCES users(id);

UPDATE questions SET user_id = (SELECT id FROM users LIMIT 1);

ALTER TABLE questions
    ALTER COLUMN user_id SET NOT NULL;