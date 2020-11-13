DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `role_permission`;
DROP TABLE IF EXISTS `permission`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `post`;

CREATE TABLE user
(
    uid      bigint(11)   not null primary key auto_increment,
    username varchar(40)  not null,
    password varchar(255) not null
);
CREATE TABLE role
(
    role_id bigint(11)   not null primary key auto_increment,
    name    varchar(255) not null
);
CREATE TABLE `user_role`
(
    `user_id` bigint(11) NOT NULL,
    `role_id` bigint(11) NOT NULL
);
CREATE TABLE `role_permission`
(
    `role_id`       bigint(11) NOT NULL,
    `permission_id` bigint(11) NOT NULL
);
CREATE TABLE `permission`
(
    `permission_id` bigint(11)   not null primary key auto_increment,
    `url`           varchar(255) not null,
    `name`          varchar(255) not null,
    `description`   varchar(255) NULL,
    `pid`           bigint(11)   not null
);
CREATE TABLE comment
(
    comment_id bigint(11) not null primary key auto_increment,
    post_id    bigint(11) not null,
    author_id  int(4)     not null,
    content    text(500)  not null
);
CREATE TABLE post
(
    post_id   bigint(11)  not null primary key auto_increment,
    title     varchar(40) not null,
    author_id bigint(11)  not null
);


INSERT INTO user (uid, username, password)
VALUES (1, 'user', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO user (uid, username, password)
VALUES (2, 'admin', 'e10adc3949ba59abbe56e057f20f883e');

INSERT INTO role (role_id, name)
VALUES (1, 'USER');
INSERT INTO role (role_id, name)
VALUES (2, 'ADMIN');

INSERT INTO permission (permission_id, url, name, pid)
VALUES (1, '/user/common', 'common', 0);
INSERT INTO permission (permission_id, url, name, pid)
VALUES (2, '/user/admin', 'admin', 0);

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1);
INSERT INTO user_role (user_id, role_id)
VALUES (2, 1);
INSERT INTO user_role (user_id, role_id)
VALUES (2, 2);

INSERT INTO role_permission (role_id, permission_id)
VALUES (1, 1);
INSERT INTO role_permission (role_id, permission_id)
VALUES (2, 1);
INSERT INTO role_permission (role_id, permission_id)
VALUES (2, 2);
