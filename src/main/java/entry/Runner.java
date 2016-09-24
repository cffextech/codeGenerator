package entry;

import engine.action.specific.FreeMakerCodeGenerator;
import engine.dao.ModelSource;
import engine.dao.specific.JsonModelSource;
import engine.dao.specific.PdmModelSource;
import engine.tao.specific.FileTemplateSource;
import model.CodeTemplate;
import model.DataModel;
import util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzy on 2016/8/30.
 */

public class Runner {

    /*
    * TODO-upgrade 这里改用工厂模式
    * */
    public static ModelSource ModelSourceFactory(String modelAddress){
        if(modelAddress.endsWith("json")){
            return new JsonModelSource(modelAddress);
        }else if(modelAddress.endsWith("pdm")){
            return new PdmModelSource(modelAddress);
        }else{
            System.out.println("feature need to be support!");
        }
        return null;
    }

    public static List<DataModel> getModels(String modelAddress){

        List<String> fileNames=new ArrayList<String>();
        if(new File(modelAddress).isDirectory()){
            FileUtil.getAllFileName(modelAddress,fileNames);
        }else {
            fileNames.add(modelAddress);
        }

        List<DataModel> dmList=new ArrayList();
        for(String fname:fileNames){
            List<DataModel> dms=ModelSourceFactory(fname).getModel();
            dmList.addAll(dms);
        }
        return dmList;
    }


    public static List<CodeTemplate> getTemplates(String tplAddress){
        List<CodeTemplate> tplList=(new FileTemplateSource(tplAddress)).getTemplate();
        return tplList;
    }

    public static void generateCode(String modelAddress,String tplAddress,String outAddress){
        List<DataModel> dmList=getModels(modelAddress);
        List<CodeTemplate> tplList=getTemplates(tplAddress);

        FreeMakerCodeGenerator fmg=new FreeMakerCodeGenerator(
                tplAddress.substring(tplAddress.lastIndexOf('/')));
        fmg.execute(dmList,tplList,outAddress);
    }

    public static void main(String args[]){

        //文件，文件夹，mongo地址,http地址
        String modelAddress="";

        //文件，文件夹，mongo地址,http地址
        String tplAddress="";
        //TODO 后期实现,模板引擎类型
        String tplEngine="";
        //可选的，默认是当前文件夹
        String outAddress="";

        generateCode(modelAddress,tplAddress,outAddress);
    }
}
