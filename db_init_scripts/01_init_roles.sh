#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL

    DO \$\$
    BEGIN
      IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'admin_role') THEN CREATE ROLE admin_role; END IF;
      IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'trainer_role') THEN CREATE ROLE trainer_role; END IF;
      IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'trainee_role') THEN CREATE ROLE trainee_role; END IF;
    END
    \$\$;

    -- Tworzenie Użytkowników
    DO \$\$
    BEGIN
      IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = '$POSTGRES_USER_ADMIN') THEN
        CREATE USER "$POSTGRES_USER_ADMIN" WITH PASSWORD '$POSTGRES_PASSWORD_ADMIN';
      END IF;
      IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = '$POSTGRES_USER_TRAINER') THEN
        CREATE USER "$POSTGRES_USER_TRAINER" WITH PASSWORD '$POSTGRES_PASSWORD_TRAINER';
      END IF;
      IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = '$POSTGRES_USER_TRAINEE') THEN
        CREATE USER "$POSTGRES_USER_TRAINEE" WITH PASSWORD '$POSTGRES_PASSWORD_TRAINEE';
      END IF;
    END
    \$\$;

    -- Przypisywanie ról do użytkowników
    GRANT admin_role TO "$POSTGRES_USER_ADMIN";
    GRANT trainer_role TO "$POSTGRES_USER_TRAINER";
    GRANT trainee_role TO "$POSTGRES_USER_TRAINEE";

    CREATE SCHEMA IF NOT EXISTS fitnesstracker;

    GRANT ALL PRIVILEGES ON SCHEMA fitnesstracker TO "$POSTGRES_USER";

    GRANT USAGE ON SCHEMA fitnesstracker TO admin_role, trainer_role, trainee_role;

    ALTER ROLE "$POSTGRES_USER_ADMIN" SET search_path TO fitnesstracker;
    ALTER ROLE "$POSTGRES_USER_TRAINER" SET search_path TO fitnesstracker;
    ALTER ROLE "$POSTGRES_USER_TRAINEE" SET search_path TO fitnesstracker;
    ALTER ROLE "$POSTGRES_USER" SET search_path TO fitnesstracker;

    GRANT CONNECT ON DATABASE "$POSTGRES_DB" TO "$POSTGRES_USER_ADMIN", "$POSTGRES_USER_TRAINER", "$POSTGRES_USER_TRAINEE";

EOSQL