package com.zxf.process;

import java.util.List;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/12/29
 */
public interface Process {
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
}
