<#--java bean模板-->
<#--该模板可能依据业务衍生出更复杂的模板-->
package test;
import java.io.Serializable;
import java.util.Date;

public class ${tableName?cap_first} implements Serializable{

    <#list columns as column>
        <#if column.dataType??>
            <#if column.name??>
        private <#if column.dataType=="bigint">int<#elseif column.dataType?starts_with("varchar")>String<#else>${column.dataType}</#if> ${column.name};
            </#if>
        </#if>
    </#list>

    <#list columns as column>
        <#if column.dataType??>
            <#if column.name??>

        public void set${column.name}(<#if column.dataType=="bigint">int<#elseif column.dataType?starts_with("varchar")>String<#else>${column.dataType}</#if> ${column.name?cap_first}){
            this.${column.name}=${column.name};
        }

        public <#if column.dataType=="bigint">int<#elseif column.dataType?starts_with("varchar")>String<#else>${column.dataType}</#if> get${column.name?cap_first}(){
            return this.${column.name};
        }
            </#if>
        </#if>
    </#list>
}