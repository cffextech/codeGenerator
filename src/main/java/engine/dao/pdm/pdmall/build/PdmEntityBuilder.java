package engine.dao.pdm.pdmall.build;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import engine.dao.pdm.pdmall.entities.PdmColumn;
import engine.dao.pdm.pdmall.entities.PdmKey;
import engine.dao.pdm.pdmall.entities.PdmPackage;
import engine.dao.pdm.pdmall.entities.PdmRef;
import engine.dao.pdm.pdmall.entities.PdmTable;
import engine.dao.pdm.pdmall.parse.PdmParser;

public class PdmEntityBuilder {

    private String path;
	public static void main(String[] args) {
		PdmEntityBuilder p = new PdmEntityBuilder();
		List<PdmTable> pdmTables = p.parse();
		System.out.println(pdmTables);
		//
	}

    public PdmEntityBuilder(){
        this.path=path;
    }

	public PdmEntityBuilder(String path){
        this.path=path;
    }


	public List<PdmTable> parse(){
		List<PdmTable> pdmTables = new ArrayList<PdmTable>();
        PdmParser parser = getDocument(path);
		//PdmParser parser = getDocument();
		Iterator<PdmParser> parseTableIt = parser.getAllTables().iterator();
		while (parseTableIt.hasNext()) {
			PdmParser parseTable= parseTableIt.next();
			PdmTable pdmTable = new PdmTable();
			pdmTable.setSid(parseTable.getId());
			pdmTable.setTableName(parseTable.getName());
			pdmTable.setTableCode(parseTable.getCode());
			pdmTable.setObjectId(parseTable.getObjectId());
			pdmTable.setColumns(buildPdmColumns(parseTable));
			pdmTable.setPdmPackage(buildParentPackage(parseTable));
			pdmTable.setDbms(parseTable.getDbmsName());
			//set Key
			pdmTable.setKeys(buildKeys(parseTable));
			if(pdmTable.getKeys() != null){
				//set PrimaryKey
				PdmParser parserPk = parseTable.child("PrimaryKey");
				if(parserPk != null){
					String pkId = parserPk.child("Key").attr("Ref");
					PdmKey pk = pdmTable.getPdmKeyById(pkId);
					if(pk!=null){
						pk.setPrimaryKey(true);
						pdmTable.getPdmColumnById(pk.getColumnId()).setPrimaryKey(true);
					}
				}
			}
			pdmTables.add(pdmTable);
		}

		setReferences(pdmTables);
		return pdmTables;
	}

	public List<PdmRef> parseRefs(List<PdmTable> pdmTables){
		List<PdmRef> pdmRefs = new ArrayList<PdmRef>();
		//List<PdmParser> parserRefList = getDocument().getAllRefs();
        List<PdmParser> parserRefList = getDocument(path).getAllRefs();
		if(parserRefList == null)
			return null;
		Iterator<PdmParser> parseRefIt = parserRefList.iterator();
		while(parseRefIt.hasNext()){
			PdmParser parseRef = parseRefIt.next();
			PdmRef pdmRef = new PdmRef();
			pdmRef.setSid(parseRef.getId());
			pdmRef.setObjectId(parseRef.getObjectId());
			pdmRef.setName(parseRef.getName());
			pdmRef.setCode(parseRef.getCode());
			pdmRef.setCardinality(parseRef.getCardinality());
			pdmRef.setParentTableId(getRefTabId(parseRef.child("ParentTable"),pdmTables));
			pdmRef.setChildTableId(getRefTabId(parseRef.child("ChildTable"),pdmTables));
			pdmRef.setParentKeyId(parseRef.child("ParentKey").child("Key").attr("Ref"));
			pdmRef.setParentTableColumnId(parseRef.child("Joins").child("ReferenceJoin").child("Object1").child("Column").attr("Ref"));
			pdmRef.setChildTableColumnId(parseRef.child("Joins").child("ReferenceJoin").child("Object2").child("Column").attr("Ref"));
			pdmRefs.add(pdmRef);
		}
		return pdmRefs;
	}

