package com.handler;

import java.io.File;
import java.io.IOException;

public class FileHandler {
	public static File createFile(String fileFoder,String fileName) throws IOException{
		File foder = new File(fileFoder);
		File file = new File(fileFoder+fileName);
		if(!foder.exists()){
			foder.mkdirs();
		}
		if(!file.exists())
			file.createNewFile();
		return file;
	}

	public static boolean isFileExists(String fileName){
		File f = new File(fileName);
		return f.exists();
	}
}
