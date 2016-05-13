drop database book_ex;
use book_ex;

create table tbl_member (
	userid varchar(50) not null,
    userpw varchar(50) not null,
    username varchar(50) not null,
    email varchar(100),
    regdate timestamp default now(),
    updatedate timestamp default now(),
    primary key(userid)
);

create table tbl_board (
	bno int not null auto_increment,
    title varchar(200) not null,
    content text null,
    writer varchar(50) not null,
    regdate timestamp not null default now(),
    viewcnt int default 0,
    primary key (bno)
);

create table tbl_reply(
	rno int not null auto_increment,
    bno int not null default 0,
    replytext varchar(1000) not null,
    replyer varchar(50) not null,
    regdate timestamp not null default now(),
    updatedate timestamp not null default now(),
    primary key (rno)
);

alter table tbl_reply add constraint fk_board
foreign key (bno) references tbl_board(bno)
on delete cascade on update cascade; -- 본글의 글번호가 삭제되거나, 수정되면 관련 댓글도 삭제 및 수정(댓글의 bno 값)하라

alter table tbl_board add replycnt int not null default 0;

create table tbl_user(
	uid varchar(50) not null,
    upw varchar(50) not null,
    uname varchar(100) not null,
    upoint int not null default 0,
    primary key (uid)
);

create table tbl_message(
	mid int not null auto_increment,
    targetid varchar(50) not null,
    sender varchar(50) not null,
    message text not null,
    opendate timestamp,
    senddate timestamp not null default now(),
    primary key (mid)
);

alter table tbl_message add constraint fk_usertarget
foreign key (targetid) references tbl_user(uid);

alter table tbl_message add constraint fk_usersneder
foreign key (sender) references tbl_user(uid);

insert into tbl_user(uid, upw, uname)
values('user00', 'user00', '김권식');

insert into tbl_user(uid, upw, uname)
values('user11', 'user11', '박찬호');

update tbl_user
set upoint = 0
where uid = 'user00';

delete from tbl_message where mid > 0;

select * from tbl_message;
select * from tbl_user;

create table tbl_attach (
	fullName varchar(150) not null,
	bno int not null,
	regdate timestamp default now(),
	primary key (fullName)
);

select * from tbl_attach;

alter table tbl_attach add constraint fk_board_attach
foreign key (bno) references tbl_board(bno)
on delete cascade on update cascade;