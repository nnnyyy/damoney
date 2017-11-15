DELIMITER $$
CREATE DEFINER=`damoney`@`localhost` PROCEDURE `GetPoint`(_id varchar(20))
BEGIN
	select id, point from point where id = _id;
END$$
DELIMITER ;
