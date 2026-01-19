-- V8: Robust Deduplication with TRIM and Subqueries

-- 1. Unify Plan Assignments to the 'Master' Plan (Lowest ID with same TRIMMED name)
UPDATE user_training_plan utp
SET training_planid = (
    SELECT MIN(t_master.id)
    FROM training_plan t_master
    JOIN training_plan t_current ON TRIM(t_master.name) = TRIM(t_current.name)
    WHERE t_current.id = utp.training_planid
);

-- 2. Clean up duplicate User Assignments (Keep only MAX ID for same User + Plan)
-- Using a simpler DELETE ... NOT IN technique
DELETE FROM user_training_plan
WHERE status = 'ACTIVE'
AND id NOT IN (
    SELECT MAX(id)
    FROM user_training_plan
    WHERE status = 'ACTIVE'
    GROUP BY userid, training_planid
);

-- 3. Delete Duplicate Plan Definitions
-- (Keep only the one that is now the 'Master', i.e. Minimum ID)
DELETE FROM training_plan
WHERE id NOT IN (
    SELECT MIN(id)
    FROM training_plan
    GROUP BY TRIM(name)
);

-- 4. Re-assert Uniqueness on Plan Name (TRIMMED) just to be safe
-- We already have idx_training_plan_name_unique from V7? 
-- If V7 passed, that index exists on exact match.
-- Let's check if we can make it purely unique.
DROP INDEX IF EXISTS idx_training_plan_name_unique;
CREATE UNIQUE INDEX idx_training_plan_name_unique ON training_plan (name);
