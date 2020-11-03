CREATE TABLE comments(
    id int not null primary key auto_increment,
    subject int not null,
    uid int not null,
    content text(500) not null
);
CREATE TABLE users(
    uid int not null primary key auto_increment,
    account varchar(40) not null,
    nick varchar(40) not null,
    password varchar(40) not null
);
CREATE TABLE posts(
    id int not null primary key auto_increment,
    title varchar(40) not null,
    author int not null
);