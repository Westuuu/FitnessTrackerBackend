CREATE OR REPLACE PROCEDURE sp_duplicate_training_plan(p_original_plan_id INT, p_new_owner_id INT)
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_new_plan_id  INT;
    v_old_day      RECORD;
    v_new_day_id   INT;
    v_old_exercise RECORD;
BEGIN
    INSERT INTO training_plan (name, description, difficulty_level, duration_weeks, visibility_type)
    SELECT name || ' (Copy)', description, difficulty_level, duration_weeks, 'PRIVATE'
    FROM training_plan
    WHERE ID = p_original_plan_id
    RETURNING ID INTO v_new_plan_id;

    INSERT INTO training_plan_role (training_planID, userID, role)
    VALUES (v_new_plan_id, p_new_owner_id, 'OWNER');

    FOR v_old_day IN SELECT * FROM workout_template_day WHERE training_planID = p_original_plan_id
        LOOP
            INSERT INTO workout_template_day (training_planID, name, day_of_week, notes)
            VALUES (v_new_plan_id, v_old_day.name, v_old_day.day_of_week, v_old_day.notes)
            RETURNING ID INTO v_new_day_id;

            FOR v_old_exercise IN SELECT * FROM workout_template_exercise WHERE workout_templateID = v_old_day.ID
                LOOP
                    INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout,
                                                           planned_sets, planned_reps, planned_weight, notes)
                    VALUES (v_new_day_id, v_old_exercise.exercise_templateID, v_old_exercise.order_in_workout,
                            v_old_exercise.planned_sets, v_old_exercise.planned_reps, v_old_exercise.planned_weight,
                            v_old_exercise.notes);
                END LOOP;
        END LOOP;
END;
$$;

