
CREATE TABLE workout_template_day (
                                      ID              SERIAL NOT NULL,
                                      training_planID int4 NOT NULL,
                                      name            varchar(255) NOT NULL,
                                      day_of_week     int4 NOT NULL CHECK(day_of_week >= 0 AND day_of_week < 7),
                                      notes           text,
                                      PRIMARY KEY (ID));

CREATE TABLE workout_session (
                                 ID                   SERIAL NOT NULL,
                                 user_training_planID int4 NOT NULL,
                                 session_date         date NOT NULL,
                                 start_time           time NOT NULL,
                                 end_time             time,
                                 notes                text,
                                 completed            bool DEFAULT 'FALSE' NOT NULL,
                                 updated_at           timestamp DEFAULT CURRENT_TIMESTAMP,
                                 PRIMARY KEY (ID));

CREATE TABLE exercise_template (
                                   ID               SERIAL NOT NULL,
                                   name             varchar(255) NOT NULL,
                                   description      text,
                                   muscle_group     varchar(50) NOT NULL CHECK(muscle_group IN ('CHEST', 'BACK', 'LEGS', 'SHOULDERS', 'ARMS', 'CORE', 'FULL_BODY')),
                                   equipment_needed varchar(255),
                                   instructions     text,
                                   created_at       timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                   PRIMARY KEY (ID));

CREATE TABLE gym (
                     ID         SERIAL NOT NULL,
                     name       varchar(255) NOT NULL,
                     email      varchar(255) NOT NULL UNIQUE,
                     created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
                     PRIMARY KEY (ID));

CREATE TABLE goal (
                      ID                  SERIAL NOT NULL,
                      userID              int4 NOT NULL,
                      exercise_templateID int4 NOT NULL,
                      title               varchar(255) NOT NULL,
                      description         text,
                      target_value        numeric(10, 2) NOT NULL CHECK(target_value > 0),
                      start_date          date DEFAULT CURRENT_DATE NOT NULL,
                      target_date         date NOT NULL,
                      status              varchar(20) NOT NULL CHECK(status IN ('IN_PROGRESS', 'ACHIEVED', 'ABANDONED')),
                      created_at          timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
                      updated_at          timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
                      PRIMARY KEY (ID));

CREATE TABLE exercise_instance (
                                   ID                      SERIAL NOT NULL,
                                   user_workout_exerciseID int4 NOT NULL,
                                   order_in_workout        int4 NOT NULL,
                                   completed               bool DEFAULT 'FALSE' NOT NULL,
                                   PRIMARY KEY (ID));

CREATE TABLE exercise_instance_set (
                                       ID                  SERIAL NOT NULL,
                                       exercise_instanceID int4 NOT NULL,
                                       set_number          int4 NOT NULL CHECK(set_number > 0),
                                       reps                int4 NOT NULL CHECK(reps > 0),
                                       weight              numeric(6, 2) NOT NULL CHECK(weight > 0),
                                       completed           bool DEFAULT 'FALSE' NOT NULL,
                                       PRIMARY KEY (ID),
                                       CONSTRAINT exercise_instance_set_unique_per_exercise
                                           UNIQUE (exercise_instanceID, set_number));

