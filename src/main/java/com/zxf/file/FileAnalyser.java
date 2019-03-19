package com.zxf.file;

import com.zxf.process.AnalyseProcess;
import com.zxf.process.SimultaneousProcess;
import com.zxf.util.TranslateUtil;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/12/29
 */
public class FileAnalyser {
    public static void read2WriteLineByLine(List<String> srcList, String resultFile,
                                            AnalyseProcess processer) {
        try {

            System.out.println(">>>>>>>>>>> analyse     start <<<<<<<<<<<");

            // //////////////////////////////////////////
            File file = new File(resultFile);
            if (file.exists()) {// 存在
                file.delete();// 删除再建
                file.createNewFile();
            } else {
                file.createNewFile();// 不存在直接创建

            }
            FileWriter fw = new FileWriter(file);// 文件写IO
            // //////////////////////////////////////////////////////

            Map<String, Map<String, Object>> logFile = MyFileReader.generateSrcFiles(srcList);

            String temp = null;

            for(String filePath : srcList){

                long a = System.currentTimeMillis();

                Map<String, Object> logFileTools = logFile.get(filePath);

                File logfile = (File) logFileTools.get("File");
                String fileName = logfile.getName();
                FileReader logfilefr = (FileReader) logFileTools.get("FileReader");
                BufferedReader logfilebr = (BufferedReader) logFileTools.get("BufferedReader");

                // 文件读IO
                System.out.print(filePath + "       begin ");
//                fw.write(filePath+"-------start---------\r\n\r\n");
                while ((temp = logfilebr.readLine()) != null) {// 读到结束为止
                    if(processer.need(temp)){
                        processer.prepareData(temp, fileName);
                    }
                }

//                fw.write(filePath+"-------end---------\r\n\r\n\r\n");
                logfilebr.close();
                logfilefr.close();

                long b = System.currentTimeMillis();

                System.out.print("       end       cost : "+ TranslateUtil.formatMs(b-a)+" s\r\n");
            }
            processer.processAndPrint(fw);

            fw.flush();
            fw.close();
            System.out.println(">>>>>>>>>>> analyse     done  <<<<<<<<<<<\r\n\r\n");

        } catch (Exception e) {
            StringWriter aWriter1 = new StringWriter();
            e.printStackTrace(new PrintWriter(aWriter1));
            System.out.println("----------------analyseByLine-Exception-----------------");
            System.out.println(aWriter1.toString());
            System.out.println("-----------------end-----------------");
        }

    }
}
