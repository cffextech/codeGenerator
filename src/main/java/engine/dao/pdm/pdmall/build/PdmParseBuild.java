package engine.dao.pdm.pdmall.build;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import engine.dao.pdm.pdmall.config.Config;

@Deprecated
public class PdmParseBuild {

	public static void main(String[] args) {
		PdmParseBuild p = new PdmParseBuild();
		p.parse();
	}

	public List<PdmTable> parse(){
		List<PdmTable> pdmTables = new ArrayList<PdmTable>();
		Document doc = readPdm(Config.get("pdm.path"));
		Iterator saxPackages = doc.selectNodes("//c:Packages//o:Package").iterator();
		while (saxPackages.hasNext()) {
			Element saxPackage = (Element) saxPackages.next();
			//set Package
			PdmPackage pdmPackage = new PdmPackage();
			pdmPackage.setPackageName(saxPackage.elementTextTrim("Name"));
			pdmPackage.setPackageCode(saxPackage.elementTextTrim("Code"));
			pdmTables.addAll(getPdmTables(saxPackage,pdmPackage));
		}
		setReferences(doc,pdmTables);
		return pdmTables;
	}

	private List<PdmTable> getPdmTables(Element saxPackage,PdmPackage pdmPackage){
		List<PdmTable> pdmTables = new ArrayList<PdmTable>();
		Iterator saxTables = saxPackage.selectNodes("c:Tables//o:Table").iterator();
		while (saxTables.hasNext()) {
			Element saxTable = (Element)saxTables.next();
			//set Table
			PdmTable pdmTable = new PdmTable();
			pdmTable.setSid(saxTable.attributeValue("Id"));
			pdmTable.setTableName(saxTable.elementTextTrim("Name"));
			pdmTable.setTableCode(saxTable.elementTextTrim("Code"));
			pdmTable.setObjectId(saxTable.elementTextTrim("ObjectID"));
			pdmTable.setColumns(getPdmColumns(saxTable));
			pdmTable.setPdmPackage(pdmPackage);
			//set Key
			pdmTable.setKeys(getPdmKeys(saxTable));
			//set PrimaryKey
			String pkId = saxTable.element("PrimaryKey").element("Key").attributeValue("Ref");
			PdmKey pk = pdmTable.getPdmKeyById(pkId);
			if(pk!=null)
				pdmTable.getPdmColumnById(pk.getColumnId()).setPrimaryKey(true);

			pdmTables.add(pdmTable);
		}
		return pdmTables;
	}

	private List<PdmColumn> getPdmColumns(Element saxTable){
		List<PdmColumn> pdmColumns = new ArrayList<PdmColumn>();
		Iterator saxColumns = saxTable.selectNodes("c:Columns//o:Column").iterator();
		while (saxColumns.hasNext()) {
			Element saxColumn = (Element)saxColumns.next();

			PdmColumn pdmColumn = new PdmColumn();
			pdmColumn.setSid(saxColumn.attributeValue("Id"));
			pdmColumn.setName(saxColumn.elementTextTrim("Name"));
			pdmColumn.setCode(saxColumn.elementTextTrim("Code"));
			pdmColumn.setDataType(saxColumn.elementTextTrim("DataType"));
			pdmColumn.setLength(saxColumn.elementTextTrim("Length"));
			pdmColumn.setNotNull(saxColumn.elementTextTrim("Mandatory")!=null?true:false);

			pdmColumns.add(pdmColumn);
		}

		return pdmColumns;
	}

	private List<PdmKey> getPdmKeys(Element saxTable){
		List<PdmKey> pdmKeys = new ArrayList<PdmKey>();
		Iterator saxKeys = saxTable.selectNodes("c:Keys//o:Key").iterator();
		while(saxKeys.hasNext()){
			Element saxKey = (Element) saxKeys.next();

			PdmKey pdmKey = new PdmKey();
			pdmKey.setSid(saxKey.attributeValue("Id"));
			pdmKey.setName(saxKey.elementTextTrim("Name"));
			pdmKey.setCode(saxKey.elementTextTrim("Code"));
			pdmKey.setColumnId(saxKey.element("Key.Columns").element("Column").attributeValue("Ref"));

			pdmKeys.add(pdmKey);
		}
		return pdmKeys;

	}

	private void setReferences(Document doc,List<PdmTable> pdmTables){
		Iterator saxRefs = doc.selectNodes("//c:References//o:Reference").iterator();
		while(saxRefs.hasNext()){
			Element saxRef = (Element) saxRefs.next();
			String refName = saxRef.elementTextTrim("Name");
			String parentTabId = getRefTabId(saxRef.element("ParentTable"),doc,pdmTables);
			String childTabId = getRefTabId(saxRef.element("ChildTable"),doc,pdmTables);
			String childColId =saxRef.element("Joins").element("ReferenceJoin").element("Object2").element("Column").attributeValue("Ref");


			String[] refNames =  refName.split("\\|");
			PdmTable parentTab = getPdmTableById(pdmTables,parentTabId);
			PdmTable childTab = getPdmTableById(pdmTables,childTabId);
			PdmColumn childCol = childTab.getPdmColumnById(childColId);
			//set childRef
			childCol.setParent(false);
			childCol.setRefTableId(parentTabId);
			if(refNames.length>1)
				childCol.setRefName(refNames[0]);
			//set parentRef
			PdmColumn parentCol = new PdmColumn();
			parentCol.setParent(true);
			parentCol.setRefTableId(childTabId);
			if(refNames.length>1)
				parentCol.setRefName(refNames[1]);
			parentTab.getColumns().add(parentCol);

		}
	}

	private String getRefTabId(Element saxRefTab,Document doc,List<PdmTable> pdmTables){
		Element table = saxRefTab.element("Table");
		if(table != null)
			return table.attributeValue("Ref");
		table = saxRefTab.element("Shortcut");
		if(table != null){
			Element shortCat = getSaxShortCatById(table.attributeValue("Ref"),doc);
			if(shortCat == null)
				return null;
			String shortCatTargetId = shortCat.elementTextTrim("TargetID");
			for (PdmTable pdmTable : pdmTables) {
				if(pdmTable.getObjectId().equals(shortCatTargetId))
					return pdmTable.getSid();
			}
		}
		return null;
	}

	private Element getSaxShortCatById(String id ,Document doc){
		Iterator saxShortCats = doc.selectNodes("//c:Tables//o:Shortcut").iterator();
		while (saxShortCats.hasNext()) {
			Element shortCat = (Element) saxShortCats.next();
			String shortCatId = shortCat.attributeValue("Id");
			if(shortCatId != null && shortCatId.equals(id))
				return shortCat;
		}
		return null;
	}


	private PdmTable getPdmTableById(List<PdmTable> pdmTables,String sid){
		for (PdmTable pdmTable : pdmTables) {
			if(pdmTable.getSid().equals(sid))
				return pdmTable;
		}
		return null;
	}


	private Document readPdm(String path){
		if(path == null || path.isEmpty())
			throw new RuntimeException("path is invalid:"+path);
		File f = new File(path);
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(f);
			return doc;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
