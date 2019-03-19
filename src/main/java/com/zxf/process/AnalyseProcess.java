package com.zxf.process;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/12/29
 */
public interface AnalyseProcess extends Process {
    /**
     * 对读取到的一行进行处理
     * @param line
     * @param fileName
     */
    public void prepareData(String line, String fileName);
    /**
     * 对收集到的信息进行处理，然后进行打印
     * @param fw
     * @throws IOException
     */
    public void processAndPrint( FileWriter fw) throws IOException;
    /**
     * 用于获取输出文件路径
     * @return 输出文件路径
     */
    public String prepareResultFilePath();
}
