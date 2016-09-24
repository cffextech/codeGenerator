package engine.dao.specific;

import engine.dao.ModelSource;
import model.DataModel;
import util.FileUtil;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 2016/9/24.
 */
/*
* TODO 该文件的处理适用于大多数的文本文件
* */
public class JsonModelSource extends ModelSource {

    public JsonModelSource(String address) {
        this.address=address;
    }

    public List<DataModel> getModel() {
        List<DataModel> dmList=new ArrayList<DataModel>();
        Map root= StringUtil.json2Map(FileUtil.readFile(address));
        DataModel dm=new DataModel();
        /*
        * TODO-upgrade 要适合正则表达式
        * */
        dm.setName(address.substring(address.lastIndexOf('/')));
        dm.setContent(root);
        dmList.add(dm);
        return dmList;
    }
}
