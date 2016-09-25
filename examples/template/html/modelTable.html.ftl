<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN" xml:lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Java 语言中 Enum 类型的使用介绍</title>
</head>
<body>
<table border="1">
    <tr>
        <th colspan="5">${tableName}</th>
    </tr>
    <tr>
        <th>属性名</th>
        <th>属性类型</th>
        <th>主键</th>
        <th>注释</th>
        <th>表限制</th>
    </tr>
<#list columns as column>
    <tr>
        <td>${column.name?if_exists }</td>
        <td>${column.dataType?if_exists }</td>
    <#--加if判断-->
        <td>${column.primaryKey?string }</td>
        <td></td>
        <td>${column.notNull?string("not null","null")}</td>
    </tr>
</#list>
</table>
</body>
</html>