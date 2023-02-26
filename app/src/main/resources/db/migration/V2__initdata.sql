CREATE TABLE user_tracking
(
    track_id BIGINT NOT NULL,
    CONSTRAINT pk_user_tracking PRIMARY KEY (track_id)
);

INSERT INTO user_plan(plan_id, plan_type, description) values(1, 'NORMAL', 'NORMAL');
INSERT INTO user_plan(plan_id, plan_type, description) values(2, 'PREMIUM', 'PREMIUM');

INSERT INTO users(username, full_name, email, phone_number, plan_id, dob, password, is_active, create_date, create_by)
values ('user', 'Duy Nguyen', 'duynh@gmail.com', '0323221122', '1', '2001-03-04', '$2a$10$2zw4kEdmMKSTPs0GC6ToS.16sEl57OMO1ZtkrDUvu7LKsn0ShVlUW', 'true', '2022-06-17', 'duy');
INSERT INTO users(username, full_name, email, phone_number, plan_id, dob, password, is_active, create_date, create_by)
values ('admin', 'Khanh Truong', 'duynh7401@gmail.com', '03232122', '2', '2001-02-04', '$2a$10$2zw4kEdmMKSTPs0GC6ToS.16sEl57OMO1ZtkrDUvu7LKsn0ShVlUW', 'true', '2022-06-17', 'duy');

INSERT INTO roles(role_id, role_name) values(1, 'ROLE_ADMIN');
INSERT INTO roles(role_id, role_name) values(2, 'ROLE_USER');
