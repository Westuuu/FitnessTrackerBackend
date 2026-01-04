CREATE OR REPLACE FUNCTION sp_generate_progress_report(p_user_id INT)
    RETURNS JSON AS
$$
DECLARE
    result JSON;
BEGIN
    SELECT json_build_object(
                   'completed_sessions_last_month', (SELECT COUNT(*)
                                                     FROM workout_session ws
                                                              JOIN user_training_plan utp ON ws.user_training_planID = utp.ID
                                                     WHERE utp.userID = p_user_id
                                                       AND ws.completed = TRUE
                                                       AND ws.session_date >= CURRENT_DATE - INTERVAL '1 month'),
                   'achieved_goals', (SELECT COUNT(*) FROM goal WHERE userID = p_user_id AND status = 'ACHIEVED')
           )
    INTO result;
    RETURN result;
END;
$$ LANGUAGE plpgsql;

