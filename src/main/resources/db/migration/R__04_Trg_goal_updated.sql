DROP TRIGGER IF EXISTS trg_goal_updated ON goal;
CREATE TRIGGER trg_goal_updated
    BEFORE UPDATE
    ON goal
    FOR EACH ROW
EXECUTE FUNCTION fn_update_timestamp();

