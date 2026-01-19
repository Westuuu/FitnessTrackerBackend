-- V11: Cleanup Orphan trainer_info and Sync Sequences
DO $$
BEGIN
    -- 1. Remove orphan trainer_info records (those without a matching user)
    DELETE FROM fitnesstracker.trainer_info 
    WHERE userid NOT IN (SELECT id FROM fitnesstracker."user");

    -- 2. Remove orphan trainee_info records (just in case)
    DELETE FROM fitnesstracker.trainee_info 
    WHERE userid NOT IN (SELECT id FROM fitnesstracker."user");

    -- 3. Sync user_id_seq
    PERFORM setval('fitnesstracker.user_id_seq', COALESCE((SELECT MAX(id) FROM fitnesstracker."user"), 1) + 1, false);

    RAISE NOTICE 'Orphan records removed and user_id_seq synced.';
END $$;
