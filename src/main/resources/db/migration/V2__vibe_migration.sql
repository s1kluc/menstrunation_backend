CREATE TABLE vibe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    date DATETIME NOT NULL,
    anger BIGINT NOT NULL,
    period BOOLEAN NOT NULL,
    blood INT,
    mood VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);