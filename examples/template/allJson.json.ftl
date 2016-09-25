<#--json模板-->
<#--语法参照网址-->
<#--http://blog.163.com/chengwei_1104/blog/static/53645274201010246343445/-->
<#--http://www.cnblogs.com/linjiqin/p/3388298.html-->
{
    "columns":[
        <#list columns as column>
            {
                <#assign map = column>
                <#list map?keys as key>
                    "${key}":"${map[key]?string}",
                </#list>
            },
        </#list>
    ]
}