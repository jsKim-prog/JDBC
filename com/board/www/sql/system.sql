CREATE tablespace board datafile 'D:\board.dbf' size 20m;
--10-3에 DB생성

CREATE USER board Identified by 1234;
-- id board/ pw 1234
Grant connect, dba to board;
REVOKE dba from board;
GRANT RESOURCE to board;
--board 계정에 접속권한, 일반조원 권한 부여

ALTER USER board default tablespace board;
--board 계정에 기본 데이터베이스를 board로 지정
ALTER USER board Temporary tablespace temp;
--board 계정에 임시 데이터베이스 배정

-->board 계정으로 접속

CREATE USER boardtest IDENTIFIED BY boardtest;
GRANT connect, Resource to boardtest;
ALTER USER boardtest default tablespace board;
--boardtest계정 새로 생성 및 권한부여(일반조원)
Grant DBA to boardtest ;
-- boardtest계정 dba권한 부여(resource 권한은 table drop 안됨)
-->boardtest 계정으로 접속