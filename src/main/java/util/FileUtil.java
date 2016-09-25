package util;

import java.io.*;
import java.util.*;

/**
 * Created by lzy on 2016/8/31.
 */
public class FileUtil {

    public static String fileSeparator="\\";
    /*
    * TODO:使用JavaDoc对函数添加注释。
    * */

    /*
    * get the first layer content of a single folder
    * */
    public static String [] getFileName(String path)
    {
        File file = new File(path);
        String [] fileName = file.list();
        return fileName;
    }

    /*
    * recursively get the all layer content of a single folder
    * */
    public static void getAllFileName(String path, List<String> fileName)
    {
        File file = new File(path);
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null)
            fileName.addAll(Arrays.asList(names));
        for(File f:files)
        {
            if(f.isDirectory())
            {
                getAllFileName(f.getAbsolutePath(),fileName);
            }
        }
    }
/*
* 带目录的文件名:相对路径的文件名
* */
    public static void getAllFileName(String Directory, String path, List<String> fileName)
    {
        File [] files = (new File(path)).listFiles();
        for(File a:files) {
            if (a.isDirectory()) {
                getAllFileName(Directory + a.getName()+fileSeparator, a.getAbsolutePath(), fileName);
            } else if (a.isFile()) {
                String filename=Directory + a.getName();
                fileName.add(filename);
            }
        }
    }

    public static String readFile(FileInputStream file){
        String result="";
        Reader fileReader=new InputStreamReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String read;
        try {
            while ((read = bufferedReader.readLine()) != null) {
                result += read+ "\r\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    /*
    * convert from file content to string
    * */
    public static String readFile(String filename){
        String result="";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            try{
                String read;
                while ((read = bufferedReader.readLine()) != null) {
                    result += read+ "\r\n";
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

/*
* TODO:PDM to Map,XML to Map,JSON to Map
*
* */
    public static Map getMap(){
        return new HashMap();
    }

    public static String getJSON(){
        return "";
    }
}
