<#--dll 构造模板，数据库建表语句-->
<#--根据不同的${dbms}创建不同的dll语句-->
create table ${tableName}(
<#list columns as column>
    `${column.name?if_exists }` `${column.dataType?if_exists }`
    `${column.notNull?string("not null","null")}`
</#list>
PRIMARY KEY (`${primaryKey.code}`)
<#--<#list keys as key>
KEY `${key.code}` (`${key.columnId}`)
</#list>-->
)ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8;