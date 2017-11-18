USE `damoneydb`;
DROP procedure IF EXISTS `CreateAccount`;

DELIMITER $$
USE `damoneydb`$$
CREATE DEFINER=`damoney`@`localhost` PROCEDURE `CreateAccount`(_id varchar(20), _pwd varchar(20), _nick varchar(20), _status int)
BEGIN
	insert into account (id, password, nick, status) values (_id, password(_pwd), _nick, _status);	
    insert into userinfo ( id, point, gacha_cnt ) values ( _id, 0, 0 );
END$$

DELIMITER ;

