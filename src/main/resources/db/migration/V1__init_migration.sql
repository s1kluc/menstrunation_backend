CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    email VARCHAR(255),
    birthdate DATETIME,
    weight FLOAT,
    height FLOAT
);