	public PdmParser getDocument(String path){
        return new PdmParser(path);
    }

	public PdmParser getDocument(){
		//return new PdmParser(Config.get("engine.dao.pdm.path"));
        return new PdmParser("E:/Projects/codeGenerationDemo/" +
        "/code_generator/examples/data-source/engine.dao.pdm/relationshipTest.engine.dao.pdm");
/*		return new PdmParser("E:/Projects/codeGenerationDemo/" +
				"/code_generator/接口/relationshipTest.engine.dao.pdm");*/
	}

	public List<PdmColumn> buildPdmColumns(PdmParser parseTable){
		List<PdmColumn> columns = new ArrayList<PdmColumn>();
		List<PdmParser> pdmColsList =parseTable.getColumns();
		if(pdmColsList == null)
			return null;
		Iterator<PdmParser> parseCols = pdmColsList.iterator();
		while(parseCols.hasNext()){
			PdmParser parseCol = parseCols.next();

			PdmColumn pdmColumn = new PdmColumn();
			pdmColumn.setSid(parseCol.getId());
			pdmColumn.setName(parseCol.getName());
			pdmColumn.setCode(parseCol.getCode());
			pdmColumn.setDataType(parseCol.getDataType());
			pdmColumn.setLength(parseCol.getLength());
			pdmColumn.setNotNull(parseCol.isNotNull());
			//
			pdmColumn.setObjectID(parseCol.getObjectId());

			columns.add(pdmColumn);
		}
		return columns;
	}

	public PdmPackage buildParentPackage(PdmParser parseTable){
		PdmParser parsePackage = parseTable.getParentByNodeName("Package");
		if(parsePackage == null)
			return null;
		PdmPackage pdmPackage = new PdmPackage();
		pdmPackage.setPackageName(parsePackage.getName());
		pdmPackage.setPackageCode(parsePackage.getCode());
		pdmPackage.setPhysicsPath(getPackagePhysicsPath(parsePackage));
		return pdmPackage;
	}

	private String getPackagePhysicsPath(PdmParser parsePackage){
		String path = parsePackage.getName();
		PdmParser parser = parsePackage;
		while (parser.getParentByNodeName("Package")!=null) {
			parser = parser.getParentByNodeName("Package");
			path = parser.getName()+"/"+path;
		}
		return path;
	}

	public List<PdmKey> buildKeys(PdmParser parseTable){
		List<PdmKey> pdmKeys = new ArrayList<PdmKey>();
		List<PdmParser> pdmKeyList = parseTable.getKeys();
		if(pdmKeyList== null)
			return null;
		Iterator<PdmParser> parseKeysIt = parseTable.getKeys().iterator();
		while (parseKeysIt.hasNext()) {
			PdmParser parseKey = parseKeysIt.next();

			PdmKey pdmKey = new PdmKey();
			pdmKey.setSid(parseKey.getId());
			pdmKey.setName(parseKey.getName());
			pdmKey.setCode(parseKey.getCode());
			pdmKey.setColumnId(parseKey.child("Key.Columns").child("Column").attr("Ref"));
			pdmKey.setObjectID(parseKey.getObjectId());

			pdmKeys.add(pdmKey);
		}
		return pdmKeys.isEmpty()?null:pdmKeys;
	}

