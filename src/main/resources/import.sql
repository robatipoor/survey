INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id , name) VALUES (2, 'ROLE_USER');

INSERT INTO users (id, create_at, update_at, activated, email, first_name, last_name, password,  username) VALUES (10000, '2021-11-27 13:33:49.358247', '2021-11-27 13:33:49.358247', TRUE, 'admin@mail.com', 'firstname', 'lastname', '$2a$10$V8UTKiK17VgtHXkOL2qdp.UZ/PnADV9sH3k/7qcov8tuzuL0o7Tmy', 'admin');
INSERT INTO users (id, create_at, update_at, activated, email, first_name, last_name, password, username) VALUES (20000, '2021-11-27 13:33:49.358247', '2021-11-27 13:33:49.358247', TRUE, 'user@mail.com', 'firstname', 'lastname', '$2a$10$VR2nME9UO6pyIGVyCvuJUe0F803SlBTTw6mguNSK206JXp3V/KXSC', 'user');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);

