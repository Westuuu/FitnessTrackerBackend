-- Initial Demo Data Seed
-- This file consolidates gyms, exercises, users, plans, and session history

-- 1. GYMS & ADDRESSES
INSERT INTO gym (name, email)
VALUES
('Main Gym', 'main@gym.com'),
('Iron Paradise', 'iron@gym.com'),
('Chicago Titan Gym', 'contact@chicagotitan.com'),
('Miami Power House', 'info@miamipower.com'),
('Seattle Seaside Fitness', 'seattle@fitness.com'),
('Austin Central Gym', 'austin@gym.com')
ON CONFLICT (email) DO NOTHING;

INSERT INTO address (gymID, country, city, street, street_number, apartment_number, post_code)
SELECT ID, 'USA', 'New York', 'Broadway', '10', '1', '10001' FROM gym WHERE name = 'Main Gym'
ON CONFLICT DO NOTHING;

INSERT INTO address (gymID, country, city, street, street_number, apartment_number, post_code)
SELECT ID, 'USA', 'Chicago', 'Michigan Ave', '101', '5', '60601' FROM gym WHERE name = 'Chicago Titan Gym'
ON CONFLICT DO NOTHING;

INSERT INTO address (gymID, country, city, street, street_number, apartment_number, post_code)
SELECT ID, 'USA', 'Miami', 'Ocean Dr', '22', '1A', '33139' FROM gym WHERE name = 'Miami Power House'
ON CONFLICT DO NOTHING;

INSERT INTO address (gymID, country, city, street, street_number, apartment_number, post_code)
SELECT ID, 'USA', 'Seattle', 'Lake Way', '5', '12', '98101' FROM gym WHERE name = 'Seattle Seaside Fitness'
ON CONFLICT DO NOTHING;

-- 2. EXERCISE TEMPLATES
INSERT INTO exercise_template (name, muscle_group, description, equipment_needed)
VALUES
-- CHEST
('Incline Dumbbell Press', 'CHEST', 'Upper chest focus with dumbbells', 'Dumbbells, Incline Bench'),
('Flat Dumbbell Press', 'CHEST', 'Overall chest development', 'Dumbbells, Flat Bench'),
('Chest Flyes (Dumbbell)', 'CHEST', 'Isolation for chest stretch', 'Dumbbells, Flat Bench'),
('Cable Crossover', 'CHEST', 'Upper/inner chest focus', 'Cable Machine'),
('Push-ups', 'CHEST', 'Bodyweight foundational move', 'None'),
('Dips (Chest focus)', 'CHEST', 'Lower chest and triceps', 'Dip Bars'),
('Machine Chest Press', 'CHEST', 'Stable chest pressing', 'Chest Press Machine'),
('Incline Barbell Press', 'CHEST', 'Upper chest barbell power', 'Barbell, Incline Bench'),
('Barbell Bench Press', 'CHEST', 'Overall chest power', 'Barbell'),
-- BACK
('Bent Over Barbell Row', 'BACK', 'Thick back builder', 'Barbell'),
('One Arm Dumbbell Row', 'BACK', 'Unilateral back work', 'Dumbbell'),
('Lat Pulldown (Wide Grip)', 'BACK', 'Back width focus', 'Lat Pulldown Machine'),
('Seated Cable Row', 'BACK', 'Mid back thickness', 'Cable Row Machine'),
('Pull-ups (Wide Grip)', 'BACK', 'Ultimate back width test', 'Pull-up Bar'),
('Chin-ups', 'BACK', 'Back and biceps', 'Pull-up Bar'),
('T-Bar Row', 'BACK', 'Mid back power', 'T-Bar Row Machine'),
('Face Pulls', 'BACK', 'Rear delts and upper back', 'Cable Machine, Rope Attachment'),
('Hyperextensions', 'BACK', 'Lower back isolation', 'Back Extension Bench'),
('Deadlift', 'BACK', 'Lower back and legs power', 'Barbell'),
-- LEGS
('Barbell Back Squat', 'LEGS', 'King of leg exercises', 'Barbell, Squat Rack'),
('Barbell Front Squat', 'LEGS', 'Quad and core focus', 'Barbell, Squat Rack'),
('Leg Press', 'LEGS', 'Heavy leg pressing', 'Leg Press Machine'),
('Leg Extensions', 'LEGS', 'Quad isolation', 'Leg Extension Machine'),
('Seated Leg Curl', 'LEGS', 'Hamstring isolation', 'Leg Curl Machine'),
('Lying Leg Curl', 'LEGS', 'Hamstring isolation', 'Lying Leg Curl Machine'),
('Walking Lunges', 'LEGS', 'Functional leg power', 'Dumbbells (Optional)'),
('Bulgarian Split Squat', 'LEGS', 'Uni-lateral leg strength', 'Dumbbells, Bench'),
('Romanian Deadlift', 'LEGS', 'Glutes and hamstrings', 'Barbell'),
('Calf Raise (Standing)', 'LEGS', 'Calf development', 'Calf Raise Machine'),
('Calf Raise (Seated)', 'LEGS', 'Calf development', 'Seated Calf Machine'),
('Squat', 'LEGS', 'Bodyweight or barbell squat', 'Squat Rack (Optional)'),
-- SHOULDERS
('Overhead Barbell Press', 'SHOULDERS', 'Strict shoulder power', 'Barbell'),
('Seated Dumbbell Press', 'SHOULDERS', 'Shoulder stability and size', 'Dumbbells, Bench'),
('Lateral Raises', 'SHOULDERS', 'Side delt width', 'Dumbbells'),
('Front Raises', 'SHOULDERS', 'Front delt focus', 'Dumbbells'),
('Upright Row', 'SHOULDERS', 'Traps and side delts', 'Barbell or EZ Bar'),
('Arnold Press', 'SHOULDERS', 'Full shoulder rotation', 'Dumbbells'),
('Rear Delt Flyes', 'SHOULDERS', 'Rear delt isolation', 'Dumbbells or Machine'),
('Dumbbell Shoulder Press', 'SHOULDERS', 'Total shoulder development', 'Dumbbells'),
-- ARMS
('Barbell Bicep Curl', 'ARMS', 'Mass builder for biceps', 'Barbell'),
('Dumbbell Hammer Curl', 'ARMS', 'Biceps and brachialis', 'Dumbbells'),
('Preacher Curl', 'ARMS', 'Bicep peak isolation', 'EZ Bar, Preacher Bench'),
('Concentration Curl', 'ARMS', 'Bicep isolation', 'Dumbbell'),
('Tricep Pushdown (Cables)', 'ARMS', 'Tricep isolation', 'Cable Machine'),
('Skull Crushers', 'ARMS', 'Tricep mass builder', 'EZ Bar'),
('Overhead Tricep Extension', 'ARMS', 'Long head tricep focus', 'Dumbbell or Cable'),
('Dips (Triceps focus)', 'ARMS', 'Power move for triceps', 'Dip Bars'),
-- CORE
('Plank', 'CORE', 'Isometric core stability', 'None'),
('Hanging Leg Raise', 'CORE', 'Lower ab focus', 'Pull-up Bar'),
('Crunches', 'CORE', 'Abdominal isolation', 'None'),
('Russian Twists', 'CORE', 'Oblique focus', 'Dumbbell or Medicine Ball'),
('Cable Crunch', 'CORE', 'Weighted ab work', 'Cable Machine'),
-- FULL BODY
('Conventional Deadlift', 'FULL_BODY', 'The ultimate posterior chain move', 'Barbell'),
('Sumo Deadlift', 'FULL_BODY', 'Hips and legs focus', 'Barbell'),
('Clean and Press', 'FULL_BODY', 'Explosive power', 'Barbell'),
('Burpees', 'FULL_BODY', 'High intensity conditioning', 'None')
ON CONFLICT (name) DO NOTHING;

