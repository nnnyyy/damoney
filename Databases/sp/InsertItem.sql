DELIMITER $$
CREATE DEFINER=`damoney`@`localhost` PROCEDURE `InsertItem`(_publisher varchar(30), _type int, _iconpath varchar(100), _name varchar(60), _price int, _regdate datetime, _enddate datetime )
BEGIN
	insert into items(`type`, publisher, name, iconpath, price, regdate, enddate) 
    values ( _type, _publisher, _name, _iconpath, _price, _regdate, _enddate );    
END$$
DELIMITER ;
