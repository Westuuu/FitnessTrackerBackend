CREATE OR REPLACE FUNCTION fn_create_user_profile() RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.user_type = 'TRAINEE' THEN
        INSERT INTO trainee_info (userID, exercise_unit) VALUES (NEW.ID, 'KG');
    ELSIF NEW.user_type = 'TRAINER' THEN
        INSERT INTO trainer_info (userID, hire_date, is_active) VALUES (NEW.ID, CURRENT_DATE, TRUE);
    ELSIF NEW.user_type = 'ADMIN' THEN
        INSERT INTO admin_info (userID, hire_date, is_active) VALUES (NEW.ID, CURRENT_DATE, TRUE);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_create_user_profile ON "user";
CREATE TRIGGER trg_create_user_profile
    AFTER INSERT
    ON "user"
    FOR EACH ROW
EXECUTE FUNCTION fn_create_user_profile();

