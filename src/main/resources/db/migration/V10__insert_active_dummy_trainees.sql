-- V10: Insert Active Dummy Trainees for Testing Autocomplete

DO $$
DECLARE
    new_user_id BIGINT;
    i INT;
    first_names TEXT[] := ARRAY['Liam', 'Emma', 'Noah', 'Olivia', 'William', 'Ava', 'James', 'Isabella', 'Oliver', 'Sophia'];
    last_names TEXT[] := ARRAY['Smith', 'Johnson', 'Williams', 'Brown', 'Jones', 'Garcia', 'Miller', 'Davis', 'Rodriguez', 'Martinez'];
BEGIN
    -- 0. Cleanup Orphans and Sync Sequence
    DELETE FROM trainee_info WHERE userid NOT IN (SELECT id FROM "user");
    -- Add other potential orphans if needed
    DELETE FROM login_credential WHERE userid NOT IN (SELECT id FROM "user");
    
    PERFORM setval('user_id_seq', COALESCE((SELECT MAX(id) FROM "user"), 1) + 1, false);

    FOR i IN 1..10 LOOP
        -- 1. Create User (No email column here)
        INSERT INTO "user" (first_name, last_name, user_type, gymid, date_of_birth, sex)
        VALUES (
            first_names[i], 
            last_names[i], 
            'TRAINEE', 
            1, -- Assuming Gym 1 is the main gym
            '1990-01-01', -- Added required DOB
            'MALE'       -- Added required Sex
        )
        RETURNING id INTO new_user_id;

        -- 2. Create Login Credentials (email goes here, password_hash)
        INSERT INTO login_credential (userid, email, password_hash)
        VALUES (
            new_user_id, 
            lower(first_names[i]) || '.' || lower(last_names[i]) || i || '@example.com',
            '$2a$10$xn3LI/AjqicFYZFruO4hqfo4op2.Fjeeb8cn7tGptDIqqV9.mS.i' -- 'password' hash
        ) ON CONFLICT (userid) DO NOTHING;

        -- 3. Create Trainee Info (userid is PK, exercise_unit required)
        INSERT INTO trainee_info (userid, exercise_unit)
        VALUES (new_user_id, 'KG')
        ON CONFLICT (userid) DO NOTHING;
        
        -- 4. Create Membership (Active)
        INSERT INTO membership (trainee_infoID, membership_status, membership_start_date, membership_end_date)
        VALUES (new_user_id, 'ACTIVE', CURRENT_DATE, CURRENT_DATE + INTERVAL '1 year');

    END LOOP;
END $$;
