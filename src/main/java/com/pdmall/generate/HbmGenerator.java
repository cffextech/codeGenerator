package com.pdmall.generate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.handler.StringUtilHandler;
import com.pdmall.config.Config;
import com.pdmall.entities.PdmColumn;
import com.pdmall.entities.PdmTable;
import com.pdmall.generate.constants.GenerateConst;

/*
* Hibernate文件的生成器
* */
public class HbmGenerator extends CodeGenerator {

	@Override
	protected String getObjName(PdmTable pdmTable) {
		String className = StringUtilHandler.convertToPascalFromDb(pdmTable
				.getTableCode());
		return className;
	}
/*
* Hibernate的思想：产生hbm.xml
* */
	@Override
	protected String getFileName(PdmTable pdmTable) {
		String classPhyName = StringUtilHandler.convertToPascalFromDb(
				pdmTable.getTableCode()).concat(".hbm.xml");
		return classPhyName;
	}

	@Override
	protected String getFilePath(PdmTable pdmTable) {
		String path = Config.get("pdm.target_web.dir");
		path = path.concat("/hbm/")
				.concat(pdmTable.getPdmPackage().getPhysicsPath()).concat("/");
		return path;
	}

	@Override
	protected void extGenerateFile(PdmTable pdmTable) {
	}

	@Override
	protected void generateContent(FileWriter fw, PdmTable pdmTable,
			String filePath, String fileName, String objName)
			throws IOException {
		fw.write(GenerateConst.GENERATE_XML_CODE_XML_HEAD);
		fw.write(getHbmHead());
		fw.write("<hibernate-mapping>\n");
		fw.write("\t<class "+GenerateConst.GENERATE_HBM_CODE_NAME+"= \""+getPackageFromFilePath(filePath)+"."+objName+"\" ");
		fw.write(GenerateConst.GENERATE_HBM_CODE_TABLE+"= \""+pdmTable.getTableCode()+"\" ");
		fw.write(" >\n");
		//TODO
//		fw.write(pdmTable.getDbms());
		fw.write(buildIdNode(pdmTable));
		fw.write(buildColumn(pdmTable));
		fw.write("\t</class>\n");
		fw.write("</hibernate-mapping >");
	}
	
	private String buildIdNode(PdmTable pdmTable){
		PdmColumn pk = pdmTable.getPrimaryKey();
		if(pk == null)
			return null;
		StringBuffer rtn = new StringBuffer();
		rtn.append("\t\t<"+GenerateConst.GENERATE_HBM_CODE_ID);
		rtn.append(GenerateConst.GENERATE_HBM_CODE_NAME+" = \""+StringUtilHandler.convertToCamelFromDb(pk.getCode())+"\" ");
		rtn.append(GenerateConst.GENERATE_HBM_CODE_TYPE+" = \""+getColDataType(pk).trim()+"\" ");
		rtn.append(GenerateConst.GENERATE_HBM_CODE_COLUMN+" = \""+pk.getCode().trim()+"\" ");
		rtn.append(" >\n");
		rtn.append("\t\t</id>\n");
		return rtn.toString();
	}
	
	public String buildColumn(PdmTable pdmTable){
		StringBuffer rtn = new StringBuffer();
		Iterator<PdmColumn> cols = pdmTable.getColumns().iterator();
		while(cols.hasNext()){
			PdmColumn col = cols.next();
			if(col.isPrimaryKey())
				continue;
			else if(col.isParent())
				rtn.append(buildSetNode(col));
			else if(col.getRefTable() != null)
				rtn.append(buildMtoNode(col));
			else 
				rtn.append(buildProperty(col));
		}
		return rtn.toString();
	}
	
	private String buildMtoNode(PdmColumn col){
		StringBuffer rtn = new StringBuffer();
		PdmTable refTable = col.getRefTable();
		String propName = col.getRefName()==null?StringUtilHandler.convertToCamelFromDb(refTable.getTableCode()):col.getRefName();
		rtn.append("\t\t<many-to-one "+GenerateConst.GENERATE_HBM_CODE_NAME+"= \""+propName+"\" " +
				GenerateConst.GENERATE_HBM_CODE_CLASS+"= \""+getPackageFromFilePath(getFilePath(refTable))+"."+StringUtilHandler.convertToPascalFromDb(refTable.getTableCode())+"\" " +
				GenerateConst.GENERATE_HBM_CODE_NOTNULL+"= \""+col.isNotNull()+"\">\n");
		rtn.append("\t\t\t<column "+GenerateConst.GENERATE_HBM_CODE_NAME+"=\""+col.getCode()+"\" />\n");
		rtn.append("\t\t</many-to-one>\n");
		return rtn.toString();
	}
	
	private String buildSetNode(PdmColumn col){
		StringBuffer rtn = new StringBuffer();
		PdmTable refTable = col.getRefTable();
		String propName = col.getRefName()==null?StringUtilHandler.convertToCamelFromDb(refTable.getTableCode())+"s":col.getRefName();
		rtn.append("\t\t<set "+GenerateConst.GENERATE_HBM_CODE_NAME+"= \""+propName+"\" " +
				"inverse=\"true\" cascade=\"persist, merge,save-update, evict, replicate, lock, refresh,fix-delete-orphan\" >\n");
		rtn.append("\t\t\t<key>\n");
		rtn.append("\t\t\t\t<column "+GenerateConst.GENERATE_HBM_CODE_NAME+"= \""+col.getRefColumn().getCode()+"\" />\n");
		rtn.append("\t\t\t</key>\n");
		rtn.append("\t\t\t<one-to-many "+GenerateConst.GENERATE_HBM_CODE_CLASS+"= \""+getPackageFromFilePath(getFilePath(refTable))+"."+StringUtilHandler.convertToPascalFromDb(refTable.getTableCode())+"\" />\n");
		rtn.append("\t\t</set>\n");
		return rtn.toString();
	}
	
	private String buildProperty(PdmColumn col){
		StringBuffer rtn = new StringBuffer();
		rtn.append("\t\t<property "+GenerateConst.GENERATE_HBM_CODE_NAME+" = \""+StringUtilHandler.convertToCamelFromDb(col.getCode())+"\" ");
		rtn.append(GenerateConst.GENERATE_HBM_CODE_COLUMN+"= \""+col.getCode()+"\" ");
		rtn.append(GenerateConst.GENERATE_HBM_CODE_TYPE+"= \""+getColDataType(col)+"\" ");
		rtn.append(GenerateConst.GENERATE_HBM_CODE_NOTNULL+"= \""+col.isNotNull()+"\" ");
		rtn.append(GenerateConst.GENERATE_HBM_CODE_INSERT+"= \"true\" ");
		rtn.append(GenerateConst.GENERATE_HBM_CODE_UPDATE+"= \"true\" ");
		rtn.append(">\n");
		rtn.append("\t\t</property>\n");
		return rtn.toString();
	}
	

	private String getHbmHead(){
		String hbmHead = "\n<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\" \"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\n";
		return hbmHead;
	}

	@Override
	protected String getPackageFromFilePath(String filePath) {
		String rtn = filePath.substring(filePath.indexOf("hbm/")+4,filePath.lastIndexOf("/"));
		rtn = rtn.replace("/", ".");
		return rtn; 
	}

}
