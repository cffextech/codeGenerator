package com.pdmall.entities;

import java.util.Iterator;
import java.util.List;

public class PdmTable {
	private String sid;
	private String objectId;
	private PdmPackage pdmPackage;
	private String tableName;
	private String tableCode;
	private List<PdmColumn> columns;
	private List<PdmKey> keys;
	private String dbms;

	public String getDbms() {
		return dbms;
	}
	public void setDbms(String dbms) {
		this.dbms = dbms;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public PdmPackage getPdmPackage() {
		return pdmPackage;
	}
	public void setPdmPackage(PdmPackage pdmPackage) {
		this.pdmPackage = pdmPackage;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableCode() {
		return tableCode;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	public List<PdmColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<PdmColumn> columns) {
		this.columns = columns;
	}
	public List<PdmKey> getKeys() {
		return keys;
	}
	public void setKeys(List<PdmKey> keys) {
		this.keys = keys;
	}

	public PdmKey getPdmKeyById(String id){
		for (PdmKey key : getKeys()) {
			if(key.getSid().equals(id))
				return key;
		}
		return null;
	}

	public PdmColumn getPdmColumnById(String id){
		for (PdmColumn pdmColumn : getColumns()) {
			if (pdmColumn.getSid().equals(id))
				return pdmColumn;
		}
		return null;
	}

	public PdmColumn getPrimaryKey(){
		Iterator<PdmColumn> cols = getColumns().iterator();
		while(cols.hasNext()){
			PdmColumn col = cols.next();
			if(col.isPrimaryKey())
				return col;
		}
		return null;
	}


}
