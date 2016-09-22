package com.pdmall.entities;

public class PdmRef {
	private String sid;
	private String objectId;
	private String name;
	private String code;
	private String cardinality;
	private String parentTableId;
	private String childTableId;
	private String parentKeyId;
	private String parentTableColumnId;
	private String childTableColumnId;

	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCardinality() {
		return cardinality;
	}
	public void setCardinality(String cardinality) {
		this.cardinality = cardinality;
	}
	public String getParentTableId() {
		return parentTableId;
	}
	public void setParentTableId(String parentTableId) {
		this.parentTableId = parentTableId;
	}
	public String getChildTableId() {
		return childTableId;
	}
	public void setChildTableId(String childTableId) {
		this.childTableId = childTableId;
	}
	public String getParentKeyId() {
		return parentKeyId;
	}
	public void setParentKeyId(String parentKeyId) {
		this.parentKeyId = parentKeyId;
	}
	public String getParentTableColumnId() {
		return parentTableColumnId;
	}
	public void setParentTableColumnId(String parentTableColumnId) {
		this.parentTableColumnId = parentTableColumnId;
	}
	public String getChildTableColumnId() {
		return childTableColumnId;
	}
	public void setChildTableColumnId(String childTableColumnId) {
		this.childTableColumnId = childTableColumnId;
	}
}
