package com.zxf.process;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * machine会按行读取文件，process按行接收数据，判断是否输出，并在输出前对数据进行处理
 * @author zhuxiangfei
 * @Description:
 * @date 2018/4/10
 */
public interface SimultaneousProcess extends Process {
    /**
     * 用于获取输出文件路径
     * @return 输出文件路径
     */
    public String prepareResultFilePath();

    /**
     * 对读取到的一行进行处理，然后进行打印
     * @param line
     * @param fileName
     * @param fw
     * @throws IOException
     */
    public void processAndPrint(String line, String fileName, FileWriter fw) throws IOException;
}
