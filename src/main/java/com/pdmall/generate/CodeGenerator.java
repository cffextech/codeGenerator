package com.pdmall.generate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import com.handler.FileHandler;
import com.pdmall.entities.PdmColumn;
import com.pdmall.entities.PdmTable;
import com.pdmall.generate.constants.GenerateConst;

public abstract class CodeGenerator {

	/*
	*
	* */
	public boolean generate(List<PdmTable> pdmTables) {
		Iterator<PdmTable> it = pdmTables.iterator();
		while (it.hasNext()) {
			PdmTable pdmTable = it.next();
			generateFile(pdmTable);
			extGenerateFile(pdmTable);
		}
		return false;
	}

	/*
	* 产生文件（具体的类产生不同的文件：1、HbmGenerator产生hibernate的映射文件；
	* 2、JavaGenerator产生Java代码）
	* */
	private boolean generateFile(PdmTable pdmTable){
		try {
			String filePath = getFilePath(pdmTable);
			String fileName = getFileName(pdmTable);
			String objName = getObjName(pdmTable);
			if(FileHandler.isFileExists(filePath+objName))
				return true;
			File classFile = FileHandler.createFile(filePath, fileName);
			FileWriter fw = new FileWriter(classFile);
			generateContent(fw, pdmTable,filePath,fileName,objName);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	/*
	* 返回列属性的数据类型
	* */
	protected String getColDataType(PdmColumn col){
		String dataType = col.getDataType();
		if(dataType==null && col.getRefTable()!= null){
			return GenerateConst.GENERATE_JAVA_CODE_SET.trim()+"<"+
                    getPackageFromFilePath(getFilePath(col.getRefTable()))+"."+
                    getObjName(col.getRefTable())+"> ";
		}else if(col.getRefTable()!= null)
			return getPackageFromFilePath(getFilePath(col.getRefTable()))+"."+getObjName(col.getRefTable())+" ";
		else if(dataType.toLowerCase().contains("int"))
			return GenerateConst.GENERATE_JAVA_CODE_INTEGER;
		else if(dataType.toLowerCase().contains("varchar"))
			return GenerateConst.GENERATE_JAVA_CODE_STRING;
		else if(dataType.toLowerCase().contains("float"))
			return GenerateConst.GENERATE_JAVA_CODE_DOUBLE;
		else if(dataType.toLowerCase().contains("date"))
			return GenerateConst.GENERATE_JAVA_CODE_TIMESTAMP;
		return "";
	}
	/*
	* 写入\t符号
	* */
	protected void genSpace(FileWriter fw, int num){
		try {
			for(int i = 0 ; i < num ; i++)
				fw.write("\t");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	* 写入\n符号
	* */
	protected void genEnter(FileWriter fw,int num){
		for(int i = 0 ; i < num ; i++)
			try {
				fw.write("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	protected abstract String getPackageFromFilePath(String classPath);

	protected abstract String getObjName(PdmTable pdmTable);

	protected abstract String getFileName(PdmTable pdmTable);

	protected abstract String getFilePath(PdmTable pdmTable);
	
	protected abstract void generateContent(FileWriter fw,PdmTable pdmTable,String filePath,String fileName,String objName) throws IOException;
	
	protected abstract void extGenerateFile(PdmTable pdmTable);
}
