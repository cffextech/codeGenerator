package engine.tao.specific;

import engine.tao.TemplateSource;
import model.CodeTemplate;
import model.DataModel;
import util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzy on 2016/9/24.
 */
public class FileTemplateSource extends TemplateSource{
    private String tplAddress;

    public FileTemplateSource(String tplAddress) {
        this.tplAddress = tplAddress;
    }

    public List<CodeTemplate> getTemplate() {
        List<String> fileNames=new ArrayList<String>();
        if(new File(tplAddress).isDirectory()){
            FileUtil.getAllFileName(tplAddress,fileNames);
        }else {
            fileNames.add(tplAddress);
        }

        List<CodeTemplate> dmList=new ArrayList();
        for(String fname:fileNames){
            CodeTemplate ct=new CodeTemplate();
            ct.setName(fname);
            dmList.add(ct);
        }
        return dmList;
    }
}
