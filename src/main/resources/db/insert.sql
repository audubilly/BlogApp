set foreign_key_checks = 0;

truncate table blog_post;
truncate table author;
truncate table comment;
truncate table author_posts;

insert into blog_post(id, title, content)
values(41,'title post 1','Post content 1'),
(42,'title post 2','Post content 2'),
(43,'title post 3','Post content 3'),
(44,'title post 4','Post content 4'),
(45,'title post 5','Post content 5');

set foreign_key_checks = 1;