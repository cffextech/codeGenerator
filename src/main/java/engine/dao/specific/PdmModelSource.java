package engine.dao.specific;

import engine.dao.ModelSource;
import engine.dao.pdm.cffex.pdm.JsonGenerator;
import engine.dao.pdm.pdmall.build.PdmEntityBuilder;
import model.DataModel;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2016/9/24.
 */
public class PdmModelSource extends ModelSource {

    public PdmModelSource(String address) {
        /*
        * TODO-new 这里应该有个地址的合法性判断
        * */
        this.address=address;
    }

    public List<DataModel> getModel() {
        PdmEntityBuilder p = new PdmEntityBuilder(address);
        List<PdmTable> pdmTables = p.parse();
        List<PdmRef> pdmRefs = p.parseRefs(pdmTables);
        /*
        * TODO-upgrade 这里不经过json，直接转化为Map root
        * */
        JsonGenerator jsonGenerator = new JsonGenerator(pdmTables, pdmRefs);
        Map root= StringUtil.json2Map(jsonGenerator.generate());

        List<Map> modelList=null;
        if (root.containsKey("tables")){
            /*表示有多个模型层*/
            modelList = (List) root.get("tables");
        }else{
            modelList=new ArrayList<Map>();
            modelList.add(root);
        }

        List<DataModel> dmList=new ArrayList<DataModel>();
        for (Map table : modelList) {
            DataModel dm=new DataModel();
            dm.setName((String) table.get("tableName"));
            dm.setContent(table);
            dmList.add(dm);
        }

        return dmList;
    }
}
