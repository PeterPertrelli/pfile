package com.zxf.demo;

import com.zxf.machine.SplitProcessMachine;
import com.zxf.process.SplitProcess;
import com.zxf.util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/11/24
 */
public class LogFileSplitByClass implements SplitProcess {

    public static void main(String[] args) throws IOException {
        LogFileSplitByClass splitByClass = new LogFileSplitByClass();
        SplitProcessMachine.exec(splitByClass);


//        String splitFilePath = destFilePath + "123.txt";
//        String line = "123";
//        splitByClass.printIntoFile(line, splitFilePath);
//
//        File file = new File(splitFilePath);
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        FileWriter fw = new FileWriter(file);
//
//        System.out.println("----"+line);
//        fw.write(line + "\r\n");
//
//        fw.flush();
//        fw.close();


    }

    private static final String defaultFileName = "else";
    private static final String destFilePath = "F:/线上log/tnt_hotel_price_service/";

    private static Map<String, String> fileNameMap=new HashMap<>();

    List<String> fileList = new ArrayList<>();

    /**
     * 用于后去源文件路径
     *
     * @return 源文件路径
     */
    @Override
    public List<String> prepareSrcFileListPath() {
        List<String> fileList = new ArrayList<String>();
        fileList.add("F:/线上log/tnt_hotel_price_service/catalina.out");
        fileList.add("F:/线上log/tnt_hotel_price_service/catalina (1).out");
        fileList.add("F:/线上log/tnt_hotel_price_service/catalina (2).out");

//        fileList.add("F:/线上log/tnt_hotel_price_service/123.txt");
//        fileList.add("F:/线上log/tnt_hotel_price_service/abd1.txt");
//        fileList.add("F:/线上log/tnt_hotel_price_service/abd2.txt");

//        for(int i = 1; i <= 16; i++){
//            fileList.add("F:/线上log/scenic_back/catalina("+i+").out");
//        }
        return fileList;
    }

    /**
     * 判断这一行是不是新的一行，如果是的话，需要写到对应的文件中去
     *
     * @param line
     * @return
     */
    @Override
    public boolean isNewLine(String line) {

        if(null == line || line.length() < 23){
            return false;
        }

        //2017-08-16 12:49:13,182 INFO[LvLogS]tnt_dist [DubboServerHandler-172.20.6.164:20880-thread-283] (SearchApiServiceImpl.java:242) - buildProd

        //1.判断是否可统计，以日期开头
        String date = line.substring(0, 23);
        String regEx = "[0-9]{4}-[0-9]{2}-[0-9]{2}\\s[0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3}";

        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(date);

        if(m.matches()){
            return true;
        }else{
            return false;
        }


    }

    /**
     * 根据一行内容，源文件名，生成文件名
     *
     * @param line
     * @param srcFileName
     * @return
     */
    @Override
    public String getSplitFilePath(String line, String srcFileName) {
        String date = line.substring(0, 23);
        String regEx = "[0-9]{4}-[0-9]{2}-[0-9]{2}\\s[0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3}";

        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(date);

        //2.获取Java名称和行号
        String javaName = null;

        if(!m.matches()){
            javaName = defaultFileName;
        }else{

            if(  !line.contains("] (") && !line.contains(") - ") ){
                System.out.println("un match str1 : "+line);
                javaName = defaultFileName;
            }else if(  (line.indexOf("] (")+3) > line.indexOf(") - ")){
                System.out.println("un match str2 : "+line);
                javaName = defaultFileName;
            }else  if(  (line.indexOf("] (")+3) > line.length()
                    ||  line.indexOf(") - ")  > line.length()){
                System.out.println("un match str3 : "+line);
                javaName = defaultFileName;
            }else {
                javaName = line.substring(line.indexOf("] (")+3, line.indexOf(") - "));
            }
        }
        if(StringUtil.isEmptyString(javaName)){
            javaName = defaultFileName;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(destFilePath).append(srcFileName).append("_").append(javaName.replaceAll(":",".")).append(".txt");



        String getLine = fileNameMap.get(sb.toString());
        if(null == getLine){
            fileNameMap.put(sb.toString(), line);
            System.out.println(sb.toString());
            System.out.println(line);
            System.out.println("----------");

        }



        return sb.toString() ;
    }

    /**
     * 默认文件路径
     *
     * @param srcFileName
     * @return
     */
    @Override
    public String getDefaultSplitFilePath(String srcFileName) {
        return destFilePath + srcFileName + "_default.txt";
    }

    /**
     * 将一行写入
     *
     * @param line
     * @param fw
     */
    @Override
    public void printIntoFile(String line,  FileWriter fw) throws IOException {

//        System.out.println(splitFilePath);

//        File file = new File(splitFilePath);
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        FileWriter fw = new FileWriter(file, true);

//        System.out.println(file.getName());
//        System.out.println("----"+line);
        fw.write(line + "\r\n");
//
//        fw.flush();
//        fw.close();
    }
}
