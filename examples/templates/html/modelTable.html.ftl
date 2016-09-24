<#--这个可以显示出来-->
<table border="1">
    <tr>
        <th colspan="2">模型</th>
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
        <td>${column.name}</td>
        <td>${column.dataType}</td>
        <#--加if判断-->
        <td>${column.primaryKey}</td>
        <td>注释没有</td>
        <td>${column.notNull}等</td>
    </tr>
    </#list>
</table>