ALTER TABLE questions
ADD COLUMN last_attempted_at TIMESTAMP,
ADD COLUMN confidence_score INTEGER DEFAULT 0;