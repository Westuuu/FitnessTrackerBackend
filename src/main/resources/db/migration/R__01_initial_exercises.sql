-- Comprehensive Exercise Templates
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

-- SHOULDERS
('Overhead Barbell Press', 'SHOULDERS', 'Strict shoulder power', 'Barbell'),
('Seated Dumbbell Press', 'SHOULDERS', 'Shoulder stability and size', 'Dumbbells, Bench'),
('Lateral Raises', 'SHOULDERS', 'Side delt width', 'Dumbbells'),
('Front Raises', 'SHOULDERS', 'Front delt focus', 'Dumbbells'),
('Upright Row', 'SHOULDERS', 'Traps and side delts', 'Barbell or EZ Bar'),
('Arnold Press', 'SHOULDERS', 'Full shoulder rotation', 'Dumbbells'),
('Rear Delt Flyes', 'SHOULDERS', 'Rear delt isolation', 'Dumbbells or Machine'),

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

-- Initial Gyms in Different Cities
INSERT INTO gym (name, email)
VALUES
('Chicago Titan Gym', 'contact@chicagotitan.com'),
('Miami Power House', 'info@miamipower.com'),
('Seattle Seaside Fitness', 'seattle@fitness.com'),
('Austin Central Gym', 'austin@gym.com')
ON CONFLICT (email) DO NOTHING;

-- Addresses for New Gyms
INSERT INTO address (gymID, country, city, street, street_number, apartment_number, post_code)
SELECT ID, 'USA', 'Chicago', 'Michigan Ave', '101', '5', '60601' FROM gym WHERE name = 'Chicago Titan Gym'
ON CONFLICT DO NOTHING;

INSERT INTO address (gymID, country, city, street, street_number, apartment_number, post_code)
SELECT ID, 'USA', 'Miami', 'Ocean Dr', '22', '1A', '33139' FROM gym WHERE name = 'Miami Power House'
ON CONFLICT DO NOTHING;

INSERT INTO address (gymID, country, city, street, street_number, apartment_number, post_code)
SELECT ID, 'USA', 'Seattle', 'Lake Way', '5', '12', '98101' FROM gym WHERE name = 'Seattle Seaside Fitness'
ON CONFLICT DO NOTHING;
