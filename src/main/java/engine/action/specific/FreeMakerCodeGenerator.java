package engine.action.specific;

import engine.action.CodeGenerator;
import engine.dao.ModelSource;
import engine.tao.TemplateSource;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.CodeTemplate;
import model.DataModel;
import util.StringUtil;

import java.io.*;
import java.util.List;

/**
 * Created by lzy on 2016/9/24.
 */
public class FreeMakerCodeGenerator extends CodeGenerator{

    public Configuration cfg;
    public String tplDirectory;

    public FreeMakerCodeGenerator(String tplDirectory) {
        this.tplDirectory = tplDirectory;
        try {
            cfg.setDirectoryForTemplateLoading(new File(tplDirectory));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(DataModel model, CodeTemplate tpl, FileOutputStream output) {

    }
    @Override
    public void execute(ModelSource model, TemplateSource tpl, FileOutputStream output){

    }
    @Override
    public void execute(List<DataModel> models, List<CodeTemplate> tpls, String outputDirectory) {
        for(CodeTemplate tpl:tpls){
            /*
            * TODO-add 这里要考虑模板存储在数据库中的情况
            * TODO-upgrade 代码层级过多
            * */
            try {
                Template FMtpl=cfg.getTemplate(tpl.getName());
                for(DataModel model:models){
                    String outFileName = StringUtil.generateOutfileName(model.getName(), tpl.getName());
                    try {
                        Writer out = new OutputStreamWriter(
                                new FileOutputStream(outputDirectory + outFileName)
                        );

                        try {
                            FMtpl.process(model.getContent(), out);
                        } catch (TemplateException e) {
                            e.printStackTrace();
                        }
                        out.flush();
                        out.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
