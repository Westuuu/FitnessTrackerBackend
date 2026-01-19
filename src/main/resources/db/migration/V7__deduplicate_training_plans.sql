-- Migration V7: Deduplicate Training Plans by Name and Cleanup Assignments

-- 1. Update User Assignments to point to the 'Master' plan (lowest ID for same name)
UPDATE user_training_plan utp
SET training_planid = master.id
FROM training_plan current_plan
JOIN training_plan master ON current_plan.name = master.name AND master.id < current_plan.id
WHERE utp.training_planid = current_plan.id;

-- 2. Update Plan Roles to point to Master plan
UPDATE training_plan_role tpr
SET training_planid = master.id
FROM training_plan current_plan
JOIN training_plan master ON current_plan.name = master.name AND master.id < current_plan.id
WHERE tpr.training_planid = current_plan.id;

-- 3. Now we have duplicate assignments in user_training_plan (same user, same plan ID).
-- We must clean them up. Keep the one with the latest start_date or highest ID.
DELETE FROM user_training_plan a 
USING user_training_plan b
WHERE a.id < b.id 
  AND a.userid = b.userid 
  AND a.training_planid = b.training_planid; 
-- Note: This deletes duplicates REGARDLESS of status. If I have an ACTIVE and a CANCELLED one?
-- If I have ACTIVE and COMPLETED?
-- Let's be smart. If status differs, we might want to keep both?
-- No, the prompt implied active duplicates. 
-- But if I merge "Beginner Plan (Active)" and "Beginner Plan (Completed)" -> User has 2 entries for Master Plan.
-- That is actually FINE. A user can do a plan, finish it, and do it again.
-- HOWEVER, the Unique Constraint `idx_unique_active_user_plan` (from V6) prevents multiple ACTIVE ones.
-- So we only definitely need to delete duplicates if BOTH are ACTIVE.
-- Or better, if we have multiple ACTIVE, keep latest.

DELETE FROM user_training_plan a 
USING user_training_plan b
WHERE a.id < b.id 
  AND a.userid = b.userid 
  AND a.training_planid = b.training_planid
  AND a.status = 'ACTIVE' 
  AND b.status = 'ACTIVE';

-- 4. Delete the Duplicate Plans and their children (Templates)
-- First, identify the duplicate IDs
CREATE TEMP TABLE duplicate_plans AS
SELECT t1.id 
FROM training_plan t1
JOIN training_plan t2 ON t1.name = t2.name AND t1.id > t2.id;

-- Delete Exercises of duplicate plans
DELETE FROM workout_template_exercise wte
USING workout_template_day wtd
WHERE wte.workout_templateid = wtd.id
AND wtd.training_planid IN (SELECT id FROM duplicate_plans);

-- Delete Days of duplicate plans
DELETE FROM workout_template_day 
WHERE training_planid IN (SELECT id FROM duplicate_plans);

-- Delete the Duplicate Plans themselves
DELETE FROM training_plan 
WHERE id IN (SELECT id FROM duplicate_plans);

-- Drop temporary table
DROP TABLE duplicate_plans;

-- 5. Add Unique Constraint on Training Plan Name to prevent future duplicates
CREATE UNIQUE INDEX idx_training_plan_name_unique ON training_plan (name);
