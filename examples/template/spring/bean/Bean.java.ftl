<#--java bean模板-->
<#--该模板可能依据业务衍生出更复杂的模板-->
<#--加一些注解-->
package test;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "${tableName?cap_first}")
public class ${tableName?cap_first} implements Serializable{

    <#list columns as column>
        <#if column.dataType??>
            <#if column.name??>
            <#if column.primaryKey>
        @Id
        @Column(name = "${column.name}")
        @GeneratedValue(strategy = GenerationType.AUTO)
            <#elseif column.dataType?starts_with("varchar")>
        @Size(min = 2)
        @Column(name = "${column.name}", nullable = ${column.notNull?string("true","false")}, length = ${column.dataType?substring(8,11)})
            </#if>
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