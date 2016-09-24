package engine.dao.pdm.cffex.pdm;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class PdmTemplate {
	String templateFilePath;
	Document templateDocument;

	public static void main(String[] args) {

	}

	public Document getTemplateDocument() {
		return templateDocument;
	}

	public void setTemplateDocument(Document templateDocument) {
		this.templateDocument = templateDocument;
	}

	public static PdmTemplate generatePdmTemplate(String path){
		return new PdmTemplate(path);
	}

	private PdmTemplate(String path) {
		if(path == null || path.isEmpty())
			throw new RuntimeException("path is invalid:"+path);
		this.templateFilePath = path;
		this.templateDocument = createTemplateDocument();
	}

	private Document createTemplateDocument(){
		File f = new File(templateFilePath);
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(f);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}

}
