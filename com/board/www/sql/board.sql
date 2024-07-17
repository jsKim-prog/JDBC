DROP TABLE borad;

CREATE TABLE board (
	bno NUMBER(5) primary key,
	btitle nvarchar2(30) not null,
	bcontent nvarchar2(1000) not null,
	bwriter nvarchar2(10) not null,
	bdate date not null
); --board 테이블 생성

drop sequence board_seq;

create sequence board_seq
	increment by 1
	start with 1
	nocycle
	nocache;
	
ALTER TABLE board RENAME COLUMN bon to bno;	
	
insert into board (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '비오내요~', '비오는데 등교하시는냐고 고생 하셨습니다.', '김기원', sysdate);
insert into board (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '안녕하세요~', '비오는데 등교하시는냐고 고생 하셨습니다.', '김기원', sysdate);
insert into board (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '감사합니다.~', '비오는데 등교하시는냐고 고생 하셨습니다.', '김기원', sysdate);
insert into board (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '수고하셨내요~', '비오는데 등교하시는냐고 고생 하셨습니다.', '김기원', sysdate);
insert into board (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '화이팅하세요~', '비오는데 등교하시는냐고 고생 하셨습니다.', '김기원', sysdate);
insert into board (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '방갑습니다.~', '비오는데 등교하시는냐고 고생 하셨습니다.', '김기원', sysdate);

select * from board ;	

-----member 테이블용
CREATE TABLE member(
	mno number(5) primary key,
	mid nvarchar2(10) not null,
	mpw nvarchar2(10) not null,
	mdate date not null
);

create sequence member_seq
	increment by 1
	start with 1
	nocycle
	nocache;

Select * from MEMBER;
Truncate table member;

Alter table member modify mid constraint mbt_id_uq unique;
select * from user_constraints where table_name like 'MEM%';

--더미데이터
insert into member (mno, mid, mpw, mdate) values (member_seq.nextval, 'aaa', '1234', sysdate);
insert into member (mno, mid, mpw, mdate) values (member_seq.nextval, 'bbb', '1234', sysdate);
insert into member (mno, mid, mpw, mdate) values (member_seq.nextval, 'ccc', '1234', sysdate);
insert into member (mno, mid, mpw, mdate) values (member_seq.nextval, 'ddd', '1234', sysdate);
insert into member (mno, mid, mpw, mdate) values (member_seq.nextval, 'eee', '1234', sysdate);

select mno, mid, mpw, mdate from member where mid = 'aaa' and mpw = '1234';