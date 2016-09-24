create table chapter(
    `id` `bigint`
    `not null`
    `courseId` `bigint`
    `not null`
    `chapterName` `varchar(255)`
    `null`
PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8;