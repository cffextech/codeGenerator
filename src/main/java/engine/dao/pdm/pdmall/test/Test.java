package engine.dao.pdm.pdmall.test;

import java.util.List;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import engine.dao.pdm.pdmall.build.PdmEntityBuilder;
import engine.dao.pdm.pdmall.config.Config;


public class Test extends TestCase{


	public void testConfig() {
		assertNotNull(Config.get("pdm.path"));
	}

	public void testPdmParser(){
		PdmEntityBuilder p = new PdmEntityBuilder();
		List<PdmTable> pdmTables = p.parse();
	}

//	public void testPrintDocument(){
//		SAXReader reader = new SAXReader();
//		File f = new File("_modules/platform/engine.dao.pdm/platform.engine.dao.pdm");
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
