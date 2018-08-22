package com.zxf.demo;

import com.zxf.machine.LoadDataProcessMachine;
import com.zxf.process.LoadDataProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/8/22
 */
public class AFileLoader implements LoadDataProcess {
    public static void main(String[] args) {
        AFileLoader extractor = new AFileLoader();
        LoadDataProcessMachine.exec(extractor);
    }
    /**
     * 源文件路径
     *
     * @return 源文件路径
     */
    @Override
    public List<String> prepareSrcFileListPath() {
        List<String> fileList = new ArrayList<String>();
        fileList.add("F:\\study\\resource\\bfroud\\ssq.TXT");
//        for(int i = 1; i <= 16; i++){
//            fileList.add("F:/线上log/scenic_back/catalina("+i+").out");
//        }
        return fileList;
    }

    /**
     * 是否需要读取到的文件内容
     *
     * @param line
     * @return
     */
    @Override
    public boolean need(String line) {
        return true;
    }

    /**
     * 对读取到的一行进行处理
     *
     * @param line
     * @param fileName
     */
    @Override
    public void prepareData(String line, String fileName) {
        System.out.println(line);
    }
}
