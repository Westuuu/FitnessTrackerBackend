CREATE OR REPLACE VIEW v_user_active_plans AS
SELECT utp.ID                            AS plan_id,
       tp.name                           AS plan_name,
       tp.difficulty_level,
       utp.start_date,
       utp.end_date,
       utp.userID                        AS user_id,
       utp.status,
       (utp.end_date - CURRENT_DATE) / 7 AS weeks_remaining
FROM user_training_plan utp
         JOIN training_plan tp ON utp.training_planID = tp.ID
WHERE utp.status = 'ACTIVE';

