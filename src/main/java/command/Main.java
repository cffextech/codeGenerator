package command;

/**
 * Created by lzy on 2016/8/30.
 */

/*
* 计划：原型demo--->原型Java web demo---->
* ------>实现模板管理功能------>
* 数据接口层优化重构---->丰富模板集----->前端界面优化
* ----->漂亮，高亮显示的模板编辑器
* ------>部署上线
* 模板集
* 1.Java:bean,dao,action,repository,service,serviceImpl,servlet(controller)
* 2.js:
* 3.html:login
* 4.python:
* 5.C/C++:
* 模板引擎：freemarker,velocity,artTemplate...  支持多模板引擎
* 数据集：元数据,css格式数据,数据信息(展示页和详情页)
* 程序的展现方式：网站,app,desktop程序,IDE插件,框架插件
* 口号: 锤锤,一种代码生成方式
* */
public class Main {

    public static void main(String args[]){
        //1.参数读入
/*        for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "] is <" + args[i] + ">");
        }*/
        if(args.length<3){
            System.out.println("command should be: " +
                    "java -jar codeGenerationDemo.jar " +
                    "\"templateDirectory\"," +
                    "\"dataDirectory\"," +
                    "\"outDirectory\"");
            return;
        }
        CodeGenerator runner=new CodeGenerator(args[0],args[1],args[2]);
        runner.execute();
        System.out.println("The task of code generation is finished!");
        //2.模板集合和数据源集合
/*        Scanner cin = new Scanner(System.in);

        int a = cin.nextInt(), b = cin.nextInt();
        System.out.println(a + b);

        CodeGenerator cg=new CodeGenerator();
        System.out.println(cg.printDemo("Hello World"));*/

        /*
        * TODO:程序配置化的研究
        * */
    }
}
