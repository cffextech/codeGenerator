package com.cffex.pdm;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pdmall.build.PdmEntityBuilder;
import com.pdmall.entities.PdmRef;
import com.pdmall.entities.PdmTable;

public class JsonGenerator {

	private List<PdmTable> pdmTables;
	private List<PdmRef> pdmRefs;

	public static void main(String[] args) {
		PdmEntityBuilder p = new PdmEntityBuilder();
		List<PdmTable> pdmTables = p.parse();
		List<PdmRef> pdmRefs = p.parseRefs(pdmTables);
		JsonGenerator jsonGenerator = new JsonGenerator(pdmTables, pdmRefs);
		jsonGenerator.generate();
	}

	public JsonGenerator(List<PdmTable> pdmTables, List<PdmRef> pdmRefs) {
		this.pdmTables = pdmTables;
		this.pdmRefs = pdmRefs;
	}

	public String generate() {
		JSONObject json = new JSONObject();
		//构建tables
//		JSONArray tables = buildTables();
		JSONArray tables = new JSONArray();
		for(PdmTable pdmTable : pdmTables){
			JSONObject table = (JSONObject) JSON.toJSON(pdmTable);
			tables.add(table);
		}
		json.put("tables", tables);
		//构建refs
		JSONArray refs = new JSONArray();
		for(PdmRef pdmRef : pdmRefs){
			JSONObject ref = (JSONObject) JSON.toJSON(pdmRef);
			refs.add(ref);
		}
		json.put("refs", refs);
		System.out.println(json);
		return json.toJSONString();
	}

//	private JSONArray buildTables() {
//		JSONArray tables = new JSONArray();
//		for(PdmTable pdmTable : pdmTables){
//			JSONObject table = new JSONObject();
//			table.put("id", pdmTable.getSid());
//			table.put("objectId", pdmTable.getObjectId());
//			table.put("tableName", pdmTable.getTableName());
//			table.put("tableCode", pdmTable.getTableCode());
//			JSONArray columns = buildColumns(pdmTable);
//			table.put("columns", columns);
//			JSONArray keys = buildKeys(pdmTable);
//		}
//		return tables;
//	}
//
//	//将pmdTable的columns转换成JSONArray
//	private JSONArray buildColumns(PdmTable pdmTable) {
//		JSONArray columns = new JSONArray();
//		for(PdmColumn pdmColumn : pdmTable.getColumns()){
//			//
//		}
//		return columns;
//	}
//
//	//将pdmTable的keys转换成JSONArray
//	private JSONArray buildKeys(PdmTable pdmTable){
//		//.....
//	}
}
