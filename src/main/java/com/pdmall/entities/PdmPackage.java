package com.pdmall.entities;

import java.util.List;

public class PdmPackage {
	private String physicsPath;
	private String packageName;
	private String PackageCode;
	private List<PdmTable> pdmTables;
	
	
	
	public String getPhysicsPath() {
		return physicsPath;
	}
	public void setPhysicsPath(String physicsPath) {
		this.physicsPath = physicsPath;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageCode() {
		return PackageCode;
	}
	public void setPackageCode(String packageCode) {
		PackageCode = packageCode;
	}
	public List<PdmTable> getPdmTables() {
		return pdmTables;
	}
	public void setPdmTables(List<PdmTable> pdmTables) {
		this.pdmTables = pdmTables;
	}
	
	
	
}
