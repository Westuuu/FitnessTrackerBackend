-- Clean up duplicate active plans, keeping only the most recent one
DELETE FROM user_training_plan a USING user_training_plan b
WHERE a.id < b.id
  AND a.userid = b.userid
  AND a.training_planid = b.training_planid
  AND a.status = 'ACTIVE'
  AND b.status = 'ACTIVE';

-- Add unique constraint to prevent future duplicates of the same plan being active
CREATE UNIQUE INDEX idx_unique_active_user_plan ON user_training_plan (userid, training_planid) WHERE status = 'ACTIVE';
