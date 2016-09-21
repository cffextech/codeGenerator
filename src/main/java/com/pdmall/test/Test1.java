package com.pdmall.test;

import java.util.List;

import com.pdmall.build.PdmEntityBuilder;
import com.pdmall.entities.PdmTable;
import com.pdmall.generate.CodeGenerater;
import com.pdmall.generate.HbmGenerater;
import com.pdmall.generate.JavaGenerater;

public class Test1 {
	public static void main(String[] args) {
		PdmEntityBuilder p = new PdmEntityBuilder();
		List<PdmTable> pdmTables = p.parse();
		Test1 t = new Test1();
//		t.testGenerateJava(pdmTables);
		t.testGenerateHbm(pdmTables);
	}

	public void testGenerateJava(List<PdmTable> pdmTables) {
		CodeGenerater javaGenerater = new JavaGenerater();
		javaGenerater.generate(pdmTables);
	}

	public void testGenerateHbm(List<PdmTable> pdmTables) {
		CodeGenerater hbmGenerater = new HbmGenerater();
		hbmGenerater.generate(pdmTables);
	}
}
