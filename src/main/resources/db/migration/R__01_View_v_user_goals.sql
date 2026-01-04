CREATE OR REPLACE VIEW v_user_goals AS
SELECT g.ID     AS goal_id,
       g.title,
       g.description,
       g.target_date,
       g.status,
       g.target_value,
       0        AS current_value,
       g.userID AS user_id,
       et.name  AS exercise_name,
       0        AS progress_percentage
FROM goal g
         JOIN exercise_template et ON g.exercise_templateID = et.ID;

