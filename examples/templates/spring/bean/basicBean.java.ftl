<#--java bean模板-->
<#--该模板可能依据业务衍生出更复杂的模板-->
package ${pack}
import java.io.Serializable

public class ${name} implements Serializable{

    <#list columns as column>
        private ${column.dataType} ${column.name};
    </#list>

    <#list columns as column>
        public void set${column.name}(${column.dataType} ${column.name}){
            this.${column.name}=${column.name};
        }

        public ${column.dataType} get${column.name}(){
            return this.${column.name};
        }
    </#list>
}