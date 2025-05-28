ALTER Table user
    ADD COLUMN password VARCHAR(255);

INSERT INTO user (username, email, birthdate, weight, height, password)
VALUES ('testuser', 'test.user@testme.cum', '2000-05-19', 70.5, 175.0, "$2a$10$kz4nDCBifWK/ss1Ti5uVw./LlXAoHmZuLxzIIa5HEVgaFCbF.5AGq");

INSERT INTO vibe (user_id, anger, period, blood, mood)
VALUES (2, 10, false, 1, "ein, neuer, mood");