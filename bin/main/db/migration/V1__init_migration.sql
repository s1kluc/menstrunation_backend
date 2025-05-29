CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    email VARCHAR(255),
    birthdate DATETIME,
    weight FLOAT,
    height FLOAT
);

INSERT INTO user (username, email, birthdate, weight, height)
VALUES ('JohnDoe', 'johndoe@example.com', '1990-05-10', 70.5, 175.0);
