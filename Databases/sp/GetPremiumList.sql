USE `damoneydb`;
DROP procedure IF EXISTS `GetPremiumList`;

DELIMITER $$
USE `damoneydb`$$
CREATE DEFINER=`damoney`@`localhost` PROCEDURE `GetPremiumList`(_id varchar(20))
BEGIN
	select * from adslist where type <> 3;
END$$

DELIMITER ;

