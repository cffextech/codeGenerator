<#--Java Bean实体类模板-->
<#--author:lzy-->
package ${ package };

import java.util.Date;
import java.io.Serializable;

public class ${className} implements Serializable{
<#list properties as pro>
private ${pro.proType} ${pro.proName};
</#list>

<#list properties as pro>
public void set${pro.proName}(${pro.proType} ${pro.proName}){
this.${pro.proName}=${pro.proName};
}

public  ${pro.proType} get${pro.proName}(){
return this.${pro.proName};
}
</#list>
}