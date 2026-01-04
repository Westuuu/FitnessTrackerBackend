DROP TRIGGER IF EXISTS trg_workout_session_updated ON workout_session;
CREATE TRIGGER trg_workout_session_updated
    BEFORE UPDATE
    ON workout_session
    FOR EACH ROW
EXECUTE FUNCTION fn_update_timestamp();

