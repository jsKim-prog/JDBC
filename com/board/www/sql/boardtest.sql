drop table board;
drop table member;
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
values (board_seq.nextval, '비오내요~', '비오는데 등교하시는냐고 고생 하셨습니다.', 'aaa', sysdate);
insert into board (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '안녕하세요~', '비오는데 등교하시는냐고 고생 하셨습니다.', 'bbb', sysdate);
insert into board (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '감사합니다.~', '비오는데 등교하시는냐고 고생 하셨습니다.', 'ccc', sysdate);
insert into board (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '수고하셨내요~', '비오는데 등교하시는냐고 고생 하셨습니다.', 'aaa', sysdate);
insert into board (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '화이팅하세요~', '비오는데 등교하시는냐고 고생 하셨습니다.', 'ddd', sysdate);
insert into board (bno, btitle, bcontent, bwriter, bdate)
values (board_seq.nextval, '방갑습니다.~', '비오는데 등교하시는냐고 고생 하셨습니다.', 'eee', sysdate);

select * from board ;	
select * from board where bwriter = 'aaa';
select * from board where bno = 5;
delete from board where bno = 7;

insert into board (bno, btitle, bcontent, bwriter, bdate) values (board_seq.nextval, '새글', '새글테스트','aaa',sysdate);

-----member 테이블용
CREATE TABLE member(
	mno number(5) not null,
	mname nvarchar2(10) not null,
	mid nvarchar2(10) primary key, 
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

--Alter table member modify mid constraint mbt_id_uq unique;
select * from user_constraints where table_name like 'MEM%';

--더미데이터
insert into member (mno, mname, mid, mpw, mdate) values (member_seq.nextval, '김지선','aaa', '1234', sysdate);
insert into member (mno, mname, mid, mpw, mdate) values (member_seq.nextval, '김지선','bbb', '1234', sysdate);
insert into member (mno, mname, mid, mpw, mdate) values (member_seq.nextval, '문지현','ccc', '1234', sysdate);
insert into member (mno, mname, mid, mpw, mdate) values (member_seq.nextval, '이영훈','ㅇㅇㅇ', '1234', sysdate);
insert into member (mno, mname, mid, mpw, mdate) values (member_seq.nextval, '김기원','ddd', '1234', sysdate);

select count(mid) from member where mid='aaa';

select mno, mname, mid, mpw, mdate from member where mid = 'aaa' and mpw = '1234';
update member set mpw='111' where mid ='aaa';


--member보드 삭제회원관리용 트리거
CREATE TABLE member_backup AS SELECT * FROM member WHERE 1<>1; --백업용 빈 테이블 생성
ALTER TABLE member_backup ADD deldate DATE; --삭제일 컬럼 추가
SELECT * FROM member_backup;
SELECT * FROM member;
--CREATE PUBLIC SYNONYM M FOR member;

--emp_trg에서 삭제되면 emp_bu로 삽입(삭제일 포함)되는 트리거 생성
CREATE OR REPLACE TRIGGER del_backup 
AFTER
DELETE ON member
FOR EACH ROW 
BEGIN 
    IF DELETING THEN
    INSERT INTO member_backup VALUES (:old.mno,:old.mname,:old.mid,:old.mpw,:old.mdate,SYSDATE);
    END IF;
 END;
--insert 문에 :old.(의사레코드) 없으면 실행불가 ->>집어넣을 데이터 확실하게 명시해야함!
-- :NEW   ->이벤트가 INSERT, UPDATE 일때 사용되며 새롭게 입력(갱신을 위한 값)되는 행을 지칭 ,DELETE에 사용되면 모든 열이 NULL로 SETTING
--:OLD  ->이벤트가 DELETE, UPDATE 일때 사용되며 삭제 또는 갱신의 대상이 되는 행을 지칭 -> INSERT에 사용되면 모든 열이 NULL로 SETTING

SELECT * FROM user_triggers; --트리거 확인
delete from member where mid='ddd' and mpw='1234';