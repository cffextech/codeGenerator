package com.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



/**
 * Parser File which dataType is Xml
 * 解析XML文件
 * TODO 将XmlParser作为PdmParser的父类
 * @author cr
 *
 */
public class XmlParser {

	protected Element element;
	protected Document document;
	
	public XmlParser(String path){
		readPdm(path);
	}
	
	public XmlParser(Element element){
		this.element = element;
	}
	
	public XmlParser setElement(Element element){
		this.element = element;
		return this;
	}
	
	
	private void readPdm(String path){
		if(path == null || path.isEmpty())
			throw new RuntimeException("path is invalid:"+path);
		File f = new File(path);
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(f);
			document = doc;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public String attr(String attrName){
		return this.element.attributeValue(attrName);
	}
	
	@SuppressWarnings("rawtypes")
	public List find(String elementName){
		List<XmlParser> rtn = new ArrayList<XmlParser>();
		@SuppressWarnings("rawtypes")
		Iterator elements = null;
		if(element == null)
			elements =  document.selectNodes(elementName).iterator();
		else
			elements = element.selectNodes(elementName).iterator();
		
		while (elements.hasNext()) {
			rtn.add(new XmlParser((Element)elements.next()));
		}
		
		if(rtn.isEmpty())
			return null;
		return rtn;
	}
	
	public XmlParser findOne(String elementName){
		if(element != null)
			return this.setElement(element.element(elementName));
		return null;
	};
	
	public XmlParser getParent(){
		this.element = element.getParent();
		return this;
	}
	
	public XmlParser getParentByNodeName(String nodeName){
		for(Element e = element;e!=null;e=e.getParent()){
			if(e.getQName().getName().equals(nodeName.trim())){
				this.element = e;
				return this;
			}
		}
			
		return null;
	}
	
	public Element toElement(){
		return element;
	}
	
	public String getChildText(String nodeName){
		if(element == null)
			return null;
		return element.elementTextTrim(nodeName);
	}
	
	
}
