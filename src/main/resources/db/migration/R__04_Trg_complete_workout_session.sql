CREATE OR REPLACE FUNCTION fn_complete_workout_session() RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.end_time IS NOT NULL AND OLD.end_time IS NULL THEN
        NEW.completed = TRUE;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_complete_workout_session ON workout_session;
CREATE TRIGGER trg_complete_workout_session
    BEFORE UPDATE
    ON workout_session
    FOR EACH ROW
EXECUTE FUNCTION fn_complete_workout_session();

