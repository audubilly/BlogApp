drop database if exists blogdb;
create database blogdb;


-- drop user if exists 'bloguser'@'localhost';
create user if not exists 'bloguser'@'localhost' identified by 'Blog123';
grant all priveledges on blogdb.* to 'bloguser'@'localhost';
flush priveledges;

