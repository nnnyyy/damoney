DROP procedure IF exists CreateAccount;
DELIMITER $$
CREATE procedure CreateAccount(_id varchar(20), _pwd varchar(20), _nick varchar(20), _status int)
BEGIN
	insert into account (id, password, nick, status) values (_id, password(_pwd), _nick, _status);
	insert into point ( id, point ) values (_id, 0 );
END$$
DELIMITER ;