CREATE TABLE training_plan (
                               ID               SERIAL NOT NULL,
                               name             varchar(255) NOT NULL,
                               description      text,
                               difficulty_level varchar(20) CHECK(difficulty_level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED')),
                               duration_weeks   int4 NOT NULL CHECK(duration_weeks > 0),
                               visibility_type  varchar(20) NOT NULL CHECK(visibility_type IN ('PUBLIC', 'PRIVATE', 'SHARED')),
                               created_at       timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
                               updated_at       timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
                               PRIMARY KEY (ID));

CREATE TABLE workout_template_exercise (
                                           ID                  SERIAL NOT NULL,
                                           workout_templateID  int4 NOT NULL,
                                           exercise_templateID int4 NOT NULL,
                                           order_in_workout    int4 NOT NULL,
                                           planned_sets        int4 NOT NULL CHECK(planned_sets > 0),
                                           planned_reps        int4 NOT NULL CHECK(planned_reps > 0),
                                           planned_weight      numeric(6, 2) NOT NULL CHECK(planned_weight > 0),
                                           notes               text,
                                           PRIMARY KEY (ID),
                                           CONSTRAINT workout_template_exercise_unique_order
                                               UNIQUE (workout_templateID, order_in_workout));

CREATE TABLE trainee_info (
                              userID         int4 NOT NULL,
                              trainer_userID int4,
                              exercise_unit  varchar(10) NOT NULL CHECK(exercise_unit IN ('KG', 'LB')),
                              PRIMARY KEY (userID));

CREATE TABLE trainer_info (
                              userID         int4 NOT NULL,
                              specialization varchar(255),
                              bio            text,
                              hire_date      date NOT NULL,
                              is_active      bool NOT NULL,
                              PRIMARY KEY (userID));

CREATE TABLE "user" (
                        ID            SERIAL NOT NULL,
                        gymID         int4 NOT NULL,
                        user_type     varchar(20) NOT NULL CHECK(user_type IN ('ADMIN', 'TRAINER', 'TRAINEE')),
                        first_name    varchar(50) NOT NULL,
                        middle_name   varchar(50),
                        last_name     varchar(100) NOT NULL,
                        date_of_birth date NOT NULL,
                        sex           varchar(10) NOT NULL CHECK(sex in ('MALE', 'FEMALE')),
                        PRIMARY KEY (ID));

CREATE TABLE admin_info (
                            userID    int4 NOT NULL,
                            hire_date date NOT NULL,
                            is_active bool NOT NULL,
                            PRIMARY KEY (userID));

CREATE TABLE login_credential (
                                  userID        int4 NOT NULL,
                                  email         varchar(255) NOT NULL UNIQUE,
                                  password_hash varchar(255) NOT NULL,
                                  PRIMARY KEY (userID));

CREATE TABLE user_phone_number (
                                   userID         int4 NOT NULL,
                                   phone_number   varchar(15) NOT NULL,
                                   country_prefix varchar(4) NOT NULL,
                                   is_primary     bool DEFAULT 'False' NOT NULL,
                                   PRIMARY KEY (userID, phone_number));

CREATE TABLE address (
                         gymID            int4 NOT NULL,
                         country          varchar(100) NOT NULL,
                         post_code        varchar(9) NOT NULL,
                         city             varchar(100) NOT NULL,
                         street           varchar(100) NOT NULL,
                         street_number    varchar(16) NOT NULL,
                         apartment_number varchar(16) NOT NULL,
                         PRIMARY KEY (gymID));

CREATE TABLE membership (
                            ID                    SERIAL NOT NULL,
                            trainee_infoID        int4 NOT NULL,
                            membership_status     varchar(20) NOT NULL CHECK(membership_status IN ('ACTIVE', 'PAUSED', 'EXPIRED')),
                            membership_start_date date NOT NULL,
                            membership_end_date   date,
                            PRIMARY KEY (ID),
                            CONSTRAINT membership_dates_order_chk
                                CHECK (membership_end_date IS NULL OR membership_end_date >= membership_start_date));

CREATE TABLE training_plan_role (
                                    ID              SERIAL NOT NULL,
                                    training_planID int4 NOT NULL,
                                    userID          int4 NOT NULL,
                                    role            varchar(20) NOT NULL CHECK(role IN ('OWNER', 'EDITOR', 'VIEWER')),
                                    granted_at      timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                    PRIMARY KEY (ID),
                                    CONSTRAINT training_plan_role_unique
                                        UNIQUE (training_planID, userID));

CREATE TABLE user_training_plan (
                                    ID              SERIAL NOT NULL,
                                    training_planID int4 NOT NULL,
                                    userID          int4 NOT NULL,
                                    start_date      date NOT NULL,
                                    end_date        date,
                                    status          varchar(20) NOT NULL CHECK(status IN ('ACTIVE', 'PAUSED', 'COMPLETED', 'CANCELLED')),
                                    plan_title      varchar(255),
                                    notes           text,
                                    PRIMARY KEY (ID),
                                    CONSTRAINT user_training_plan_unique
                                        UNIQUE (userID, training_planID));

CREATE TABLE user_workout_exercise (
                                       ID                  SERIAL NOT NULL,
                                       workout_sessionID   int4 NOT NULL,
                                       exercise_templateID int4 NOT NULL,
                                       order_in_workout    int4 NOT NULL,
                                       planned_sets        int4 NOT NULL,
                                       planned_reps        int4 NOT NULL,
                                       planned_weight      numeric(6, 2),
                                       notes               text,
                                       PRIMARY KEY (ID),
                                       CONSTRAINT user_workout_exercise_unique_order
                                           UNIQUE (workout_sessionID, order_in_workout));

CREATE TABLE opening_hours (
                               gymID       int4 NOT NULL,
                               day_of_week int4 NOT NULL CHECK(day_of_week BETWEEN 0 AND 6),
                               opens_at    time NOT NULL,
                               closes_at   time NOT NULL,
                               PRIMARY KEY (gymID, day_of_week),
                               CONSTRAINT opening_hours_time_order_chk
                                   CHECK (closes_at > opens_at));

CREATE TABLE gym_phone_number (
                                  gymID          int4 NOT NULL,
                                  phone_number   varchar(15) NOT NULL,
                                  country_prefix varchar(4) NOT NULL,
                                  is_primary     bool NOT NULL,
                                  PRIMARY KEY (gymID, phone_number),
                                  CONSTRAINT primary_phone_unique_per_gym
                                      UNIQUE (is_primary, gymID));

CREATE TABLE body_metrics (
                              userID int4 NOT NULL,
                              "date" date NOT NULL,
                              weight numeric(6, 2) NOT NULL,
                              height int4 NOT NULL,
                              PRIMARY KEY (userID, "date"));

-- ======================================================================================
-- 3. KLUCZE OBCE
-- ======================================================================================

ALTER TABLE exercise_instance_set
    ADD CONSTRAINT fk_exercise_instance_set_instance
        FOREIGN KEY (exercise_instanceID) REFERENCES exercise_instance (ID);

ALTER TABLE workout_template_day
    ADD CONSTRAINT fk_workout_template_day_training_plan
        FOREIGN KEY (training_planID) REFERENCES training_plan (ID);

ALTER TABLE workout_template_exercise
    ADD CONSTRAINT fk_workout_template_exercise_day
        FOREIGN KEY (workout_templateID) REFERENCES workout_template_day (ID);

ALTER TABLE workout_template_exercise
    ADD CONSTRAINT fk_workout_template_exercise_template
        FOREIGN KEY (exercise_templateID) REFERENCES exercise_template (ID);

ALTER TABLE trainer_info
    ADD CONSTRAINT fk_trainer_info_user
        FOREIGN KEY (userID) REFERENCES "user" (ID);

ALTER TABLE admin_info
    ADD CONSTRAINT fk_admin_info_user
        FOREIGN KEY (userID) REFERENCES "user" (ID);

ALTER TABLE "user"
    ADD CONSTRAINT fk_user_gym
        FOREIGN KEY (gymID) REFERENCES gym (ID);

ALTER TABLE goal
    ADD CONSTRAINT fk_goal_user
        FOREIGN KEY (userID) REFERENCES "user" (ID);

ALTER TABLE login_credential
    ADD CONSTRAINT fk_login_credential_user
        FOREIGN KEY (userID) REFERENCES "user" (ID);

ALTER TABLE user_phone_number
    ADD CONSTRAINT fk_user_phone_number_user
        FOREIGN KEY (userID) REFERENCES "user" (ID);

ALTER TABLE address
    ADD CONSTRAINT fk_address_gym
        FOREIGN KEY (gymID) REFERENCES gym (ID);

ALTER TABLE membership
    ADD CONSTRAINT fk_membership_trainee
        FOREIGN KEY (trainee_infoID) REFERENCES trainee_info (userID);

ALTER TABLE training_plan_role
    ADD CONSTRAINT fk_training_plan_role_user
        FOREIGN KEY (userID) REFERENCES "user" (ID);

ALTER TABLE user_training_plan
    ADD CONSTRAINT fk_user_training_plan_user
        FOREIGN KEY (userID) REFERENCES "user" (ID);

ALTER TABLE user_training_plan
    ADD CONSTRAINT fk_user_training_plan_template
        FOREIGN KEY (training_planID) REFERENCES training_plan (ID);

ALTER TABLE exercise_instance
    ADD CONSTRAINT fk_exercise_instance_user_workout_exercise
        FOREIGN KEY (user_workout_exerciseID) REFERENCES user_workout_exercise (ID);

ALTER TABLE training_plan_role
    ADD CONSTRAINT fk_training_plan_role_plan
        FOREIGN KEY (training_planID) REFERENCES training_plan (ID);

ALTER TABLE user_workout_exercise
    ADD CONSTRAINT fk_user_workout_exercise_template
        FOREIGN KEY (exercise_templateID) REFERENCES exercise_template (ID);

ALTER TABLE opening_hours
    ADD CONSTRAINT fk_opening_hours_gym
        FOREIGN KEY (gymID) REFERENCES gym (ID);

ALTER TABLE trainee_info
    ADD CONSTRAINT fk_trainee_info_user
        FOREIGN KEY (userID) REFERENCES "user" (ID);

ALTER TABLE trainee_info
    ADD CONSTRAINT fk_trainee_info_trainer
        FOREIGN KEY (trainer_userID) REFERENCES "user" (ID);

ALTER TABLE goal
    ADD CONSTRAINT fk_goal_exercise_template
        FOREIGN KEY (exercise_templateID) REFERENCES exercise_template (ID);

ALTER TABLE gym_phone_number
    ADD CONSTRAINT fk_gym_phone_number_gym
        FOREIGN KEY (gymID) REFERENCES gym (ID);

ALTER TABLE workout_session
    ADD CONSTRAINT fk_workout_session_user_training_plan
        FOREIGN KEY (user_training_planID) REFERENCES user_training_plan (ID);

ALTER TABLE user_workout_exercise
    ADD CONSTRAINT fk_user_workout_exercise_session
        FOREIGN KEY (workout_sessionID) REFERENCES workout_session (ID);

ALTER TABLE body_metrics
    ADD CONSTRAINT fk_body_metrics_trainee
        FOREIGN KEY (userID) REFERENCES trainee_info (userID);

-- ======================================================================================
-- 4. INDEKSY
-- ======================================================================================

CREATE INDEX idx_goal_exercise_template ON goal (exercise_templateID);
CREATE INDEX idx_workout_template_day_plan ON workout_template_day (training_planID);
CREATE INDEX idx_workout_template_exercise_day ON workout_template_exercise (workout_templateID);
CREATE INDEX idx_trainee_info_trainer ON trainee_info (trainer_userID);
CREATE INDEX idx_user_workout_exercise_session ON user_workout_exercise (workout_sessionID);
CREATE INDEX idx_exercise_instance_workout_exercise ON exercise_instance (user_workout_exerciseID);
CREATE INDEX idx_training_plan_role_user ON training_plan_role (userID);

CREATE INDEX idx_exercise_template_muscle_group ON exercise_template (muscle_group);
CREATE INDEX idx_exercise_template_name ON exercise_template (name);
CREATE INDEX idx_trainer_info_specialization ON trainer_info (specialization);

CREATE INDEX idx_workout_session_plan_date ON workout_session (user_training_planID, session_date);
CREATE INDEX idx_workout_session_plan_completed ON workout_session (user_training_planID, completed);
CREATE INDEX idx_goal_user_status ON goal (userID, status);
CREATE INDEX idx_membership_trainee_status ON membership (trainee_infoID, membership_status);
CREATE INDEX idx_user_training_plan_user_status ON user_training_plan (userID, status);
CREATE INDEX idx_membership_dates ON membership (membership_start_date, membership_end_date);

CREATE UNIQUE INDEX unique_user_email ON login_credential (email);
CREATE UNIQUE INDEX unique_active_membership ON membership (trainee_infoID)
    WHERE membership_status = 'active';

