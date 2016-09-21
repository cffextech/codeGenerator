package com.handler;
/*
* 匈牙利命名法
* 驼峰命名法
* 帕斯卡命名法
* 下划线命名法(_.-)
* */
public class StringUtilHandler {
	/*
	* 首字符大写
	* */
	public static String toUpperFirstChar(String s) {
		byte[] items = s.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	/*
	* 首字符小写
	* */
	public static String toLowerFirstChar(String s) {
		byte[] items = s.getBytes();
		items[0] = (byte) ((char) items[0] - 'A' + 'a');
		return new String(items);
	}
	
	/**
	 * convert varchar to Pascal(帕斯卡命名法)
	 * example: XXxX_YYYY --> XxxxYyyy
	 * @param s
	 * @return string with Pascal
	 */
	public static String convertToPascalFromDb(String s){
		String rtn = "";
		s = s.toLowerCase();
		String [] ss = s.split("_");
		for (int i = 0; i < ss.length; i++) {
			rtn = rtn.concat(StringUtilHandler.toUpperFirstChar(ss[i]));
		}
		return rtn;
	}
	/**
	 * convert varchar to Camel（驼峰写法）
	 * example: XXxX_YYYY --> xxxxYyyy
	 * @param s
	 * @return
	 */
	public static String convertToCamelFromDb(String s){
		String rtn = "";
		if(s == null)
			return rtn;
		s = s.toLowerCase();
		String [] ss = s.split("_");
		rtn = rtn.concat(ss[0]);
		for (int i = 1; i < ss.length; i++) {
			rtn = rtn.concat(StringUtilHandler.toUpperFirstChar(ss[i]));
		}
		return rtn;
	}
	
	/**
	 * convert Pascal to Camel
	 * example: XxxxYyyy --> xxxxYyyy
	 * @param s
	 * @return
	 */
	public static String convertToCamelFromPascal(String s){
		String rtn = "";
		if(s == null)
			return rtn;
		return StringUtilHandler.toLowerFirstChar(s);
	}
	
	/**
	 * convert Camel to Pascal
	 * example: xxxxYyyy --> XxxxYyyy
	 * @param s
	 * @return
	 */
	public static String convertToPascalFromCamel(String s){
		String rtn = "";
		if(s == null)
			return rtn;
		return StringUtilHandler.toUpperFirstChar(s);
	}
}
