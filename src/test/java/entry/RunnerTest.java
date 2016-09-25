package entry;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lzy on 2016/9/25.
 */
public class RunnerTest {
    @Test
    public void generateCode() throws Exception {
        String pwd=System.getProperty("user.dir");
/*
* TODO 使用效率高一点的容器
* */
        List<String> modelAddress=new ArrayList<String>();
        List<String> tplAddress=new ArrayList<String>();
        String outAddress=pwd+"\\examples\\output\\";

        modelAddress.add(pwd+"\\examples\\model\\pdm\\relationshipTest.pdm");
        modelAddress.add(pwd+"\\examples\\model\\pdm\\relationshipTest.pdm");
        modelAddress.add(pwd+"\\examples\\model\\pdm\\");
        modelAddress.add(pwd+"\\examples\\model\\json\\");
        modelAddress.add(pwd+"\\examples\\model\\pdm\\");
        modelAddress.add(pwd+"\\examples\\model\\json\\");
        modelAddress.add(pwd+"\\examples\\model\\hybrid\\");


        tplAddress.add(pwd+"\\examples\\template\\sql\\dll.sql.ftl");
        tplAddress.add(pwd+"\\examples\\template\\");
        tplAddress.add(pwd+"\\examples\\template\\sql\\dll.sql.ftl");
        tplAddress.add(pwd+"\\examples\\template\\sql\\dll.sql.ftl");
        tplAddress.add(pwd+"\\examples\\template\\");
        tplAddress.add(pwd+"\\examples\\template\\");
        tplAddress.add(pwd+"\\examples\\template\\");

        for(int i=0;i<=6;i++){
            Runner.generateCode(modelAddress.get(i),tplAddress.get(i),outAddress);
        }

    }

}