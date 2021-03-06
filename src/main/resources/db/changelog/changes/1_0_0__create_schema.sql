-- liquibase formatted sql

-- changeset geekoff:1642823093965-1
CREATE SEQUENCE  IF NOT EXISTS hibernate_sequence START WITH 1000 INCREMENT BY 1;

-- changeset geekoff:1642823093965-2
CREATE TABLE answers (id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, create_at TIMESTAMP WITHOUT TIME ZONE, update_at TIMESTAMP WITHOUT TIME ZONE, comment VARCHAR(255), choice_id BIGINT, question_id BIGINT, survey_id BIGINT, user_id BIGINT, CONSTRAINT "answersPK" PRIMARY KEY (id));

-- changeset geekoff:1642823093965-3
CREATE TABLE choices (id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, create_at TIMESTAMP WITHOUT TIME ZONE, update_at TIMESTAMP WITHOUT TIME ZONE, content VARCHAR(255) NOT NULL, number INTEGER NOT NULL, question_id BIGINT, CONSTRAINT "choicesPK" PRIMARY KEY (id));

-- changeset geekoff:1642823093965-4
CREATE TABLE questions (id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, create_at TIMESTAMP WITHOUT TIME ZONE, update_at TIMESTAMP WITHOUT TIME ZONE, content VARCHAR(255) NOT NULL, survey_id BIGINT, CONSTRAINT "questionsPK" PRIMARY KEY (id));

-- changeset geekoff:1642823093965-5
CREATE TABLE roles (id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, name VARCHAR(255), CONSTRAINT "rolesPK" PRIMARY KEY (id));

-- changeset geekoff:1642823093965-6
CREATE TABLE surveys (id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, create_at TIMESTAMP WITHOUT TIME ZONE, update_at TIMESTAMP WITHOUT TIME ZONE, expire_date TIMESTAMP WITHOUT TIME ZONE NOT NULL, slug VARCHAR(255) NOT NULL, title VARCHAR(255) NOT NULL, user_id BIGINT, CONSTRAINT "surveysPK" PRIMARY KEY (id));

-- changeset geekoff:1642823093965-7
CREATE TABLE users (id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, create_at TIMESTAMP WITHOUT TIME ZONE, update_at TIMESTAMP WITHOUT TIME ZONE, activated BOOLEAN NOT NULL, email VARCHAR(255) NOT NULL, first_name VARCHAR(255) NOT NULL, last_name VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, username VARCHAR(255) NOT NULL, CONSTRAINT "usersPK" PRIMARY KEY (id));

-- changeset geekoff:1642823093965-8
CREATE TABLE users_roles (user_id BIGINT NOT NULL, role_id BIGINT NOT NULL, CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id));

-- changeset geekoff:1642823093965-9
ALTER TABLE roles ADD CONSTRAINT UC_ROLESNAME_COL UNIQUE (name);

-- changeset geekoff:1642823093965-10
ALTER TABLE users ADD CONSTRAINT UC_USERSEMAIL_COL UNIQUE (email);

-- changeset geekoff:1642823093965-11
ALTER TABLE users ADD CONSTRAINT UC_USERSUSERNAME_COL UNIQUE (username);

-- changeset geekoff:1642823093965-12
ALTER TABLE surveys ADD CONSTRAINT "UK_mt9mlg3b212nr9ygi1lb7mmx9" UNIQUE (slug);

-- changeset geekoff:1642823093965-13
ALTER TABLE answers ADD CONSTRAINT "UKnuhasayngavi95ujpmx6bk8np" UNIQUE (user_id, question_id);

-- changeset geekoff:1642823093965-14
ALTER TABLE answers ADD CONSTRAINT "FK2d1oukkdx92qeow2drcbuc586" FOREIGN KEY (survey_id) REFERENCES surveys (id);

-- changeset geekoff:1642823093965-15
ALTER TABLE users_roles ADD CONSTRAINT "FK2o0jvgh89lemvvo17cbqvdxaa" FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset geekoff:1642823093965-16
ALTER TABLE answers ADD CONSTRAINT "FK3erw1a3t0r78st8ty27x6v3g1" FOREIGN KEY (question_id) REFERENCES questions (id);

-- changeset geekoff:1642823093965-17
ALTER TABLE choices ADD CONSTRAINT "FK4vhssp102sjhbey1y4rhiiyos" FOREIGN KEY (question_id) REFERENCES questions (id);

-- changeset geekoff:1642823093965-18
ALTER TABLE answers ADD CONSTRAINT "FK5bp3d5loftq2vjn683ephn75a" FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset geekoff:1642823093965-19
ALTER TABLE answers ADD CONSTRAINT "FKdln843iye7s6nsir2n1lk50e7" FOREIGN KEY (choice_id) REFERENCES choices (id);

-- changeset geekoff:1642823093965-20
ALTER TABLE surveys ADD CONSTRAINT "FKiydpdbdg90l5bl365gt67qgrn" FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset geekoff:1642823093965-21
ALTER TABLE users_roles ADD CONSTRAINT "FKj6m8fwv7oqv74fcehir1a9ffy" FOREIGN KEY (role_id) REFERENCES roles (id);

-- changeset geekoff:1642823093965-22
ALTER TABLE questions ADD CONSTRAINT "FKnf38uiy78c0g0tmo68btk3y0p" FOREIGN KEY (survey_id) REFERENCES surveys (id);

