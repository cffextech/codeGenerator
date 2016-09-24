package command;

import org.junit.Before;
import org.junit.Test;
import util.FileUtil;

import java.util.ArrayList;

/**
 * Created by lzy on 2016/8/31.
 */
public class FileStringUtilTest {

    private String currentDirectory="";
    private String projectDirectory="";

    @Before
    public void setUp() throws Exception {
        //currentDirectory=this.getClass().getResource("").getPath();
        //System.out.println(currentDirectory);
        projectDirectory=System.getProperty("user.dir");
        //System.out.println(System.getProperty("user.dir"));
        //System.out.println(System.getProperty("java.class.path"));
/*        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath() ;
        System.out.println(courseFile);*/

    }

    @Test
    public void getFileName() throws Exception {
        /*
        * linux下的测试要多做
        * */
        String[] filenames= FileUtil.getFileName(projectDirectory);

        for(String fname:filenames){
            System.out.println(fname);
        }
        //Object
        //assertArrayEquals();
    }

    @Test
    public void getAllFileName() throws Exception {
        ArrayList<String> filenames=new ArrayList<String>();
        filenames.clear();
        FileUtil.getAllFileName("",projectDirectory+"\\examples\\templates",filenames);
        for(String fname:filenames){
            System.out.println(fname);
        }
    }

    @Test
    public void readFile() throws Exception {
        String res= FileUtil.readFile(projectDirectory+"\\examples\\data-source\\employee.json");
        System.out.println(res);
    }

}