-- ======================================================================================
-- ROLE I UŻYTKOWNICY
-- ======================================================================================

DO
$$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'admin_role') THEN
            CREATE ROLE admin_role;
        END IF;
        IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'trainer_role') THEN
            CREATE ROLE trainer_role;
        END IF;
        IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'trainee_role') THEN
            CREATE ROLE trainee_role;
        END IF;
        IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'auth_role') THEN
            CREATE ROLE auth_role;
        END IF;
    END
$$;

DO
$$
    BEGIN
        -- Użytkownik ADMIN
        IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'db_admin') THEN
            CREATE USER db_admin WITH PASSWORD '${flyway_admin_password}';
        END IF;

        -- Użytkownik TRAINER
        IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'db_trainer') THEN
            CREATE USER db_trainer WITH PASSWORD '${flyway_trainer_password}';
        END IF;

        -- Użytkownik TRAINEE
        IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'db_trainee') THEN
            CREATE USER db_trainee WITH PASSWORD '${flyway_trainee_password}';
        END IF;

        -- Rola autoryzacji
        IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'db_auth') THEN
            CREATE USER db_auth WITH PASSWORD '${flyway_auth_password}';
        END IF;

    END
$$;

GRANT admin_role TO db_admin;
GRANT trainer_role TO db_trainer;
GRANT trainee_role TO db_trainee;
GRANT auth_role TO db_auth;

GRANT CONNECT ON DATABASE fitness_tracker TO db_admin, db_trainer, db_trainee, db_auth;

GRANT USAGE ON SCHEMA fitnesstracker TO admin_role, trainer_role, trainee_role, db_auth;

-- Automatyczne ustawienie ścieżki
ALTER ROLE db_admin SET search_path TO fitnesstracker;
ALTER ROLE db_trainer SET search_path TO fitnesstracker;
ALTER ROLE db_trainee SET search_path TO fitnesstracker;
ALTER ROLE db_auth SET search_path TO fitnesstracker;



-- ======================================================================================
-- UPRAWNIENIA ADMIN_ROLE
-- ======================================================================================
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA fitnesstracker TO admin_role;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA fitnesstracker TO admin_role;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA fitnesstracker TO admin_role;
GRANT EXECUTE ON ALL PROCEDURES IN SCHEMA fitnesstracker TO admin_role;

ALTER DEFAULT PRIVILEGES IN SCHEMA fitnesstracker GRANT ALL ON TABLES TO admin_role;

-- ======================================================================================
-- UPRAWNIENIA TRAINER_ROLE
-- ======================================================================================
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA fitnesstracker TO trainer_role;

GRANT SELECT ON "user" TO trainer_role;
GRANT SELECT ON login_credential TO trainer_role;
GRANT SELECT, UPDATE ON trainer_info TO trainer_role;
GRANT SELECT, UPDATE ON trainee_info TO trainer_role;
GRANT SELECT ON user_phone_number TO trainer_role;
GRANT SELECT ON body_metrics TO trainer_role;

GRANT SELECT ON gym TO trainer_role;
GRANT SELECT ON address TO trainer_role;
GRANT SELECT ON opening_hours TO trainer_role;
GRANT SELECT ON gym_phone_number TO trainer_role;

GRANT SELECT ON membership TO trainer_role;
GRANT SELECT, INSERT, UPDATE ON exercise_template TO trainer_role;

GRANT SELECT, INSERT, UPDATE, DELETE ON training_plan TO trainer_role;
GRANT SELECT, INSERT, UPDATE, DELETE ON training_plan_role TO trainer_role;
GRANT SELECT, INSERT, UPDATE, DELETE ON workout_template_day TO trainer_role;
GRANT SELECT, INSERT, UPDATE, DELETE ON workout_template_exercise TO trainer_role;

GRANT SELECT, INSERT, UPDATE ON user_training_plan TO trainer_role;
GRANT SELECT ON workout_session TO trainer_role;
GRANT SELECT ON user_workout_exercise TO trainer_role;
GRANT SELECT ON exercise_instance TO trainer_role;
GRANT SELECT ON exercise_instance_set TO trainer_role;

