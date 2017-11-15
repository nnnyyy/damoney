DELIMITER $$
CREATE DEFINER=`damoney`@`localhost` PROCEDURE `InsertAds`(_name varchar(40), _iconpath varchar(100), _type int, _reward int, _regdate datetime, _enddate datetime, _link varchar(1000), _desc varchar(500), _barcode int )
BEGIN
	insert into adslist (name, iconpath, type, reward, regdate, enddate, link, `desc`, barcode) 
    values ( _name, _iconpath, _type, _reward, _regdate, _enddate, _link, _desc, _barcode );    
END$$
DELIMITER ;
