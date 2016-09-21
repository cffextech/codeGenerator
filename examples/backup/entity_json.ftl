<#--实体的JSON表示模板-->
<#--author:lzy-->
{
    "name":{
        "firstName":[
            ${name.firstName[0]},
            {
                "fatherName": "${name.firstName[1].fatherName}",
                "motherName": "${name.firstName[1].motherName}"
            },
            [
<#--这里要用循环-->
                ${name.firstName[2][0]},
                ${name.firstName[2][1]},
                ${name.firstName[2][2]}
            ]
        ]
    },
    "phones":[
<#--这里要用循环来写-->
        "${phones[0]}",
        "${phones[1]}",
        "${phones[2]}"
    ]
}