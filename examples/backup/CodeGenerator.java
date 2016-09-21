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
* 这个类要弄成一个单例对象
* */
/*
   * TODO 多模板引擎支持，抽象类和类继承机制
 * */

public class CodeGenerator {

    Configuration cfg;
    String tplDirectory;
    String dataDirectory;
    String outDirectory;

    public CodeGenerator() {
    }

    /*
    * 构造函数
    * 模板文件：dao_java.ftl,grid_html.ftl
    * 目录结构：com/Java/dao,com/python/bean
    * 生成的代码文件：com/Java/dao/employee_dao.java
    * 配置文件：db.config,output.config(文件生成规则)
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
            cfg.setDirectoryForTemplateLoading(new File(this.tplDirectory));
            /*
            * 适用于单机的绝对路径模板加载器
            * */
        } catch (IOException e) {
            e.printStackTrace();
        }
/*        cfg.setClassForTemplateLoading(this.getClass(),"");//这里的设置要清楚*/

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
            * TODO 这里的取名要有一定的规则
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
    /*
    * 后期datafile参数要修改
    * 这里是不是有内存泄漏问题?
    * TODO outfile的名称要可配置 outfile的配置放到一个新的函数里
    * */
    public void generateCode(String dataFile, String templateFile, String outfileSuffix) {
        String json = IO.readFile(dataDirectory + dataFile);
        Map root = Util.json2Map(json);
        try {
//            这个规则可以解决重名问题,支持自定义文件名
            /*
            * TODO：通过配置文件的方式来解决，代码需要重构，文件目录和文件名的方式要正确
            * */
            String outSubDirectory = "";
            String outfile = "";
            int i = templateFile.lastIndexOf("\\");
            int t = templateFile.lastIndexOf(".");
            int endi=0;
            if(templateFile.lastIndexOf("_")>=0){
                endi=templateFile.lastIndexOf("_");
            }else{
                endi= t >= 0 ? t : (templateFile.length()-1);
            }

            if (i > 0) {
                outSubDirectory = templateFile.substring(0, i + 1);
                File fp = new File(outDirectory + outSubDirectory);
                // 创建目录
                if (!fp.exists()) {
                    fp.mkdirs();// 目录不存在的情况下，创建目录。
                }
                outfile = templateFile.substring(i + 1, endi);
            } else {
                outfile = templateFile.substring(0, endi);
            }
            Writer out = new OutputStreamWriter(
                    new FileOutputStream(
                            outDirectory + outSubDirectory + outfile + "." + outfileSuffix)
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
                String outsuffix="txt";
                int ti=j.lastIndexOf("_");
                if (ti>= 0) {
                    int t = j.lastIndexOf(".");
                    int endi = t >= 0 ? t : (j.length()-1);
                    outsuffix=j.substring(ti+1,endi);
                }
                generateCode(i, j, outsuffix);

            }
        }
    }

    public String printDemo(String x) {
        return x;
    }
}


/*
* Configuration cfg= new Configuration();
freemarkerCfg.setClassForTemplateLoading(this.getClass(), "/");//类路径

[java] view plain copy
//cfg.setDirectoryForTemplateLoading(new File(System .getProperty("user.dir") +
"<a href="file://\\template">\\template"));//</a>j绝对路径
[java] view plain copy
//cfg.setServletContextForTemplateLoading(getServletContext(),"/templates");//web路径
cfg.setEncoding(Locale.getDefault(), "UTF-8");
Template template = null ;
*
* */