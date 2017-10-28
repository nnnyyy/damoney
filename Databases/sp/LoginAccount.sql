use damoneydb;
DROP procedure IF exists LoginAccount;
DELIMITER $$
CREATE procedure LoginAccount(_id varchar(20), _pwd varchar(20))
BEGIN
	select count(*) as cnt from account where id = _id and password = password(_pwd);
END$$
DELIMITER ;