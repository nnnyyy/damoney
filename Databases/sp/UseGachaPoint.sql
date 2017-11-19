
DELIMITER $$
CREATE DEFINER=`damoney`@`localhost` PROCEDURE `UseGachaPoint`(_id varchar(20), out ret int)
BEGIN	
	DECLARE exit handler for SQLEXCEPTION
    BEGIN
		ROLLBACK;
		SET ret = -3;
    END;
    
    START TRANSACTION;
		select @gacha_cnt:=gacha_cnt from userinfo;
        IF @gacha_cnt <= 0 THEN
			SET ret = -1;
        ELSE
			update userinfo set gacha_cnt = gacha_cnt - 1 where id = _id;
            SET ret = 0;
        END IF;
	COMMIT;
END$$
DELIMITER ;