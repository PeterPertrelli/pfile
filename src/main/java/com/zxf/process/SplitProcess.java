package com.zxf.process;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/11/24
 */
public interface SplitProcess {
    /**
     * 用于后去源文件路径
     * @return 源文件路径
     */
    public List<String> prepareSrcFileListPath();

    /**
     * 判断这一行是不是新的一行，如果是的话，需要写到对应的文件中去
     * @param line
     * @return
     */
    public boolean isNewLine(String line);

    /**
     * 根据一行内容，源文件名，生成文件名
     * @param line
     * @param srcFileName
     * @return
     */
    public String getSplitFilePath(String line, String srcFileName);

    /**
     * 默认文件路径
     * @return
     */
    public String getDefaultSplitFilePath(String srcFileName);

    /**
     * 将一行写入
     * @param line
     * @param fw
     */
    public void printIntoFile(String line, FileWriter fw)  throws IOException;
}
