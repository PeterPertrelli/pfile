package com.zxf.demo;

import com.zxf.machine.ReadSimultaneousProcessMachine;
import com.zxf.process.SimultaneousProcess;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/4/10
 */
public class AFileExtracor implements SimultaneousProcess {
    /**
     * 用于获取输出文件路径
     *
     * @return 输出文件路径
     */
    @Override
    public String prepareResultFilePath() {
        return "F:/线上log/scenic_back/test-out.txt";
    }

    /**
     * 用于后去源文件路径
     *
     * @return 源文件路径
     */
    @Override
    public List<String> prepareSrcFileListPath() {
        List<String> fileList = new ArrayList<String>();
        fileList.add("F:/线上log/scenic_back/catalina.out");
        for(int i = 1; i <= 16; i++){
			fileList.add("F:/线上log/scenic_back/catalina("+i+").out");
		}
        return fileList;
    }

    /**
     * 是否对读取到的文件内容进行输出
     *
     * @param line
     * @return
     */
    @Override
    public boolean needPrint(String line) {
        return true;
    }

    /**
     * 对读取到的一行进行处理，然后进行打印
     *
     * @param line
     * @param fileName
     * @param fw
     * @throws IOException
     */
    @Override
    public void processAndPrint(String line, String fileName, FileWriter fw) throws IOException {
        fw.write(line + "\r\n\r\n");
    }

    public static void main(String[] args) {
        AFileExtracor extractor = new AFileExtracor();
        ReadSimultaneousProcessMachine.exec(extractor);
    }
}
