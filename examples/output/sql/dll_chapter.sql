
drop table if exists chapter;

/*==============================================================*/
/* Table: chapter                                                 */
/*==============================================================*/
create table `chapter`
(
    `id` bigint NOT NULL,
    `courseId` bigint NOT NULL,
    `chapterName` varchar(255) ,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4080 DEFAULT CHARSET=latin1;


