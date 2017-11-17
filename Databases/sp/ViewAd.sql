DROP procedure IF exists ViewAd;
DELIMITER $$
CREATE DEFINER=`damoney`@`localhost` PROCEDURE `ViewAd`(_id varchar(20), _adsn int, out ret int)
BEGIN	
	DECLARE exit handler for SQLEXCEPTION
    BEGIN
		ROLLBACK;
		SET ret = -3;
    END;
    
    START TRANSACTION;
		-- 이미 본 이력이 있는 광고인지 확인
		select * from adviewrecord where id = _id and adsn = _adsn;
        IF FOUND_ROWS() <> 0 THEN			
			SET ret = -1;
		ELSE
			-- 광고에서 가지고 있는 포인트만큼 추가 해준다.
            SET @nAddPoint = 0;
            select @nAddPoint:=reward from adslist where sn = _adsn;
            IF found_rows() = 0 THEN
				SET ret = -2;
			ELSE
				-- 포인트 추가 로직
                update point set point = point + @nAddPoint where id = _id;
                -- 광고 보기 로그에도 추가함
                insert into adviewrecord (id, adsn) values (_id, _adsn);
				SET ret = 0;
            END IF;			
        END IF;
	COMMIT;
END$$
DELIMITER ;