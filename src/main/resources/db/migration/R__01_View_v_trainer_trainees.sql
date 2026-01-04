CREATE OR REPLACE VIEW v_trainer_trainees AS
SELECT t.userID             AS trainee_id,
       u.first_name,
       u.last_name,
       lc.email,
       t.trainer_userID     AS trainer_id,
       utp.plan_title       AS active_plan_name,
       m.membership_status,
       MAX(ws.session_date) AS last_session_date
FROM trainee_info t
         JOIN "user" u ON t.userID = u.ID
         JOIN login_credential lc ON u.ID = lc.userID
         LEFT JOIN user_training_plan utp ON u.ID = utp.userID AND utp.status = 'ACTIVE'
         LEFT JOIN membership m ON t.userID = m.trainee_infoID AND m.membership_status = 'ACTIVE'
         LEFT JOIN workout_session ws ON utp.ID = ws.user_training_planID
WHERE t.trainer_userID IS NOT NULL
GROUP BY t.userID, u.first_name, u.last_name, lc.email, t.trainer_userID, utp.plan_title, m.membership_status;

