<#--json模板-->
{
    "columns":[
        <#list columns as column>
            {
                <#assign map = column>
                <#list map?keys as key>
                    ${key}:${map[key]}
                </#list>
            },
        </#list>
    ],
    "dbms":${dbms},
    "keys":[
        <#list keys as key>
        {
            <#assign map = key>
            <#list map?keys as key>
                ${key}:${map[key]}
            </#list>
        },
        </#list>
    ],
    "objectId":${objectId},
    "primaryKey":{
       <#-- <#assign map=primaryKey>-->
        <#list primaryKey?keys as key>
            ${key}:${map[key]}
        </#list>
    }
    "sid":${sid},
    "tableCode":${tableCode},
    "tableName":${tableName}
    ]
}