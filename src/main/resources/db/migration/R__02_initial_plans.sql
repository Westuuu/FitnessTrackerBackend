-- Ready-to-use Training Plans
-- NOTE: We use subqueries to handle dynamic IDs

-- 1. Beginner Full Body (8 Weeks, Public)
INSERT INTO training_plan (name, description, difficulty_level, duration_weeks, visibility_type)
VALUES ('Beginner Full Body', 'Perfect for those starting their fitness journey. Focused on core compound movements.', 'BEGINNER', 8, 'PUBLIC')
ON CONFLICT DO NOTHING;

-- Workout Days for Beginner Full Body
DO $$
DECLARE
    plan_id BIGINT;
    day_id BIGINT;
BEGIN
    SELECT ID INTO plan_id FROM training_plan WHERE name = 'Beginner Full Body';
    
    IF plan_id IS NOT NULL THEN
        -- Day 1: Monday
        INSERT INTO workout_template_day (training_planID, name, day_of_week)
        VALUES (plan_id, 'Full Body A', 1) RETURNING ID INTO day_id;
        
        IF day_id IS NOT NULL THEN
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 1, 3, 5, 40 FROM exercise_template WHERE name = 'Barbell Back Squat';
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 2, 3, 8, 30 FROM exercise_template WHERE name = 'Barbell Bench Press';
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 3, 3, 10, 20 FROM exercise_template WHERE name = 'Bent Over Barbell Row';
        END IF;

        -- Day 3: Wednesday
        INSERT INTO workout_template_day (training_planID, name, day_of_week)
        VALUES (plan_id, 'Full Body B', 3) RETURNING ID INTO day_id;
        
        IF day_id IS NOT NULL THEN
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 1, 1, 5, 50 FROM exercise_template WHERE name = 'Conventional Deadlift';
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 2, 3, 10, 20 FROM exercise_template WHERE name = 'Overhead Barbell Press';
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 3, 3, 12, 30 FROM exercise_template WHERE name = 'Lat Pulldown (Wide Grip)';
        END IF;

        -- Day 5: Friday
        INSERT INTO workout_template_day (training_planID, name, day_of_week)
        VALUES (plan_id, 'Full Body C', 5) RETURNING ID INTO day_id;

        IF day_id IS NOT NULL THEN
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 1, 3, 10, 40 FROM exercise_template WHERE name = 'Leg Press';
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 2, 3, 10, 20 FROM exercise_template WHERE name = 'Incline Dumbbell Press';
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 3, 3, 12, 10 FROM exercise_template WHERE name = 'Seated Cable Row';
        END IF;
    END IF;
END $$;

-- 2. Push/Pull/Legs Split (12 Weeks, Public)
INSERT INTO training_plan (name, description, difficulty_level, duration_weeks, visibility_type)
VALUES ('Advanced PPL Split', 'A classic high-volume split for intermediate to advanced lifters.', 'ADVANCED', 12, 'PUBLIC')
ON CONFLICT DO NOTHING;

DO $$
DECLARE
    plan_id BIGINT;
    day_id BIGINT;
BEGIN
    SELECT ID INTO plan_id FROM training_plan WHERE name = 'Advanced PPL Split';
    
    IF plan_id IS NOT NULL THEN
        -- Push Day (Mon)
        INSERT INTO workout_template_day (training_planID, name, day_of_week)
        VALUES (plan_id, 'Push Day', 1) RETURNING ID INTO day_id;
        IF day_id IS NOT NULL THEN
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 1, 4, 6, 80 FROM exercise_template WHERE name = 'Barbell Bench Press';
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 2, 3, 10, 25 FROM exercise_template WHERE name = 'Lateral Raises';
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 3, 3, 12, 15 FROM exercise_template WHERE name = 'Tricep Pushdown (Cables)';
        END IF;

        -- Pull Day (Wed)
        INSERT INTO workout_template_day (training_planID, name, day_of_week)
        VALUES (plan_id, 'Pull Day', 3) RETURNING ID INTO day_id;
        IF day_id IS NOT NULL THEN
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 1, 3, 8, 60 FROM exercise_template WHERE name = 'Bent Over Barbell Row';
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 2, 3, 10, 1 FROM exercise_template WHERE name = 'Pull-ups (Wide Grip)';
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 3, 3, 12, 30 FROM exercise_template WHERE name = 'Barbell Bicep Curl';
        END IF;

        -- Legs Day (Fri)
        INSERT INTO workout_template_day (training_planID, name, day_of_week)
        VALUES (plan_id, 'Leg Day', 5) RETURNING ID INTO day_id;
        IF day_id IS NOT NULL THEN
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 1, 4, 8, 100 FROM exercise_template WHERE name = 'Barbell Back Squat';
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 2, 3, 10, 60 FROM exercise_template WHERE name = 'Romanian Deadlift';
            INSERT INTO workout_template_exercise (workout_templateID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            SELECT day_id, ID, 3, 3, 15, 40 FROM exercise_template WHERE name = 'Leg Extensions';
        END IF;
    END IF;
END $$;
