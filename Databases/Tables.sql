use damoneydb;

create table account 
(
	id varchar(20) not null, -- 아이디
	password varchar(100) not null, -- 비번
	nick varchar(20) not null, -- 닉네임        
    status int default 1, -- 계정 상태
    primary key (id)
);

create index idx_account_id on account (id);


CREATE TABLE `point` (
  `id` varchar(20) NOT NULL,
  `point` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_point_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `adslist` (
  `sn` bigint NOT NULL auto_increment,
  `name` varchar(40) NOT NULL,
  `iconpath` varchar(100) NOT NULL,
  `type` int not null,
  `reward` int not null,
  `regdate` datetime not null,
  `enddate` datetime not null,
  `link` varchar(1000),
  `desc` varchar(500),
  `barcode` int,
  PRIMARY KEY (`sn`),
  KEY `idx_account_sn` (`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `adviewrecord` (
  `id` varchar(20) NOT NULL,
  `adsn` int(11) DEFAULT NULL,  
  KEY `idx_adviewrecord_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `buylog` (
  `id` varchar(20) NOT NULL,
  `buydate` datetime NOT NULL,
  `itemsn` int(11) DEFAULT NULL,  
  KEY `idx_buylog_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `items` (
  `sn` int NOT NULL auto_increment,
  `type` int,
  `publisher` varchar(30),
  `name` varchar(60),
  `iconpath` varchar(100) NOT NULL,
  `price` int,
  `regdate` datetime,
  `enddate` datetime,
  `desc` varchar(200),
  PRIMARY KEY (`sn`),
  KEY `idx_items_sn` (`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `userinfo` (
  `id` varchar(20) NOT NULL,
  `point` int(11) NOT NULL DEFAULT '0',
  `gacha_cnt` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_point_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `itemsn` (
  `idx` varchar(20) NOT NULL,
  `sn` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idx`),
  KEY `idx_userinfo_idx` (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `useritems` (
  `id` varchar(20) NOT NULL,
  `listsn` int(11) NOT NULL,
  `itemsn` bigint(20) NOT NULL,
  KEY `idx_useritems_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
