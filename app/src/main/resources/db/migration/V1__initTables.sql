CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE essays
(
    essay_id    BIGINT NOT NULL,
    question_id BIGINT,
    essay_type  VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_essays PRIMARY KEY (essay_id)
);

CREATE TABLE questions
(
    question_id BIGINT NOT NULL,
    topic_id    BIGINT,
    question    VARCHAR(255),
    CONSTRAINT pk_questions PRIMARY KEY (question_id)
);

CREATE TABLE roles
(
    role_id   BIGINT NOT NULL,
    role_name VARCHAR(20),
    CONSTRAINT pk_roles PRIMARY KEY (role_id)
);

CREATE TABLE topics
(
    topic_id SERIAL NOT NULL,
    topic    VARCHAR(255),
    CONSTRAINT pk_topics PRIMARY KEY (topic_id)
);

CREATE TABLE user_plan
(
    plan_id     BIGINT NOT NULL,
    plan_type   VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_user_plan PRIMARY KEY (plan_id)
);

CREATE TABLE user_role
(
    role_id BIGINT NOT NULL,
    user_id UUID   NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (role_id, user_id)
);

CREATE TABLE users
(
    user_id uuid DEFAULT uuid_generate_v4() NOT NULL,
    username             VARCHAR(255),
    full_name            VARCHAR(255),
    email                VARCHAR(255),
    phone_number         VARCHAR(255),
    plan_id              BIGINT,
    dob                  TIMESTAMP WITHOUT TIME ZONE,
    password             VARCHAR(255),
    is_active            BOOLEAN,
    reset_password_token VARCHAR(255),
    create_date          TIMESTAMP WITHOUT TIME ZONE,
    create_by            VARCHAR(255),
    modify_date          TIMESTAMP WITHOUT TIME ZONE,
    modify_by            VARCHAR(255),
    provider             VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

ALTER TABLE essays
    ADD CONSTRAINT FK_ESSAYS_ON_QUESTION FOREIGN KEY (question_id) REFERENCES questions (question_id);

ALTER TABLE questions
    ADD CONSTRAINT FK_QUESTIONS_ON_TOPIC FOREIGN KEY (topic_id) REFERENCES topics (topic_id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_PLAN FOREIGN KEY (plan_id) REFERENCES user_plan (plan_id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_role FOREIGN KEY (role_id) REFERENCES roles (role_id);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_user FOREIGN KEY (user_id) REFERENCES users (user_id);
