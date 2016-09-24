package engine.dao.pdm.pdmall.entities;

public class PdmColumn {
	private String sid;
	private String name;
	private String code;
	private String dataType;
	private String length;
	private boolean notNull;//true:1 false:0
	private boolean isPrimaryKey;
	//
	private String objectID;
	//ref
	private boolean isParent; //等于false时实体 上是对象类型,等于true时是set类型
	private String refName;
	//	private PdmTable refTable;
//	private PdmColumn refColumn;
	private String refTableId;
	private String refColumnId;

	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public boolean isNotNull() {
		return notNull;
	}
	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getObjectID() {
		return objectID;
	}
	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}
	public boolean isParent() {
		return isParent;
	}
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
	}
	public String getRefTableId() {
		return refTableId;
	}
	public void setRefTableId(String refTableId) {
		this.refTableId = refTableId;
	}
	public String getRefColumnId() {
		return refColumnId;
	}
	public void setRefColumnId(String refColumnId) {
		this.refColumnId = refColumnId;
	}

}
