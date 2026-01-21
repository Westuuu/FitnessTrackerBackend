-- Insert Gyms (if not exist)
INSERT INTO gym (name, email) 
VALUES ('Main Gym', 'main@gym.com'), ('Iron Paradise', 'iron@gym.com')
ON CONFLICT (email) DO NOTHING;

-- Insert Address for Main Gym
INSERT INTO address (gymID, country, city, street, street_number, apartment_number, post_code)
SELECT ID, 'USA', 'New York', 'Broadway', '10', '1', '10001' FROM gym WHERE name = 'Main Gym'
ON CONFLICT DO NOTHING;

-- Insert Users
INSERT INTO "user" (gymID, user_type, first_name, last_name, date_of_birth, sex)
SELECT ID, 'TRAINEE', 'Demo', 'Trainee', '2000-01-01'::date, 'MALE' FROM gym WHERE name = 'Main Gym'
UNION ALL
SELECT ID, 'TRAINER', 'Demo', 'Trainer', '1990-01-01'::date, 'FEMALE' FROM gym WHERE name = 'Main Gym'
UNION ALL
SELECT ID, 'ADMIN', 'Demo', 'Admin', '1980-01-01'::date, 'MALE' FROM gym WHERE name = 'Main Gym'
ON CONFLICT DO NOTHING;

-- Insert Login Credentials (with 'password' hashed)
INSERT INTO login_credential (userID, email, password_hash)
(SELECT ID, 'demo.trainee@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi' FROM "user" WHERE first_name = 'Demo' AND last_name = 'Trainee' LIMIT 1)
UNION ALL
(SELECT ID, 'demo.trainer@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi' FROM "user" WHERE first_name = 'Demo' AND last_name = 'Trainer' LIMIT 1)
UNION ALL
(SELECT ID, 'demo.admin@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi' FROM "user" WHERE first_name = 'Demo' AND last_name = 'Admin' LIMIT 1)
ON CONFLICT (email) DO UPDATE SET password_hash = EXCLUDED.password_hash;

-- Insert Info records
INSERT INTO trainee_info (userID, exercise_unit)
SELECT ID, 'KG' FROM "user" WHERE first_name = 'Demo' AND last_name = 'Trainee'
ON CONFLICT DO NOTHING;

INSERT INTO trainer_info (userID, hire_date, is_active)
SELECT ID, CURRENT_DATE, TRUE FROM "user" WHERE first_name = 'Demo' AND last_name = 'Trainer'
ON CONFLICT DO NOTHING;

INSERT INTO admin_info (userID, hire_date, is_active)
SELECT ID, CURRENT_DATE, TRUE FROM "user" WHERE first_name = 'Demo' AND last_name = 'Admin'
ON CONFLICT DO NOTHING;

-- Give an active membership to the demo trainee
INSERT INTO membership (trainee_infoID, membership_status, membership_start_date)
SELECT userid, 'ACTIVE', CURRENT_DATE 
FROM trainee_info 
WHERE userid = (SELECT ID FROM "user" WHERE first_name = 'Demo' AND last_name = 'Trainee' LIMIT 1)
AND NOT EXISTS (
    SELECT 1 FROM membership 
    WHERE trainee_infoID = (SELECT userid FROM trainee_info WHERE userid = (SELECT ID FROM "user" WHERE first_name = 'Demo' AND last_name = 'Trainee' LIMIT 1))
);

-- Insert some Exercise Templates
INSERT INTO exercise_template (name, muscle_group)
VALUES 
('Barbell Bench Press', 'CHEST'),
('Dumbbell Shoulder Press', 'SHOULDERS'),
('Squat', 'LEGS'),
('Deadlift', 'BACK')
ON CONFLICT (name) DO NOTHING;
