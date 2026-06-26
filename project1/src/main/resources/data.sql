insert into todo ( id, userid, description, target_date, done)
values( 1000, 'hong4832','Learn Java', DATE_ADD( NOW(), Interval 1 YEAR), false );

insert into todo ( id, userid, description, target_date, done)
values( 1001, 'kim4832','Learn SQL', DATE_ADD( NOW(), Interval 1 MONTH), false );
insert into todo ( id, userid, description, target_date, done)
values( 1002, 'kim4832','Learn SpringBoot', DATE_ADD( NOW(), Interval 10 DAY), false );

insert into member ( userid, password, username, role )
values ( 'hong4832','1234','홍길동','ADMIN');
insert into member ( userid, password, username, role )
values ( 'kim4832','1234','김유신','USER');