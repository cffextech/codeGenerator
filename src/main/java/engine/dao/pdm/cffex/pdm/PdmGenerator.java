package engine.dao.pdm.cffex.pdm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import engine.dao.pdm.pdmall.build.PdmEntityBuilder;

public class PdmGenerator {

	private Document document;
	private List<PdmTable> pdmTables;
	private List<PdmRef> pdmRefs;
	private String fileName;
	private Element model;
	private Position position;

	public static void main(String[] args) {
		PdmEntityBuilder p = new PdmEntityBuilder();
		List<PdmTable> pdmTables = p.parse();
		List<PdmRef> pdmRefs = p.parseRefs(pdmTables);
		PdmGenerator pdmGenerator = new PdmGenerator(pdmTables, pdmRefs, "_modules/generate1.engine.dao.pdm");
		pdmGenerator.generate();
	}

	public PdmGenerator(List<PdmTable> pdmTables, List<PdmRef> pdmRefs, String fileName){
		PdmTemplate pdmTmpl = PdmTemplate.generatePdmTemplate("_modules/CodeTemplate.engine.dao.pdm");
		this.document = (Document) pdmTmpl.getTemplateDocument().clone();
		this.pdmTables = pdmTables;
		this.pdmRefs = pdmRefs;
		this.fileName = fileName;
		this.position = new Position();
	}

	public void generate(){
		Element root = document.getRootElement();
		this.model = root.element("RootObject").element("Children")
				.element("DataModel");

		setModelInfo();
		outputPdmFile();
	}

