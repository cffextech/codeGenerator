package com.pdmall.test;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import junit.framework.TestCase;

import com.pdmall.build.PdmEntityBuilder;
import com.pdmall.config.Config;
import com.pdmall.entities.PdmTable;
import com.pdmall.generate.CodeGenerator;
import com.pdmall.generate.JavaGenerator;


public class Test extends TestCase{

	public void testConfig() {
		assertNotNull(Config.get("pdm.path"));
	}
	
	public void testPdmParser(){
		PdmEntityBuilder p = new PdmEntityBuilder();
		List<PdmTable> pdmTables = p.parse();
		CodeGenerator javaGenerater= new JavaGenerator();
		javaGenerater.generate(pdmTables);
	}
	
//	public void testPrintDocument(){
//		SAXReader reader = new SAXReader();
//		File f = new File("_modules/platform/pdm/platform.pdm");
//		try {
//			Document doc = reader.read(f);
//			treeWalk(doc);
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//	}

	
	public void treeWalk(Document doc) {
		treeWalk(doc.getRootElement(),0);
	}

	public void treeWalk(Element element, int depth) {
		depth++;
		for (int i = 0, size = element.nodeCount(); i < size; i++) {
			Node node = element.node(i);
			for(int k=0; k<depth; k++){
				System.out.print("-");
			}
			if (node instanceof Element) {
				System.out.println(node.getName());
				treeWalk((Element) node,depth);
			} else {
				System.out.println(node.getName());
			}
		}
	}
}