GRANT SELECT ON goal TO trainer_role;

GRANT SELECT ON v_user_active_plans TO trainer_role;
GRANT SELECT ON v_user_goals TO trainer_role;
GRANT SELECT ON v_active_goals TO trainer_role;
GRANT SELECT ON v_workout_session_details TO trainer_role;
GRANT SELECT ON v_trainer_trainees TO trainer_role;

GRANT EXECUTE ON FUNCTION sp_generate_progress_report(INT) TO trainer_role;
GRANT EXECUTE ON PROCEDURE sp_duplicate_training_plan(INT, INT) TO trainer_role;


-- ======================================================================================
-- UPRAWNIENIA TRAINEE_ROLE
-- ======================================================================================
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA fitnesstracker TO trainee_role;

GRANT SELECT ON "user" TO trainee_role;
GRANT SELECT ON login_credential TO trainee_role;
GRANT SELECT ON trainer_info TO trainee_role;
GRANT SELECT, UPDATE ON trainee_info TO trainee_role;
GRANT SELECT, INSERT, UPDATE, DELETE ON user_phone_number TO trainee_role;
GRANT SELECT, INSERT, UPDATE, DELETE ON body_metrics TO trainee_role;

GRANT SELECT ON gym TO trainee_role;
GRANT SELECT ON address TO trainee_role;
GRANT SELECT ON opening_hours TO trainee_role;
GRANT SELECT ON gym_phone_number TO trainee_role;

GRANT SELECT ON membership TO trainee_role;
GRANT SELECT ON exercise_template TO trainee_role;

GRANT SELECT, INSERT, UPDATE, DELETE ON training_plan TO trainee_role;
GRANT SELECT, INSERT, UPDATE, DELETE ON training_plan_role TO trainee_role;
GRANT SELECT, INSERT, UPDATE, DELETE ON workout_template_day TO trainee_role;
GRANT SELECT, INSERT, UPDATE, DELETE ON workout_template_exercise TO trainee_role;

GRANT SELECT, INSERT, UPDATE, DELETE ON user_training_plan TO trainee_role;

GRANT SELECT, INSERT, UPDATE, DELETE ON workout_session TO trainee_role;
GRANT SELECT, INSERT, UPDATE, DELETE ON user_workout_exercise TO trainee_role;
GRANT SELECT, INSERT, UPDATE, DELETE ON exercise_instance TO trainee_role;
GRANT SELECT, INSERT, UPDATE, DELETE ON exercise_instance_set TO trainee_role;

GRANT SELECT, INSERT, UPDATE, DELETE ON goal TO trainee_role;

GRANT SELECT ON v_user_active_plans TO trainee_role;
GRANT SELECT ON v_user_goals TO trainee_role;
GRANT SELECT ON v_active_goals TO trainee_role;
GRANT SELECT ON v_workout_session_details TO trainee_role;
GRANT SELECT ON v_trainer_trainees TO trainee_role;

GRANT EXECUTE ON FUNCTION sp_generate_progress_report(INT) TO trainee_role;


-- ======================================================================================
-- UPRAWNIENIA AUTH_ROLE
-- ======================================================================================

GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA fitnesstracker TO auth_role;

GRANT EXECUTE ON FUNCTION fn_create_user_profile() TO auth_role;


GRANT SELECT ON login_credential TO auth_role;
GRANT SELECT ON "user" TO auth_role;
GRANT SELECT ON gym TO auth_role;
GRANT SELECT ON address TO auth_role;
GRANT SELECT ON gym_phone_number TO auth_role;
GRANT SELECT ON membership TO auth_role;
GRANT SELECT, INSERT ON trainee_info TO auth_role;
GRANT SELECT, INSERT ON trainer_info TO auth_role;
GRANT SELECT, INSERT ON admin_info TO auth_role;

GRANT INSERT ON "user" TO auth_role;
GRANT INSERT ON login_credential TO auth_role;


-- Force rerun
