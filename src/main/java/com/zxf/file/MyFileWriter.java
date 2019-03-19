package com.zxf.file;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/11/24
 */
public class MyFileWriter {

    public static Map<String, Map<String, Object>> generateFilesWriterToolMap(){
        Map<String, Map<String, Object>> filesWriterToolMap = new HashMap<String, Map<String, Object>>();
        return filesWriterToolMap;
    }


    public static FileWriter getFileWriter(String fileName, Map<String, Map<String, Object>> fileMap) throws IOException {

        Map<String, Object> fileTools = fileMap.get(fileName);
        if(null == fileTools){
            fileTools = new HashMap<String, Object>();
            File file = new File(fileName);
            if (file.exists()) {// 存在
                file.delete();// 删除再建
                file.createNewFile();
            } else {
                file.createNewFile();// 不存在直接创建

            }
            FileWriter fw = new FileWriter(file, true);// 文件写IO
            fileTools.put("File", file);
            fileTools.put("FileWriter", fw);
        }
        return (FileWriter)fileTools.get("FileWriter");
    }

    public static void closeFileWriter(Map<String, Map<String, Object>> fileMap){
        try {

            for(Map.Entry<String, Map<String, Object>> fileToolMap : fileMap.entrySet()){
                for(Map.Entry<String, Object> toolMap : fileToolMap.getValue().entrySet()){
                    FileWriter fw = (FileWriter)toolMap.getValue();

                    fw.flush();
                    fw.close();
                }
            }


        }catch (Exception e){
            StringWriter aWriter1 = new StringWriter();
            e.printStackTrace(new PrintWriter(aWriter1));
            System.out.println("----------------close-Exception-----------------");
            System.out.println(aWriter1.toString());
            System.out.println("-----------------end-----------------");
        }

    }

}
