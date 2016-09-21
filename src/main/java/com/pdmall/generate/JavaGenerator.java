package com.pdmall.generate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.handler.FileHandler;
import com.handler.StringUtilHandler;
import com.pdmall.config.Config;
import com.pdmall.entities.PdmColumn;
import com.pdmall.entities.PdmTable;
import com.pdmall.generate.constants.GenerateConst;

/*
* 从pdm的实体产生Java代码
* */
public class JavaGenerator extends CodeGenerator {
	@Override
	protected String getObjName(PdmTable pdmTable) {
		String className = StringUtilHandler.convertToPascalFromDb(pdmTable.getTableCode());
		return className;
	}

	@Override
	protected String getFileName(PdmTable pdmTable) {
		String classPhyName = StringUtilHandler.convertToPascalFromDb(pdmTable.getTableCode()).concat(".java");
		return classPhyName;
	}

	@Override
	protected String getFilePath(PdmTable pdmTable) {
		String path = Config.get("pdm.target_src.dir");
		path = path
				.concat("/src/")
				//.concat(pdmTable.getPdmPackage().getPhysicsPath())
				.concat("/");
		return path;
	}

	@Override
	protected void generateContent(FileWriter fw,PdmTable pdmTable, String filePath, String fileName, String objName) throws IOException {
		fw.write(GenerateConst.GENERATE_JAVA_CODE_PACKAGE+getPackageFromFilePath(filePath)+";\n");
		fw.write("import "+getPackageFromFilePath(filePath)+"._entity._"+objName+";\n");
		fw.write(GenerateConst.GENERATE_JAVA_CODE_PUBLIC+GenerateConst.GENERATE_JAVA_CODE_CLASS+objName+" "+GenerateConst.GENERATE_JAVA_CODE_EXTENDS+"_"+objName+"{\n");
		fw.write(getSerialId());
		fw.write("}");
	}

	@Override
	protected void extGenerateFile(PdmTable pdmTable) {
		try {
			String pojoClassPath = getFilePath(pdmTable)+"_entity/";
			String pojoClassPhyName = "_"+getFileName(pdmTable);
			String className = getObjName(pdmTable);
			File classFile = FileHandler.createFile(pojoClassPath , pojoClassPhyName);
			FileWriter fw = new FileWriter(classFile);
			fw.write(GenerateConst.GENERATE_JAVA_CODE_PACKAGE+getPackageFromFilePath(pojoClassPath)+";\n");
			fw.write(GenerateConst.GENERATE_JAVA_CODE_PUBLIC+GenerateConst.GENERATE_JAVA_CODE_CLASS+"_"+className+"{\n");
			fw.write(getSerialId());
			fw.write(getPropertyCode(pdmTable));
			fw.write(getSetterGetter(pdmTable));
			fw.write("}");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getPropertyCode(PdmTable pdmTable){
		StringBuffer rtn = new StringBuffer();
		rtn.append("\t//FIELDS\n");
		Iterator<PdmColumn> cols = pdmTable.getColumns().iterator();
		while(cols.hasNext()){
			PdmColumn col = cols.next();
			rtn.append("\t");
			rtn.append(GenerateConst.GENERATE_JAVA_CODE_PRIVATE);
			rtn.append(getColDataType(col));
			rtn.append(getColName(col));
			rtn.append(";");
			rtn.append("\t//"+col.getName()+"\n");
		}
		return rtn.toString();
		
	}
	
	private String getSetterGetter(PdmTable pdmTable){
		StringBuffer rtn = new StringBuffer();
		rtn.append("\t//GETTER&SETTER\n");
		Iterator<PdmColumn> cols = pdmTable.getColumns().iterator();
		while(cols.hasNext()){
			PdmColumn col = cols.next();
			rtn.append("\t"+GenerateConst.GENERATE_JAVA_CODE_PUBLIC+GenerateConst.GENERATE_JAVA_CODE_VOID);
			rtn.append("set"+StringUtilHandler.convertToPascalFromCamel(getColName(col))+"("+getColDataType(col)+" "+getColName(col)+"){\n");
			rtn.append("\t\tthis."+getColName(col)+" = "+getColName(col)+";\n");
			rtn.append("\t}\n");
			
			rtn.append("\t"+GenerateConst.GENERATE_JAVA_CODE_PUBLIC+getColDataType(col)+"");
			rtn.append("get"+StringUtilHandler.convertToPascalFromCamel(getColName(col))+"(){\n");
			rtn.append("\t\treturn "+getColName(col)+";\n");
			rtn.append("\t}\n");
		}
		return rtn.toString();
	}
	
	
	public String getColName(PdmColumn col){
		if(col.getRefName()!=null)
			return col.getRefName();
		else if(col.getRefTable()!=null)
			return StringUtilHandler.convertToCamelFromDb(col.getRefTable().getTableCode())+"s";
		else
			return StringUtilHandler.convertToCamelFromDb(col.getCode());
	}
	/*
	* classPath中解析出包名
	* */
	protected String getPackageFromFilePath(String classPath){
		String rtn = classPath.substring(classPath.indexOf("src/")+4,classPath.lastIndexOf("/"));
		rtn = rtn.replace("/", ".");
		return rtn; 
	}
	
	
	
	private String getSerialId(){
		return "\tprivate static final long serialVersionUID = 1L;\n";
	}

}
