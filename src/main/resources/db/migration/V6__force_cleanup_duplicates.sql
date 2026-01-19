-- Force cleanup of duplicates (V6)
-- First drop the index if it somehow exists partially
DROP INDEX IF EXISTS idx_unique_active_user_plan;

-- Delete duplicate active plans, keeping only the most recent one (highest ID)
DELETE FROM user_training_plan a 
USING user_training_plan b
WHERE a.id < b.id
  AND a.userid = b.userid
  AND a.training_planid = b.training_planid
  AND a.status = 'ACTIVE'
  AND b.status = 'ACTIVE';

-- Re-create the unique index to prevent recurrence
CREATE UNIQUE INDEX idx_unique_active_user_plan ON user_training_plan (userid, training_planid) WHERE status = 'ACTIVE';
