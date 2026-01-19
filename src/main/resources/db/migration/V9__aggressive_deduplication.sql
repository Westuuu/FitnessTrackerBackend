-- V9: Aggressive Deduplication for known problem plans (Beginner Full Body)

-- 1. Identify Master Plan for "Beginner Full Body" (using LIKE to catch variations)
-- We'll use a specific logic block to handle this safely.

DO $$
DECLARE
    master_beginner_id BIGINT;
    master_ppl_id BIGINT;
BEGIN
    -- A. Handle "Beginner Full Body"
    -- Find the ID of the "Master" (e.g. the one with the shortest clean name, or just min ID)
    SELECT ID INTO master_beginner_id 
    FROM training_plan 
    WHERE name LIKE 'Beginner Full Body%' 
    ORDER BY ID ASC 
    LIMIT 1;

    IF master_beginner_id IS NOT NULL THEN
        -- Reassign ALL users who have ANY 'Beginner Full Body%' plan to the Master ID
        UPDATE user_training_plan
        SET training_planid = master_beginner_id
        WHERE training_planid IN (
            SELECT ID FROM training_plan WHERE name LIKE 'Beginner Full Body%' AND ID != master_beginner_id
        );

        -- Delete the Duplicate Plans (the ones we just moved users FROM)
        -- (Ideally we should delete their sub-tables first: day, exercise...)
        -- Note: We rely on CASCADE delete if configured? 
        -- Checking V1: No ON DELETE CASCADE defined. We must delete manually.
        
        -- Delete sub-entities for duplicate plans
        DELETE FROM workout_template_exercise wte
        USING workout_template_day wtd
        JOIN training_plan tp ON wtd.training_planid = tp.id
        WHERE wte.workout_templateid = wtd.id
          AND tp.name LIKE 'Beginner Full Body%' 
          AND tp.id != master_beginner_id;

        DELETE FROM workout_template_day
        WHERE training_planid IN (
            SELECT ID FROM training_plan WHERE name LIKE 'Beginner Full Body%' AND ID != master_beginner_id
        );

        -- Delete the Duplicate Plans themselves
        DELETE FROM training_plan 
        WHERE name LIKE 'Beginner Full Body%' 
          AND ID != master_beginner_id;
    END IF;

    -- B. Handle "Advanced PPL Split" (Just in case)
    SELECT ID INTO master_ppl_id 
    FROM training_plan 
    WHERE name LIKE 'Advanced PPL Split%' 
    ORDER BY ID ASC 
    LIMIT 1;

    IF master_ppl_id IS NOT NULL THEN
        UPDATE user_training_plan
        SET training_planid = master_ppl_id
        WHERE training_planid IN (
            SELECT ID FROM training_plan WHERE name LIKE 'Advanced PPL Split%' AND ID != master_ppl_id
        );

         DELETE FROM workout_template_exercise wte
        USING workout_template_day wtd
        JOIN training_plan tp ON wtd.training_planid = tp.id
        WHERE wte.workout_templateid = wtd.id
          AND tp.name LIKE 'Advanced PPL Split%' 
          AND tp.id != master_ppl_id;

        DELETE FROM workout_template_day
        WHERE training_planid IN (
            SELECT ID FROM training_plan WHERE name LIKE 'Advanced PPL Split%' AND ID != master_ppl_id
        );

        DELETE FROM training_plan 
        WHERE name LIKE 'Advanced PPL Split%' 
          AND ID != master_ppl_id;
    END IF;

END $$;

-- 2. Final User Assignment Cleanup (Deduplicate rows in user_training_plan)
-- Now that valid assignments all point to the SAME ID (master_beginner_id), duplicates are strictly on (UserID, MasterID)
DELETE FROM user_training_plan
WHERE status = 'ACTIVE'
AND id NOT IN (
    SELECT MAX(id)
    FROM user_training_plan
    WHERE status = 'ACTIVE'
    GROUP BY userid, training_planid
);