-- 3. TRAINING PLANS & WORKOUT DAYS
INSERT INTO training_plan (name, description, difficulty_level, duration_weeks, visibility_type)
VALUES ('Beginner Full Body', 'Perfect for those starting their fitness journey. Focused on core compound movements.', 'BEGINNER', 8, 'PUBLIC')
ON CONFLICT DO NOTHING;

DO $$
DECLARE
    plan_id BIGINT;
    day_id BIGINT;
BEGIN
    SELECT ID INTO plan_id FROM training_plan WHERE name = 'Beginner Full Body';
    IF plan_id IS NOT NULL THEN
        -- Day 1: Full Body A
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
    END IF;
END $$;

INSERT INTO training_plan (name, description, difficulty_level, duration_weeks, visibility_type)
VALUES ('Advanced PPL Split', 'A classic high-volume split for intermediate to advanced lifters.', 'ADVANCED', 12, 'PUBLIC')
ON CONFLICT DO NOTHING;

-- 4. USERS & CREDENTIALS
INSERT INTO "user" (gymID, user_type, first_name, last_name, date_of_birth, sex)
SELECT ID, 'TRAINEE', 'Demo', 'Trainee', '2000-01-01'::date, 'MALE' FROM gym WHERE name = 'Main Gym'
UNION ALL
SELECT ID, 'TRAINER', 'Demo', 'Trainer', '1990-01-01'::date, 'FEMALE' FROM gym WHERE name = 'Main Gym'
UNION ALL
SELECT ID, 'ADMIN', 'Demo', 'Admin', '1980-01-01'::date, 'MALE' FROM gym WHERE name = 'Main Gym'
ON CONFLICT DO NOTHING;

INSERT INTO login_credential (userID, email, password_hash)
(SELECT ID, 'demo.trainee@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi' FROM "user" WHERE first_name = 'Demo' AND last_name = 'Trainee' LIMIT 1)
UNION ALL
(SELECT ID, 'demo.trainer@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi' FROM "user" WHERE first_name = 'Demo' AND last_name = 'Trainer' LIMIT 1)
UNION ALL
(SELECT ID, 'demo.admin@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi' FROM "user" WHERE first_name = 'Demo' AND last_name = 'Admin' LIMIT 1)
ON CONFLICT (email) DO UPDATE SET password_hash = EXCLUDED.password_hash;

