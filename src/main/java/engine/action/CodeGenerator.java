package engine.action;

import engine.dao.ModelSource;
import engine.tao.TemplateSource;
import javafx.scene.chart.PieChart;
import model.CodeTemplate;
import model.DataModel;

import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by lzy on 2016/8/30.
 */

public abstract class CodeGenerator {
    public abstract void execute(DataModel model, CodeTemplate tpl, FileOutputStream output);

    public abstract void execute(ModelSource model, TemplateSource tpl, FileOutputStream output);

    public abstract void execute(List<DataModel> models, List<CodeTemplate> tpls, String output);

    //public abstract void execute(List<DataModel> models, List<CodeTemplate> tpls, List<FileOutputStream> outputs);

}

