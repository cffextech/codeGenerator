{
    "name":{
        "firstName":[
            ${name.firstName[0]},
            {
                "fatherName": "${name.firstName[1].fatherName}",
                "motherName": "${name.firstName[1].motherName}"
            },
            [
                ${name.firstName[2][0]},
                ${name.firstName[2][1]},
                ${name.firstName[2][2]}
            ]
        ]
    },
    "phones":[
        ${phones[0]},
        ${phones[1]},
        ${phones[2]}
    ]
}