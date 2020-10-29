CREATE TABLE Comments(
    id int not null primary key auto_increment,
    subject int not null,
    uid int not null,
    email char(40) not null,
    content text(500) not null
);
CREATE TABLE Users(
    uid int not null primary key auto_increment,
    account varchar(40) not null,
    nick varchar(40) not null,
    password varchar(40) not null
);