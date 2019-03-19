package com.zxf.file;

import com.zxf.process.LoadDataProcess;
import com.zxf.util.TranslateUtil;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/8/22
 */
public class FileLoader {
    public static void loadDataLineByLine(List<String> srcList,
                                          LoadDataProcess processer) {
        try {

            System.out.println(">>>>>>>>>>> load     start <<<<<<<<<<<");

            Map<String, Map<String, Object>> srcFile = MyFileReader.generateSrcFiles(srcList);

            String temp = null;

            for(String filePath : srcList){

                long a = System.currentTimeMillis();

                Map<String, Object> srcFileTools = srcFile.get(filePath);

                File srcfile = (File) srcFileTools.get("File");
                String fileName = srcfile.getName();
                FileReader srcfilefr = (FileReader) srcFileTools.get("FileReader");
                BufferedReader srcfilebr = (BufferedReader) srcFileTools.get("BufferedReader");

                // 文件读IO
                System.out.print(filePath + "       begin ");
                while ((temp = srcfilebr.readLine()) != null) {// 读到结束为止
                    if(processer.need(temp)){
                        processer.prepareData(temp, fileName);
                    }
                }

                srcfilebr.close();
                srcfilefr.close();

                long b = System.currentTimeMillis();

                System.out.print("       end       cost : "+ TranslateUtil.formatMs(b-a)+" s\r\n");
            }

            System.out.println(">>>>>>>>>>> load     done  <<<<<<<<<<<\r\n\r\n");

        } catch (Exception e) {
            StringWriter aWriter1 = new StringWriter();
            e.printStackTrace(new PrintWriter(aWriter1));
            System.out.println("----------------load-Exception-----------------");
            System.out.println(aWriter1.toString());
            System.out.println("-----------------end-----------------");
        }

    }
}
