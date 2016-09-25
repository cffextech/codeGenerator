<#--dll 构造模板，数据库建表语句-->
<#--根据不同的${dbms}创建不同的dll语句-->

drop table if exists ${tableName?if_exists};

/*==============================================================*/
/* Table: ${tableName?if_exists}                                                 */
/*==============================================================*/
create table `${tableName?if_exists}`
(
<#list columns as column>
    <#if column.name?? >
    `${column.name?if_exists }` ${column.dataType?if_exists } ${column.notNull?string("NOT NULL","")},
    </#if>
</#list>
    PRIMARY KEY (`${primaryKey.name}`)
<#--外键另外说明-->
<#--CONSTRAINT `city_ibfk_1` FOREIGN KEY (`CountryCode`) REFERENCES `country` (`Code`)-->
) ENGINE=InnoDB AUTO_INCREMENT=4080 DEFAULT CHARSET=latin1;


