package org.meowcat.sqldata

import org.ktorm.database.Database

val database = Database.connect ("jdbc:mysql://localhost:3306/mychat?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC",
    driver = "com.mysql.cj.jdbc.Driver",
    user = "root",
    password = "X*2Jzh.m;Mrj")