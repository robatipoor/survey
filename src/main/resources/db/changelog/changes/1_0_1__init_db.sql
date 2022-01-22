-- liquibase formatted sql

-- changeset geekoff:1642823093966-1
INSERT INTO roles (id,name) VALUES (1 ,'ROLE_ADMIN');
-- changeset geekoff:1642823093966-2
INSERT INTO roles (id,name) VALUES (2,'ROLE_USER');
-- changeset geekoff:1642823093966-3
INSERT INTO users (id,create_at,update_at,activated,email,first_name,last_name,password,username) VALUES (3,'2021-11-27 13:33:49.358247','2021-11-27 13:33:49.358247',TRUE,'admin@mail.com','firstname','lastname','$2a$10$V8UTKiK17VgtHXkOL2qdp.UZ/PnADV9sH3k/7qcov8tuzuL0o7Tmy','admin');
-- changeset geekoff:1642823093966-4
INSERT INTO users (id,create_at,update_at,activated,email,first_name,last_name,password,username) VALUES (4,'2021-11-27 13:33:49.358247','2021-11-27 13:33:49.358247',TRUE,'user@mail.com','firstname','lastname','$2a$10$VR2nME9UO6pyIGVyCvuJUe0F803SlBTTw6mguNSK206JXp3V/KXSC','user');
-- changeset geekoff:1642823093966-5
INSERT INTO users_roles (user_id, role_id) VALUES (3,1);
-- changeset geekoff:1642823093966-6
INSERT INTO users_roles (user_id, role_id) VALUES (4,2);