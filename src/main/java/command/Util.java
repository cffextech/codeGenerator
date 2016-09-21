package command;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lzy on 2016/8/31.
 */
public class Util {
    /*
    * TODO: json to Object
    * TODO：jackson的API还不是很清楚
    * */
    //模板文件分隔符
    public static String tplFileNameRegExp="\\.";
    //目录分隔符
    /*
    * TODO 该分隔符可以从OS取得
    * */
    public static String fileSeparator=File.separator;

    public static Map json2Map(String json){
        ObjectMapper mapper = new ObjectMapper();//转换器
        Map res=null;
        try {
            res = mapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String generateOutfileName(String dataFileName, String tplFileName){
        /*
        * 使用了split函数，参数是正则表达式，效率低于字符串函数的实现方式
        * */
        String[] dataFileNameArray=dataFileName.split(tplFileNameRegExp);
        String[] tplFileNameArray=tplFileName.split(tplFileNameRegExp);
        String outfileName="";
        if(tplFileNameArray.length>1){
            outfileName+=tplFileNameArray[0];
        }
        outfileName+="_";
        if(dataFileNameArray.length>1){
            outfileName+=dataFileNameArray[0];
        }
        outfileName+=".";
        if(tplFileNameArray.length>2){
            outfileName+=tplFileNameArray[1];
        }
        return outfileName;
    }

    /*
    * 验证文件名代表的文件目录是否存在
    * 譬如：E:\\test\\java\\test1_data.java
    * 如果E:\\test\\java\\文件目录不存在，会创建该文件目录。
    * */
    public static String verifyFileName(String filename){
        int i=filename.lastIndexOf(fileSeparator);
        if(i>0){
            String directory=filename.substring(0,i+1);
            File fp = new File(directory);
            if(!fp.exists()){
//                创建子目录
                fp.mkdirs();
            }
        }
        return filename;
    }
}
