CREATE TABLE board (
	bno NUMBER(5) primary key,
	btitle nvarchar2(30) not null,
	bcontent nvarchar2(1000) not null,
	bwriter nvarchar2(10) not null,
	bdate date not null
); --board 테이블 생성

create sequence board_seq
	increment by 1
	start with 1
	nocycle
	nocache;
	
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