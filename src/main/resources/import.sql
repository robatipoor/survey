INSERT INTO roles (id, name) VALUES (hibernate_sequence.nextval, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (hibernate_sequence.nextval, 'ROLE_USER');

INSERT INTO users (id, create_at, update_at, activated, email, first_name, last_name, password,  username) VALUES (hibernate_sequence.nextval, '2021-11-27 13:33:49.358247', '2021-11-27 13:33:49.358247', TRUE, 'admin@mail.com', 'firstname', 'lastname', '$2a$10$V8UTKiK17VgtHXkOL2qdp.UZ/PnADV9sH3k/7qcov8tuzuL0o7Tmy', 'admin');
INSERT INTO users (id, create_at, update_at, activated, email, first_name, last_name, password, username) VALUES (hibernate_sequence.nextval, '2021-11-27 13:33:49.358247', '2021-11-27 13:33:49.358247', TRUE, 'user@mail.com', 'firstname', 'lastname', '$2a$10$VR2nME9UO6pyIGVyCvuJUe0F803SlBTTw6mguNSK206JXp3V/KXSC', 'user');

INSERT INTO users_roles (user_id, role_id) VALUES (SELECT u.id FROM users u WHERE u.username = 'user',SELECT r.id FROM roles r WHERE r.name = 'ROLE_USER');
INSERT INTO users_roles (user_id, role_id) VALUES (SELECT u.id FROM users u WHERE u.username = 'admin',SELECT r.id FROM roles r WHERE r.name = 'ROLE_ADMIN');

