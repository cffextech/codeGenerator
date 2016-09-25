/*
package command;

import engine.action.CodeGenerator;
import org.junit.Before;
import org.junit.Test;

*/
/**
 * Created by lzy on 2016/8/30.
 *//*

public class CodeGeneratorTest {
    @Test
    public void execute() throws Exception {
        */
/*
        * 测试模板集和数据集的生成过程
        * *//*

        codeGenerator.execute();
    }

    private CodeGenerator codeGenerator;
    private String projectDirectory="";
    @Before
    public void setUp() throws Exception {
        projectDirectory=System.getProperty("user.dir");
        codeGenerator=new CodeGenerator(
                projectDirectory+"\\examples\\templates",
                projectDirectory+"\\examples\\data-source",
                projectDirectory+"\\examples\\output");
    }

    @Test
    public void generateCode() throws Exception {
        */
/*
        * TODO 多级目录的测试用例要多一些
        * *//*


*/
/*        FileInputStream data=new FileInputStream(
                "E:\\代码生成器测试\\examples\\data-source\\data.json");
        FileInputStream CodeTemplate=new FileInputStream(
                "E:\\代码生成器测试\\examples\\templates\\test2.ftl");
        FileOutputStream out=new FileOutputStream(
                "E:\\代码生成器测试\\examples\\output\\out1.json");
        codeGenerator.generateCode(data,CodeTemplate,out);*//*


*/
/*        FileOutputStream t1=new FileOutputStream(
                "E:\\Projects\\codeGenerationDemo\\examples\\output\\test.json");*//*

        codeGenerator.generateCode("data.json","test1\\test1.java.ftl");
    }
}*/
