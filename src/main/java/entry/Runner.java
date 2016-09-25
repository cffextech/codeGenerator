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
    * TODO-refactor 文件夹和文件，文件能否采用文件夹的方式实现？还是没有层次
    * */
    public static ModelSource ModelSourceFactory(String modelAddress){
        if(modelAddress.endsWith("json")){
            return new JsonModelSource(modelAddress);
        }else if(modelAddress.endsWith("pdm")){
            return new PdmModelSource(modelAddress);
        }else{
            System.out.println("feature need to be support!");
            /*
            * 默认是JSON的读取方式
            * */
            return new JsonModelSource(modelAddress);
        }
        //return null;
    }

    public static List<DataModel> getModels(String modelAddress){

        List<String> fileNames=new ArrayList<String>();
        if(new File(modelAddress).isDirectory()){
            FileUtil.getAllFileName("",modelAddress,fileNames);
        }else {
            int i=modelAddress.lastIndexOf('\\');
            fileNames.add(modelAddress.substring(i+1));
            modelAddress=modelAddress.substring(0,i+1);
        }

        List<DataModel> dmList=new ArrayList();
        for(String fname:fileNames){
            List<DataModel> dms=ModelSourceFactory(modelAddress+fname).getModel();
            dmList.addAll(dms);
        }
        return dmList;
    }


    public static List<CodeTemplate> getTemplates(String tplAddress){
        /*
        * TODO 文件的方式采用用文件夹的方式实现
        * */
        List<CodeTemplate> tplList=(new FileTemplateSource(tplAddress)).getTemplate();
        return tplList;
    }

    public static void generateCode(String modelAddress,String tplAddress,String outAddress){
        /*
        * TODO 文件分隔符统一换成FileUtil中的文件分隔符
        * */
        modelAddress=modelAddress.replace('/','\\');
        tplAddress=tplAddress.replace('/','\\');
        outAddress=outAddress.replace('/','\\');

        List<DataModel> dmList=getModels(modelAddress);
        List<CodeTemplate> tplList=getTemplates(tplAddress);

        FreeMakerCodeGenerator fmg=new FreeMakerCodeGenerator(
                tplAddress.substring(0,tplAddress.lastIndexOf('\\')+1));
        fmg.execute(dmList,tplList,outAddress);
    }

    public static void main(String args[]){

        String pwd=System.getProperty("user.dir");
        if(args.length<3){
            System.out.println("command should be: " +
                    "java -jar codeGenerationDemo.jar " +
                    "\"modelAddress\" " +
                    "\"tplAddress\" " +
                    "\"outAddress\"");
            return;
        }

        String modelAddress=args[0];
        String tplAddress=args[1];
        String outAddress=args[2];
        System.out.println(modelAddress);
        System.out.println(tplAddress);
        System.out.println(outAddress);
        generateCode(modelAddress,tplAddress,outAddress);
    }
}
