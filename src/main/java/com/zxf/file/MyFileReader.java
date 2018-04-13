package com.zxf.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFileReader {
	
	public static Map<String, Map<String, Object>> generateLogFiles(
			List<String> files) throws Exception {

		Map<String, Map<String, Object>> logFile = new HashMap<String, Map<String, Object>>();

		for (String filePath : files) {
			
			System.out.println("add file "+filePath);
			
			File file = new File(filePath);
			Map<String, Object> fileTools = new HashMap<String, Object>();
			fileTools.put("File", file);
			FileReader fileReader = new FileReader(file);
			fileTools.put("FileReader", fileReader);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			fileTools.put("BufferedReader", bufferedReader);
			logFile.put(filePath, fileTools);
		}

		return logFile;
	}




	
}
