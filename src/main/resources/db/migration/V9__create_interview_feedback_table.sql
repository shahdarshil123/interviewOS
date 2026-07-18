CREATE TABLE interview_feedback (
                                    id BIGSERIAL PRIMARY KEY,
                                    interview_id BIGINT NOT NULL UNIQUE REFERENCES mock_interviews(id),
                                    strengths TEXT,
                                    weaknesses TEXT,
                                    technical_score INTEGER,
                                    communication_score INTEGER,
                                    next_steps TEXT,
                                    created_at TIMESTAMP NOT NULL
);