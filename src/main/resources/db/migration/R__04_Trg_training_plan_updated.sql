DROP TRIGGER IF EXISTS trg_training_plan_updated ON training_plan;
CREATE TRIGGER trg_training_plan_updated
    BEFORE UPDATE
    ON training_plan
    FOR EACH ROW
EXECUTE FUNCTION fn_update_timestamp();

