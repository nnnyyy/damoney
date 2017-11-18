USE `damoneydb`;
DROP procedure IF EXISTS `BuyItem`;

DELIMITER $$
USE `damoneydb`$$
CREATE DEFINER=`damoney`@`localhost` PROCEDURE `BuyItem`(_id varchar(20), _itemsn int, out ret int)
BEGIN	
	DECLARE exit handler for SQLEXCEPTION
    BEGIN
		ROLLBACK;
		SET ret = -3;
    END;    
    START TRANSACTION;		
		-- 아이템 정보를 얻어옴
        SELECT @price:=price FROM items WHERE sn = _itemsn;
        SELECT @mypoint:=point FROM userinfo WHERE id = _id;
        IF @mypoint < @price THEN
			SET ret = -1;
		ELSE
			-- 돈 깎고 아이템 구입
            UPDATE userinfo SET point = point - @price WHERE id = _id;
            -- SN 발급
            SELECT @sn:=sn FROM itemsn WHERE idx = 0;
            SET SQL_SAFE_UPDATES=0;
            UPDATE itemsn SET sn = sn + 1 WHERE idx = 0;
            INSERT INTO useritems (id,listsn,itemsn) VALUES (_id, _itemsn, @sn);
            INSERT INTO buylog (id, buydate, itemsn) VALUES (_id, NOW(), @sn);            
            SET ret = 0;
        END IF;
	COMMIT;
END$$

DELIMITER ;

