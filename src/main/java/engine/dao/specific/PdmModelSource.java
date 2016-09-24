package engine.dao.specific;

import engine.dao.ModelSource;
import engine.dao.pdm.cffex.pdm.JsonGenerator;
import engine.dao.pdm.pdmall.build.PdmEntityBuilder;
import engine.dao.pdm.pdmall.entities.PdmRef;
import engine.dao.pdm.pdmall.entities.PdmTable;
import model.DataModel;
import util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2016/9/24.
 */
public class PdmModelSource extends ModelSource {

    public PdmModelSource(String address) {
        this.address=address;
    }
/*
* TODO-upgrade PdmModelSource是否可以从JsonModelSource继承
* */
    public List<DataModel> getModel() {
        PdmEntityBuilder p = new PdmEntityBuilder(address);
        List<PdmTable> pdmTables = p.parse();
        List<PdmRef> pdmRefs = p.parseRefs(pdmTables);
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
        }

        return dmList;
    }
}
