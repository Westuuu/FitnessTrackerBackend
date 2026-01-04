CREATE OR REPLACE PROCEDURE sp_delete_user_account(p_user_id INT)
    LANGUAGE plpgsql
AS
$$
BEGIN
    DELETE FROM body_metrics WHERE userID = p_user_id;
    DELETE FROM goal WHERE userID = p_user_id;
    DELETE FROM user_phone_number WHERE userID = p_user_id;
    DELETE FROM login_credential WHERE userID = p_user_id;

    DELETE
    FROM user_workout_exercise
    WHERE workout_sessionID IN (SELECT ID
                                FROM workout_session
                                WHERE user_training_planID IN
                                      (SELECT ID FROM user_training_plan WHERE userID = p_user_id));
    DELETE
    FROM workout_session
    WHERE user_training_planID IN (SELECT ID FROM user_training_plan WHERE userID = p_user_id);
    DELETE FROM user_training_plan WHERE userID = p_user_id;

    DELETE FROM trainee_info WHERE userID = p_user_id;
    DELETE FROM trainer_info WHERE userID = p_user_id;
    DELETE FROM admin_info WHERE userID = p_user_id;

    DELETE FROM "user" WHERE ID = p_user_id;
END;
$$;

