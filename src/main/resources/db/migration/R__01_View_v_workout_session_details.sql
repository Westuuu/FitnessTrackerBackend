CREATE OR REPLACE VIEW v_workout_session_details AS
SELECT ws.ID                                                  AS session_id,
       ws.session_date,
       ws.start_time,
       ws.end_time,
       EXTRACT(EPOCH FROM (ws.end_time - ws.start_time)) / 60 AS duration_minutes,
       tp.name                                                AS plan_name,
       ws.completed,
       COUNT(DISTINCT uwe.ID)                                 AS exercises_count
FROM workout_session ws
         JOIN user_training_plan utp ON ws.user_training_planID = utp.ID
         JOIN training_plan tp ON utp.training_planID = tp.ID
         LEFT JOIN user_workout_exercise uwe ON ws.ID = uwe.workout_sessionID
GROUP BY ws.ID, tp.name;

