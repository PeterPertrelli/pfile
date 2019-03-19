package com.zxf.process;

import java.util.List;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/8/22
 */
public interface LoadDataProcess extends Process {

    /**
     * 对读取到的一行进行处理
     * @param line
     * @param fileName
     */
    public void prepareData(String line, String fileName);
}
