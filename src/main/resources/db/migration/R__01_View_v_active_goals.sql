CREATE OR REPLACE VIEW v_active_goals AS
SELECT g.ID                               AS goal_id,
       g.title,
       g.description,
       g.target_date,
       (g.target_date - CURRENT_DATE)     AS days_until_target,
       u.first_name || ' ' || u.last_name AS user_name,
       et.name                            AS exercise_name
FROM goal g
         JOIN "user" u ON g.userID = u.ID
         JOIN exercise_template et ON g.exercise_templateID = et.ID
WHERE g.status = 'IN_PROGRESS'
ORDER BY g.target_date ASC;

