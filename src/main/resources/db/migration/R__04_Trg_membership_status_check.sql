CREATE OR REPLACE FUNCTION fn_membership_status_check() RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.membership_end_date < CURRENT_DATE THEN
        NEW.membership_status = 'EXPIRED';
    ELSIF NEW.membership_start_date > CURRENT_DATE THEN
        NEW.membership_status = 'PAUSED';
    ELSE
        NEW.membership_status = 'ACTIVE';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_membership_status_check ON membership;
CREATE TRIGGER trg_membership_status_check
    BEFORE INSERT OR UPDATE
    ON membership
    FOR EACH ROW
EXECUTE FUNCTION fn_membership_status_check();

