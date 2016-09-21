package command;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lzy on 2016/8/30.
 */
/*
*   TODO 这个类要弄成一个单例对象
* */
/*
   * TODO 多模板引擎支持，抽象类和类继承机制
 * */

public class CodeGenerator {

    public Configuration cfg;
    public String tplDirectory="";
    public String dataDirectory="";
    public String outDirectory="";

    /*
    * 构造函数
    * 模板文件：dao.java.ftl,grid.html.ftl
    * 目录结构：com/Java/dao,com/python/bean
    * 生成的代码文件：com/Java/dao/employee_dao.java
    * 配置文件：db.config,output.config(文件生成规则)
    * TODO 文件生成规则优化
    * TODO 从多个路径加载模板
    * */
    public CodeGenerator(String tplDirectory, String dataDirectory, String outDirectory) {
        this.cfg = new Configuration(Configuration.VERSION_2_3_23);

        this.tplDirectory = (tplDirectory.lastIndexOf("\\") < tplDirectory.length() - 1)
                ? tplDirectory + "\\" : tplDirectory;

        this.dataDirectory = (dataDirectory.lastIndexOf("\\") < dataDirectory.length() - 1)
                ? dataDirectory + "\\" : dataDirectory;

        this.outDirectory = (outDirectory.lastIndexOf("\\") < outDirectory.length() - 1)
                ? outDirectory + "\\" : outDirectory;
/*
* TODO 构造函数里有异常抛出
* */
        try {
            /*
            * 适用于单机的绝对路径模板加载器
            * */
            cfg.setDirectoryForTemplateLoading(new File(this.tplDirectory));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public void generateCode(FileInputStream data,FileInputStream template,
                             FileOutputStream outCode){
        String json = IO.readFile(data);
        Map root = Util.json2Map(json);
        Writer out = new OutputStreamWriter(outCode);
        try {
            /*
            * TODO 这里test的取名要有一定的规则
            * */
            Template tpl=new Template("test", new InputStreamReader(template),cfg);
            try {
                tpl.process(root, out);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateCode(String dataFile, String templateFile) {
        String json = IO.readFile(dataDirectory + dataFile);
        Map root = Util.json2Map(json);
        try {
            String outfileName=Util.generateOutfileName(dataFile,templateFile);
            outfileName=Util.verifyFileName(outDirectory+outfileName);//创建子目录
            Writer out = new OutputStreamWriter(
                    new FileOutputStream(outfileName)
            );
            Template tpl = cfg.getTemplate(templateFile);
            tpl.process(root, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    public void execute() {
        ArrayList<String> tplFilenames = new ArrayList<String>();
        IO.getAllFileName("", tplDirectory, tplFilenames);
        ArrayList<String> dataFilenames = new ArrayList<String>();
        IO.getAllFileName("", dataDirectory, dataFilenames);
        for (String i : dataFilenames) {
            for (String j : tplFilenames) {
                generateCode(i, j);
            }
        }
    }
}

