set foreign_key_checks = 0;

truncate table blog_post;
truncate table author;
truncate table comment;
truncate table author_posts;

insert into blog_post(id, title, content,date_created)
values(41,'title post 1','Post content 1','2021-06-03 11:37:23.000000' ),
(42,'title post 2','Post content 2','2021-06-03 11:38:23.000000' ),
(43,'title post 3','Post content 3','2021-06-03 11:39:23.000000' ),
(44,'title post 4','Post content 4','2021-06-03 11:40:23.000000' ),
(45,'title post 5','Post content 5','2021-06-03 11:41:23.000000' );

set foreign_key_checks = 1;