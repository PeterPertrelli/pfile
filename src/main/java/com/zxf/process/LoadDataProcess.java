package com.zxf.process;

import java.util.List;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/8/22
 */
public interface LoadDataProcess {

    /**
     * 源文件路径
     * @return 源文件路径
     */
    public List<String> prepareSrcFileListPath();

    /**
     * 是否需要读取到的文件内容
     * @param line
     * @return
     */
    public boolean need(String line);

    /**
     * 对读取到的一行进行处理
     * @param line
     * @param fileName
     */
    public void prepareData(String line, String fileName);
}
