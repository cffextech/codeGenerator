<#--Java DAO 数据访问对象模板-->
<#--author:lzy-->
package ${ package };

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import com.excellence.common.base.BaseObject;

/**
* @author masb
*
*对应表(${ tableName })
*/

public class ${ className } extends BaseObject {

private static final long serialVersionUID = 1L;

<#list properties as pro>
private  ${pro.proType} ${pro.proName};
</#list>

/**无参构造方法**/
public ${ className }(){};

/**带参构造方法*/
public ${ className } (<#list properties as pro>${pro.proType} ${pro.proName}<#if pro_has_next>,<#else></#if></#list>){

<#list properties as pro>
this.${pro.proName} = ${pro.proName};
</#list>
}

/**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
public ${ className }(Map data){

<#list properties as pro>
this.${pro.proName} = data.get("${pro.fieldName}") == null ? null : (${pro.proType})data.get("${pro.fieldName}");
</#list>
}

/***
* 将形参传入的参数data的值,设置到vo对象对应属性中
*/
public BaseObject setMap(Map data) {
<#list properties as pro>
this.set${pro.proName?cap_first}(data.get("${pro.fieldName}") == null ? null : (${pro.proType})data.get("${pro.fieldName}"));
</#list>
return this;
}

/** 将vo对象的值放入到Map对象中*/
public Map toMap()
{
Map map = new HashMap();
<#list properties as pro>
map.put("${pro.fieldName}",${pro.proName});
</#list>
return map;
}

public String toString(){
return toMap().toString();
}

//属性get||set方法
<#list properties as pro>
public ${pro.proType} get${pro.proName?cap_first}() {
return this.${pro.proName};
}
public void set${pro.proName?cap_first}(Integer ${pro.proName}) {
this.${pro.proName} = ${pro.proName};
}
</#list>

}