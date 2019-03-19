package com.zxf.file;

import com.zxf.process.SplitProcess;
import com.zxf.util.TranslateUtil;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/11/24
 */
public class FileSplitor {

    public static void readAndSplitByProcessRule(SplitProcess processer){

        try {

            //1.准备文件
            List<String> srcList = processer.prepareSrcFileListPath();
            Map<String, Map<String, Object>> srcFile = MyFileReader.generateSrcFiles(srcList);

            for(String filePath : srcList){

                long a = System.currentTimeMillis();

                Map<String, Object> srcFileTools = srcFile.get(filePath);

                Map<String, FileWriter> writerMap = new HashMap<>();

                File srcfile = (File) srcFileTools.get("File");
                String srcFileName = srcfile.getName();
                FileReader srcfilefr = (FileReader) srcFileTools.get("FileReader");
                BufferedReader srcfilebr = (BufferedReader) srcFileTools.get("BufferedReader");
                // 文件读IO
                System.out.print(filePath + "       begin ");
                String line = null;
                String lastSplitFilePath = processer.getDefaultSplitFilePath(srcFileName);
                while ((line = srcfilebr.readLine()) != null) {// 读到结束为止

                    if(processer.isNewLine(line)){
                        //是新的一行，单独写到对应的文件里
                        String splitFilePath = processer.getSplitFilePath(line, srcFileName);

                        try {
                            FileWriter fw = getFileWriter(writerMap, splitFilePath);
                            processer.printIntoFile(line, fw);

                        }catch (Exception e){
                            StringWriter aWriter1 = new StringWriter();
                            e.printStackTrace(new PrintWriter(aWriter1));
                            System.out.println("----------------write-Exception-----------------splitFilePath : "+splitFilePath);
                            System.out.println(aWriter1.toString());
                            System.out.println("-----------------end-----------------");
                        }


                        lastSplitFilePath = splitFilePath;
                    }else if(null != lastSplitFilePath && !"".equals(lastSplitFilePath)){

                        try {
                            FileWriter fw = getFileWriter(writerMap, lastSplitFilePath);
                            //不是新的一行，写到上一行的文件里
                            processer.printIntoFile(line, fw);

                        }catch (Exception e){
                            StringWriter aWriter1 = new StringWriter();
                            e.printStackTrace(new PrintWriter(aWriter1));
                            System.out.println("----------------write-Exception-----------------lastSplitFilePath : "+lastSplitFilePath);
                            System.out.println(aWriter1.toString());
                            System.out.println("-----------------end-----------------");
                        }


                    }

                }


                for(Map.Entry<String, FileWriter> toolMap : writerMap.entrySet()){
                    FileWriter fw = (FileWriter)toolMap.getValue();

                    fw.flush();
                    fw.close();
                }

                srcfilebr.close();
                srcfilefr.close();

                long b = System.currentTimeMillis();

                System.out.print("       end       cost : "+ TranslateUtil.formatMs(b-a)+" s\r\n");
            }

        } catch (Exception e) {
            StringWriter aWriter1 = new StringWriter();
            e.printStackTrace(new PrintWriter(aWriter1));
            System.out.println("----------------readAndSplitByProcessRule-Exception-----------------");
            System.out.println(aWriter1.toString());
            System.out.println("-----------------end-----------------");
        }

    }

    private static FileWriter getFileWriter(Map<String, FileWriter> writerMap, String splitFilePath) throws IOException {

        FileWriter fw = writerMap.get(splitFilePath);

        if(null != fw){
            return fw;
        }

        File file = new File(splitFilePath);
        if (file.exists()) {// 存在
            file.delete();// 删除再建
            file.createNewFile();
        } else {
            file.createNewFile();// 不存在直接创建

        }
        fw = new FileWriter(file, true);// 文件写IO
        writerMap.put(splitFilePath, fw);

        return fw;
    }
}