	private void setReferences(List<PdmTable> pdmTables){
		//List<PdmParser> parserRefList = getDocument().getAllRefs();
        List<PdmParser> parserRefList = getDocument(path).getAllRefs();
		if(parserRefList == null)
			return;
		Iterator<PdmParser> parseRefIt = parserRefList.iterator();
		while(parseRefIt.hasNext()){
			PdmParser parseRef = parseRefIt.next();
			String refName = parseRef.getName();
			String parentTabId = getRefTabId(parseRef.child("ParentTable"),pdmTables);
			String childTabId = getRefTabId(parseRef.child("ChildTable"),pdmTables);
			String childColId =parseRef.child("Joins").child("ReferenceJoin").child("Object2").child("Column").attr("Ref");


			String[] refNames =  refName.split("\\|");
			PdmTable parentTab = getPdmTableById(pdmTables,parentTabId);
			PdmTable childTab = getPdmTableById(pdmTables,childTabId);
			PdmColumn childCol = childTab.getPdmColumnById(childColId);
			//set childRef
			childCol.setParent(false);
			//childCol.setRefTable(parentTab);
			childCol.setRefTableId(parentTabId);
			if(refNames.length>1)
				childCol.setRefName(checkrefNameExists(childTab,refNames[0]));
			//set parentRef
			PdmColumn parentCol = new PdmColumn();
			parentCol.setParent(true);
//			parentCol.setRefTable(childTab);
//			parentCol.setRefColumn(childCol);
			parentCol.setRefTableId(childTabId);
			parentCol.setRefColumnId(childColId);
			if(refNames.length>1)
				parentCol.setRefName(checkrefNameExists(parentTab,refNames[1]));
			else
				checkChildTabExists(parentTab,childTab);
			parentTab.getColumns().add(parentCol);

		}
	}

	private String getRefTabId(PdmParser parseRef,List<PdmTable> pdmTables){
		PdmParser parsetable = parseRef.child("Table");
		if(parsetable != null)
			return parsetable.attr("Ref");
		parsetable = parseRef.child("Shortcut");
		if(parsetable != null)
			return getRefTabIdWhenShortcut(parsetable,pdmTables);
		return null;
	}

	private String getRefTabIdWhenShortcut(PdmParser parsetable,List<PdmTable> pdmTables){
		Iterator<PdmParser> parseShortcutsIt = getDocument().getAllShortcut().iterator();
		while (parseShortcutsIt.hasNext()) {
			PdmParser parseShourcut = parseShortcutsIt.next();
			String shortCatId = parseShourcut.attr("Id");
			if(shortCatId != null && shortCatId.equals(parsetable.attr("Ref"))){
				String shortCatTargetId = parseShourcut.getTargetId();
				PdmTable table = getPdmTableByObjectId(pdmTables,shortCatTargetId);
				if(table != null)
					return table.getSid();
			}
		}
		return null;
	}

	private void checkChildTabExists(PdmTable parentTab,PdmTable childTab){
		Iterator<PdmColumn> parentCols = parentTab.getColumns().iterator();
		while(parentCols.hasNext()){
			PdmColumn parentCol = parentCols.next();
//			if(parentCol.getRefTable() == null || !parentCol.isParent())
//				continue;
//			if(parentCol.getRefTable().getTableCode().equals(childTab.getTableCode()))
//				if(parentCol.getRefName() == null)
//					throw new RuntimeException("duplicate childs field:["+childTab.getTableCode()+"]in table["+parentTab.getTableCode()+"]");

		}
	}

	private String checkrefNameExists(PdmTable pdmTable,String refName){
		Iterator<PdmColumn> cols = pdmTable.getColumns().iterator();
		while(cols.hasNext()){
			PdmColumn col = cols.next();
			if(col.getRefName() == null)
				continue;
			else if(col.getRefName().trim().equals(refName))
				throw new RuntimeException("duplicate childs refName:["+refName+"]in table["+pdmTable.getTableCode()+"]");

		}
		return refName;
	}


	//TODO 把List<PdmTable> 封装成容器以后这个方法就可以移到pdmTables里了
	private PdmTable getPdmTableById(List<PdmTable> pdmTables,String sid){
		for (PdmTable pdmTable : pdmTables) {
			if(pdmTable.getSid().equals(sid))
				return pdmTable;
		}
		return null;
	}
	//TODO 把List<PdmTable> 封装成容器以后这个方法就可以移到pdmTables里了
	private PdmTable getPdmTableByObjectId(List<PdmTable> pdmTables,String objectId){
		for (PdmTable pdmTable : pdmTables) {
			if(pdmTable.getObjectId().equals(objectId))
				return pdmTable;
		}
		return null;
	}
}
