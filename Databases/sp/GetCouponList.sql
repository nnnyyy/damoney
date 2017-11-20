DELIMITER $$
CREATE DEFINER=`damoney`@`localhost` PROCEDURE `GetCouponList`(_id varchar(20))
BEGIN
	select * from adslist left join adviewrecord on sn = adsn where id is null and type = 3;
END$$
DELIMITER ;