	//输出pdm
	private void outputPdmFile(){
		OutputFormat format = OutputFormat.createPrettyPrint();// 创建文件输出的时候，自动缩进的格式
		format.setEncoding("UTF-8");//设置编码
		try{
			XMLWriter writer = new XMLWriter(new OutputStreamWriter(
					new FileOutputStream(new File(fileName)), "UTF-8"), format);
			writer.write(document);
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	//设置Model信息
	private void setModelInfo() {
		//a:ObjectID
		//a:Name
		Element name = model.element("Name");
		name.setText(fileName);
		//a:Code
		Element code = model.element("Code");
		code.setText(fileName);
		//a:CreationDate
		//a:Creator
		//a:ModificationDate
		//a:Modifier
		//c:DBMS
		setDBMSInfo();
		//c:PhysicalDiagrams
		setPhysicalDiagramsInfo();
		//c:DefaultDiagram
		//c:Tables
		generateTables();
		//c:References
		generentReferences();
		//c:DefaultGroups
		setDefaultGroupsInfo();
		//c:TargetModels
		setTargetModelsInfo();
	}

	//设置DBMS信息
	private void setDBMSInfo() {
		//Element dbms = DataModel.element("DBMS");
		//Element shortcut = dbms.element("Shortcut");
		//a:ObjectID
		//a:Name
		//a:Code
		//a:CreationDate
		//a:Creator
		//a:ModificationDate
		//a:Modifier
		//a:TargetStereotype
		//a:TargetID
		//a:TargetClassID
	}

	//设置PhysicalDiagrams信息
	private void setPhysicalDiagramsInfo(){
		Element physicalDiagrams = model.element("PhysicalDiagrams");
		Element physicalDiagram = physicalDiagrams.element("PhysicalDiagram");
		//a:ObjectID
		//a:Name
		//a:Code
		//a:CreationDate
		//a:Creator
		//a:ModificationDate
		//a:Modifier
		generateSymbols(physicalDiagram);
	}

	//创建Symbols信息
	private void generateSymbols(Element physicalDiagram){
		Element symbols = physicalDiagram.addElement("c:Symbols");
		//为每个PdmRef创建一个ReferenceSymbol
		for(PdmRef pdmRef : pdmRefs){
			generateReferenceSymbol(symbols,pdmRef);
		}
		//为每个PdmTable创建一个TableSymbol
		for(PdmTable pdmTable : pdmTables){
			generateTableSymbol(symbols,pdmTable);
		}
	}

	//创建ReferenceSymbol
	private void generateReferenceSymbol(Element symbols, PdmRef pdmRef){
		//o:ReferenceSymbol
		Element referenceSymbol = symbols.addElement("o:ReferenceSymbol");
		referenceSymbol.addAttribute("Id", pdmRef.getParentTableId()+pdmRef.getChildTableId()+"Symbol");
		//a:CreationDate
		Element creationDate = referenceSymbol.addElement("a:CreationDate");
		creationDate.setText((new Date()).toString());
		//a:ModificationDate
		Element modificationDate = referenceSymbol.addElement("a:ModificationDate");
		modificationDate.setText((new Date()).toString());
		//a:Rect
		Element rect = referenceSymbol.addElement("a:Rect");
		rect.setText("((0,0), (10000,10000))");
		//a:ListOfPoints
		Element listOfPoints = referenceSymbol.addElement("a:ListOfPoints");
		listOfPoints.setText("((0,0), (10000,10000))");
		//a:CornerStyle
		Element cornerStyle = referenceSymbol.addElement("a:CornerStyle");
		cornerStyle.setText("1");
		//a:ArrowStyle
		Element arrowStyle = referenceSymbol.addElement("a:ArrowStyle");
		arrowStyle.setText("1");
		//a:LineColor
		Element lineColor = referenceSymbol.addElement("a:LineColor");
		lineColor.setText("12615680");
		//a:ShadowColor
		Element shadowColor = referenceSymbol.addElement("a:ShadowColor");
		shadowColor.setText("12632256");
		//a:FontList
		Element fontList = referenceSymbol.addElement("a:FontList");
		fontList.setText("CENTER 0 新宋体,8,N\n"
				+"SOURCE 0 新宋体,8,N\n"
				+"DESTINATION 0 新宋体,8,N");
		//c:SourceSymbol
		Element sourceSymbol = referenceSymbol.addElement("c:SourceSymbol");
		//o:TableSymbol
		Element srcTableSymbol = sourceSymbol.addElement("o:TableSymbol");
		srcTableSymbol.addAttribute("Ref", pdmRef.getChildTableId()+"Symbol");
		//c:DestinationSymbol
		Element destinationSymbol = referenceSymbol.addElement("c:DestinationSymbol");
		//o:TableSymbol
		Element desTableSymbol = destinationSymbol.addElement("o:TableSymbol");
		desTableSymbol.addAttribute("Ref", pdmRef.getParentTableId()+"Symbol");
		//c:Object
		Element object = referenceSymbol.addElement("c:Object");
		//o:Reference
		Element reference = object.addElement("o:Reference");
		reference.addAttribute("Ref", pdmRef.getSid());
	}

	//创建TableSymbol
	private void generateTableSymbol(Element symbols, PdmTable pdmTable){
		//o:TableSymbol
		Element tableSymbol = symbols.addElement("o:TableSymbol");
		tableSymbol.addAttribute("Id", pdmTable.getSid()+"Symbol");
		//a:CreationDate
		Element creationDate = tableSymbol.addElement("a:CreationDate");
		creationDate.setText((new Date()).toString());
		//a:ModificationDate
		Element modificationDate = tableSymbol.addElement("a:ModificationDate");
		modificationDate.setText((new Date()).toString());
		//a:IconMode
		Element iconMode = tableSymbol.addElement("a:IconMode");
		iconMode.setText("-1");
		//a:Rect
		Element rect = tableSymbol.addElement("a:Rect");
		rect.setText(calculatePosition());
		//a:LineColor
		Element lineColor = tableSymbol.addElement("a:LineColor");
		lineColor.setText("12615680");
		//a:FillColor
		Element fillColor = tableSymbol.addElement("a:FillColor");
		fillColor.setText("16570034");
		//a:ShadowColor
		Element shadowColor = tableSymbol.addElement("a:ShadowColor");
		shadowColor.setText("12632256");
		//a:FontList
		Element fontList = tableSymbol.addElement("a:FontList");
		fontList.setText("STRN 0 新宋体,8,N\n"
				+ "DISPNAME 0 新宋体,8,N\n"
				+ "OWNRDISPNAME 0 新宋体,8,N\n"
				+ "Columns 0 新宋体,8,N\n"
				+ "TablePkColumns 0 新宋体,8,U\n"
				+ "TableFkColumns 0 新宋体,8,N\n"
				+ "Keys 0 新宋体,8,N\n"
				+ "Indexes 0 新宋体,8,N\n"
				+ "Triggers 0 新宋体,8,N\n"
				+ "LABL 0 新宋体,8,N");
		//a:BrushStyle
		Element brushStyle = tableSymbol.addElement("a:BrushStyle");
		brushStyle.setText("6");
		//a:GradientFillMode
		Element gradientFillMode = tableSymbol.addElement("a:GradientFillMode");
		gradientFillMode.setText("65");
		//a:GradientEndColor
		Element gradientEndColor = tableSymbol.addElement("a:GradientEndColor");
		gradientEndColor.setText("16777215");
		//c:Object
		Element object = tableSymbol.addElement("c:Object");
		Element tableRef = object.addElement("o:Table");
		tableRef.addAttribute("Ref", pdmTable.getSid());
	}

	//创建Tables信息
	private void generateTables() {
		Element tables = model.addElement("c:Tables");
		//为每个PdmTable对象创建一个Table
		for(PdmTable pdmTable : pdmTables){
			generateTable(tables, pdmTable);
		}
	}

	//根据PdmTable创建Table
	private void generateTable(Element tables, PdmTable pdmTable) {
		//o:Table
		Element table = tables.addElement("o:Table");
		table.addAttribute("Id", pdmTable.getSid());
		//a:ObjectID
		Element objectID = table.addElement("a:ObjectID");
		objectID.setText(pdmTable.getObjectId());
		//a:Name
		Element name = table.addElement("a:Name");
		name.setText(pdmTable.getTableName());
		//a:Code
		Element code = table.addElement("a:Code");
		code.setText(pdmTable.getTableCode());
		//a:CreationDate
		Element creationDate = table.addElement("a:CreationDate");
		creationDate.setText((new Date()).toString());
		//a:Creator
		Element creator = table.addElement("a:Creator");
		creator.setText("Generator");
		//a:ModificationDate
		Element modificationDate = table.addElement("a:ModificationDate");
		modificationDate.setText((new Date()).toString());
		//a:Modifier
		Element modifier = table.addElement("a:Modifier");
		modifier.setText("Generator");
		//a:TotalSavingCurrency
		table.addElement("a:TotalSavingCurrency");
		//创建columns
		Element columns = table.addElement("c:Columns");
		for(PdmColumn pdmColumn : pdmTable.getColumns()){
			//parent column关系维护在reference中，不需要创建
			if(!pdmColumn.isParent()){
				generateColumn(columns, pdmColumn, pdmTable);
			}
		}
		//创建keys
		Element keys = table.addElement("c:Keys");
		for(PdmKey pdmKey : pdmTable.getKeys()){
			generateKey(keys, pdmKey, pdmTable);
		}

		//创建primarykey
		Element primaryKey = table.addElement("c:PrimaryKey");
		for(PdmKey pdmKey : pdmTable.getKeys()){
			if(pdmKey.isPrimaryKey()){
				Element key = primaryKey.addElement("o:Key");
				key.addAttribute("Ref", pdmKey.getSid());
			}
		}

	}

	//根据PdmColumn创建column
	private void generateColumn(Element columns, PdmColumn pdmColumn, PdmTable pdmTable){
		//o:Column
		Element column = columns.addElement("o:Column");
		column.addAttribute("Id", pdmColumn.getSid());
		//a:ObjectID
		Element objectId = column.addElement("a:ObjectID");
		objectId.setText(pdmColumn.getObjectID());
		//a:Name
		Element name = column.addElement("a:Name");
		name.setText(pdmColumn.getName());
		//a:Code
		Element code = column.addElement("a:Code");
		code.setText(pdmColumn.getCode());
		//a:CreationDate
		Element creationDate = column.addElement("a:CreationDate");
		creationDate.setText((new Date()).toString());
		//a:Creator
		Element creator = column.addElement("a:Creator");
		creator.setText("Generator");
		//a:ModificationDate
		Element modificationDate = column.addElement("a:ModificationDate");
		modificationDate.setText((new Date()).toString());
		//a:Modifier
		Element modifier = column.addElement("a:Modifier");
		modifier.setText("Generator");
		//a:DataType
		Element dataType = column.addElement("a:DataType");
		dataType.setText(pdmColumn.getDataType());
		//a:Length
		if(pdmColumn.getLength()!=null){
			Element length = column.addElement("a:Length");
			length.setText(pdmColumn.getLength());
		}
		//a:Column.Mandatory
		if(pdmColumn.isNotNull()==true){
			Element columnMandatory = column.addElement("a:Column.Mandatory");
			columnMandatory.setText("1");
		}
	}

	//根据PdmKey创建key
	private void generateKey(Element keys, PdmKey pdmKey, PdmTable pdmTable){
		//o:Key
		Element key = keys.addElement("o:Key");
		key.addAttribute("Id", pdmKey.getSid());
		//a:ObjectID
		Element objectId = key.addElement("a:ObjectID");
		objectId.setText(pdmKey.getObjectID());
		//a:Name
		Element name = key.addElement("a:Name");
		name.setText(pdmKey.getName());
		//a:Code
		Element code = key.addElement("a:Code");
		code.setText(pdmKey.getCode());
		//a:CreationDate
		Element creationDate = key.addElement("a:CreationDate");
		creationDate.setText((new Date()).toString());
		//a:Creator
		Element creator = key.addElement("a:Creator");
		creator.setText("Generator");
		//a:ModificationDate
		Element modificationDate = key.addElement("a:ModificationDate");
		modificationDate.setText((new Date()).toString());
		//a:Modifier
		Element modifier = key.addElement("a:Modifier");
		modifier.setText("Generator");
		//c:Key.Columns
		Element keyColumns = key.addElement("c:Key.Columns");
		//o:Column Ref
		Element columnRef = keyColumns.addElement("o:Column");
		columnRef.addAttribute("Ref", pdmKey.getColumnId());
	}

	//创建References信息
	private void generentReferences() {
		Element references = model.addElement("c:References");
		//为每个PdmRef对象创建一个Reference
		for(PdmRef pdmRef : pdmRefs){
			generateReference(references, pdmRef);
		}
	}

	//根据PdmRef创建Reference
	private void generateReference(Element references, PdmRef pdmRef){
		//o:Reference
		Element reference = references.addElement("o:Reference");
		reference.addAttribute("Id", pdmRef.getSid());
		//a:ObjectID
		Element objectID = reference.addElement("a:ObjectID");
		objectID.setText(pdmRef.getObjectId());
		//a:Name
		Element name = reference.addElement("a:Name");
		name.setText(pdmRef.getName());
		//a:Code
		Element code = reference.addElement("a:Code");
		code.setText(pdmRef.getCode());
		//a:CreationDate
		Element creationDate = reference.addElement("a:CreationDate");
		creationDate.setText((new Date()).toString());
		//a:Creator
		Element creator = reference.addElement("a:Creator");
		creator.setText("Generator");
		//a:ModificationDate
		Element modificationDate = reference.addElement("a:ModificationDate");
		modificationDate.setText((new Date()).toString());
		//a:Modifier
		Element modifier = reference.addElement("a:Modifier");
		modifier.setText("Generator");
		//a:Cardinality
		Element cardinality = reference.addElement("a:Cardinality");
		cardinality.setText(pdmRef.getCardinality());
		//a:UpdateConstraint
		Element updateConstraint = reference.addElement("a:UpdateConstraint");
		updateConstraint.setText("1");
		//a:DeleteConstraint
		Element deleteConstraint = reference.addElement("a:DeleteConstraint");
		deleteConstraint.setText("1");
		//c:ParentTable
		Element parentTable = reference.addElement("c:ParentTable");
		//o:Table
		Element pTable = parentTable.addElement("o:Table");
		pTable.addAttribute("Ref", pdmRef.getParentTableId());
		//c:ChildTable
		Element childTable = reference.addElement("c:ChildTable");
		//o:Table
		Element cTable = childTable.addElement("o:Table");
		cTable.addAttribute("Ref", pdmRef.getChildTableId());
		//c:ParentKey
		Element parentKey = reference.addElement("c:ParentKey");
		//o:Key
		Element key = parentKey.addElement("o:Key");
		key.addAttribute("Ref", pdmRef.getParentKeyId());
		//c:Joins
		Element joins = reference.addElement("c:Joins");
		//o:ReferenceJoin
		Element referenceJoin = joins.addElement("o:ReferenceJoin");
		referenceJoin.addAttribute("Id", pdmRef.getParentTableId()+pdmRef.getChildTableId()+"ReferenceJoin");
		//a:ObjectID
		Element refJoinObjectID = referenceJoin.addElement("a:ObjectID");
		refJoinObjectID.setText(pdmRef.getParentTableId()+pdmRef.getChildTableId()+"ReferenceJoinObjectID");
		//a:CreationDate
		Element refJoinCreationDate = referenceJoin.addElement("a:CreationDate");
		refJoinCreationDate.setText((new Date()).toString());
		//a:Creator
		Element refJoinCreator = referenceJoin.addElement("a:Creator");
		refJoinCreator.setText("Generator");
		//a:ModificationDate
		Element refJoinModificationDate = referenceJoin.addElement("a:ModificationDate");
		refJoinModificationDate.setText((new Date()).toString());
		//a:Modifier
		Element refJoinModifier = referenceJoin.addElement("a:Modifier");
		refJoinModifier.setText("Generator");
		//c:Object1
		Element object1 = referenceJoin.addElement("c:Object1");
		//o:Column
		Element object1Column = object1.addElement("o:Column");
		object1Column.addAttribute("Ref", pdmRef.getParentTableColumnId());
		//c:Object2
		Element object2 = referenceJoin.addElement("c:Object2");
		//o:Column
		Element object2Column = object2.addElement("o:Column");
		object2Column.addAttribute("Ref", pdmRef.getChildTableColumnId());
	}

	//设置DefaultGroups信息
	@SuppressWarnings("deprecation")
	private void setDefaultGroupsInfo(){
		//c:DefaultGroups
		Element defaultGroups = model.element("DefaultGroups");
		//o:Group
		Element group = defaultGroups.element("Group");
		group.setAttributeValue("Id", "defaultgroup");
	}

	//设置TargetModels信息
	@SuppressWarnings("deprecation")
	private void setTargetModelsInfo(){
		//c:TargetModels
		Element targetModels = model.element("TargetModels");
		//o:TargetModel
		Element targetModel = targetModels.element("TargetModel");
		targetModel.setAttributeValue("Id", "targetmodel");
	}

	//计算TableSymbol位置
	private String calculatePosition(){
		String result = "(("+position.getX()+","+position.getY()+"),("+
				(position.getX()+position.getL())+","+(position.getY()+position.getH())+"))";
		position.setCount(position.getCount()+1);
		if((position.getCount())%(position.getxCount())!=0){
			position.setX(position.getX()+position.getxSpace());
		}else{
			position.setX(position.getX()-position.getxSpace()*(position.getxCount()-1));
			position.setY(position.getY()-position.getySpace());
		}
		return result;
	}

	class Position {
		private int x=0;
		private int y=0;
		private int h=6000;
		private int l=12000;
		private int xSpace = 20000;
		private int ySpace = 16000;
		private int count=0;
		private int xCount = 4;

		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getH() {
			return h;
		}
		public void setH(int h) {
			this.h = h;
		}
		public int getL() {
			return l;
		}
		public void setL(int l) {
			this.l = l;
		}
		public int getxSpace() {
			return xSpace;
		}
		public void setxSpace(int xSpace) {
			this.xSpace = xSpace;
		}
		public int getySpace() {
			return ySpace;
		}
		public void setySpace(int ySpace) {
			this.ySpace = ySpace;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public int getxCount() {
			return xCount;
		}
		public void setxCount(int xCount) {
			this.xCount = xCount;
		}
	}
}
