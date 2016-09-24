package command;

import org.junit.Test;
import util.StringUtil;

import java.io.File;
import java.util.*;

/*
*  import static只会引入指定类中的静态方法,非静态方法不会引入
* */
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by lzy on 2016/9/1.
 */
public class StringUtilTest {
    @Test
    public void verifyFileName() throws Exception {
        String outfileName= StringUtil.verifyFileName("E:\\test\\java\\test_data.java");
        String outDirectory=outfileName.substring(0,outfileName.indexOf("\\")+1);
        File fp = new File(outDirectory);
        assertEquals(true,fp.exists());

        /*
        * TODO linux下的测试集
        * TODO abc.txt这种测试集
        * */
    }

    @Test
    public void generateOutfileName() throws Exception {
        /*
        * TODO 更多的测试集，添加"_."的测试
        * */
        String outfileName= StringUtil.generateOutfileName("data.json","test1/java/test.java.ftl");
        assertEquals("test1/java/test_data.java",outfileName);
    }

    @Test
    public void json2Map() throws Exception {
        /*
        * json格式定义：元数据表，元数据表中的定义参考
        * TODO 多写几个测试
        * */
        String json="{\n" +
                "  \"name\": {\n" +
                "    \"firstName\": [\n" +
                "      1,\n" +
                "      {\"fatherName\": \"Tom\", \"motherName\": \"Mary\"},\n" +
                "      [1,2,4]\n" +
                "    ]\n" +
                "  },\n" +
                "  \"phones\": [123, 234, 456]\n" +
                "}";

        Map res= StringUtil.json2Map(json);

        List O11=new ArrayList<Object>();

        Map O111=new LinkedHashMap<String, Object>();
        O111.put("fatherName","Tom");
        O111.put("motherName","Mary");

        List O112=new ArrayList<Integer>();
        O112.add(1);
        O112.add(2);
        O112.add(4);

        O11.add(1);
        O11.add(O111);
        O11.add(O112);
        Map O1=new LinkedHashMap<String, Object>();
        O1.put("firstName",O11);

        List O2=new ArrayList<Integer>();
        O2.add(123);
        O2.add(234);
        O2.add(456);

        Map expected = new LinkedHashMap<String, Object>();
        expected.put("name",O1);
        expected.put("phones",O2);

        assertThat("parsed map is not correct",res, is(expected));
        //assertThat(res.size(),is(2));
        /*
        * 模糊测试，精确测试
        * 单向测试，双向测试
        * TODO 异常测试 参数化测试 并行测试
        * */
    }
}