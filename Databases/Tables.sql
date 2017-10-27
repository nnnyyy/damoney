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