INSERT INTO trainee_info (userID, exercise_unit)
SELECT ID, 'KG' FROM "user" WHERE first_name = 'Demo' AND last_name = 'Trainee'
ON CONFLICT DO NOTHING;

INSERT INTO trainer_info (userID, hire_date, is_active)
SELECT ID, CURRENT_DATE, TRUE FROM "user" WHERE first_name = 'Demo' AND last_name = 'Trainer'
ON CONFLICT DO NOTHING;

INSERT INTO admin_info (userID, hire_date, is_active)
SELECT ID, CURRENT_DATE, TRUE FROM "user" WHERE first_name = 'Demo' AND last_name = 'Admin'
ON CONFLICT DO NOTHING;

-- 5. MEMBERSHIPS & ASSIGNMENTS
INSERT INTO membership (trainee_infoID, membership_status, membership_start_date)
SELECT userid, 'ACTIVE', CURRENT_DATE 
FROM trainee_info 
WHERE userid = (SELECT ID FROM "user" WHERE first_name = 'Demo' AND last_name = 'Trainee' LIMIT 1)
ON CONFLICT DO NOTHING;

INSERT INTO user_training_plan (training_planID, userID, start_date, status, plan_title)
SELECT 
    (SELECT ID FROM training_plan WHERE name = 'Beginner Full Body' LIMIT 1),
    (SELECT ID FROM "user" WHERE first_name = 'Demo' AND last_name = 'Trainee' LIMIT 1),
    CURRENT_DATE - INTERVAL '14 days',
    'ACTIVE',
    'My Beginner Body'
ON CONFLICT DO NOTHING;

-- 6. GOALS & SESSIONS
DO $$
DECLARE
    trainee_id BIGINT;
    user_plan_id BIGINT;
    session_id BIGINT;
    workout_exercise_id BIGINT;
    exercise_instance_id BIGINT;
    squat_template_id BIGINT;
    bench_template_id BIGINT;
    row_template_id BIGINT;
BEGIN
    SELECT ID INTO trainee_id FROM "user" WHERE first_name = 'Demo' AND last_name = 'Trainee' LIMIT 1;
    SELECT utp.ID INTO user_plan_id 
    FROM user_training_plan utp
    JOIN training_plan tp ON utp.training_planID = tp.ID
    WHERE utp.userID = trainee_id AND tp.name = 'Beginner Full Body'
    LIMIT 1;
    
    SELECT ID INTO squat_template_id FROM exercise_template WHERE name = 'Barbell Back Squat' LIMIT 1;
    SELECT ID INTO bench_template_id FROM exercise_template WHERE name = 'Barbell Bench Press' LIMIT 1;
    SELECT ID INTO row_template_id FROM exercise_template WHERE name = 'Bent Over Barbell Row' LIMIT 1;

    IF trainee_id IS NOT NULL AND user_plan_id IS NOT NULL THEN
        -- Goals
        INSERT INTO goal (userID, exercise_templateID, title, target_value, current_value, start_date, target_date, status)
        VALUES 
        (trainee_id, squat_template_id, 'Squat 100kg', 100.00, 60.00, CURRENT_DATE - INTERVAL '30 days', CURRENT_DATE + INTERVAL '60 days', 'IN_PROGRESS'),
        (trainee_id, bench_template_id, 'Bench Press 80kg', 80.00, 50.00, CURRENT_DATE - INTERVAL '30 days', CURRENT_DATE + INTERVAL '60 days', 'IN_PROGRESS')
        ON CONFLICT DO NOTHING;

        -- Session History
        INSERT INTO workout_session (user_training_planID, session_date, start_time, end_time, notes, completed)
        VALUES (user_plan_id, CURRENT_DATE - 2, '18:00:00', '19:00:00', 'Felt strong today.', TRUE)
        RETURNING ID INTO session_id;

        IF session_id IS NOT NULL THEN
            INSERT INTO user_workout_exercise (workout_sessionID, exercise_templateID, order_in_workout, planned_sets, planned_reps, planned_weight)
            VALUES (session_id, squat_template_id, 1, 3, 5, 50.00) RETURNING ID INTO workout_exercise_id;
            INSERT INTO exercise_instance (user_workout_exerciseID, order_in_workout, completed)
            VALUES (workout_exercise_id, 1, TRUE) RETURNING ID INTO exercise_instance_id;
            INSERT INTO exercise_instance_set (exercise_instanceID, set_number, reps, weight, completed)
            VALUES (exercise_instance_id, 1, 5, 40, TRUE), (exercise_instance_id, 2, 5, 45, TRUE), (exercise_instance_id, 3, 5, 50, TRUE);
        END IF;
    END IF;
END $$;
