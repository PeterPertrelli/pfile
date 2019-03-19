package com.zxf.machine;

import com.zxf.file.FileAnalyser;
import com.zxf.file.FileExtracor;
import com.zxf.process.AnalyseProcess;
import com.zxf.process.SimultaneousProcess;
import com.zxf.util.TranslateUtil;

import java.util.Date;
import java.util.List;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/12/29
 */
public class ReadAnalyseProcessMachin {
    public static void exec(AnalyseProcess processer) {
        Date time= new Date();
        System.out.println("开始时间 : "+time);
        long t1 = System.currentTimeMillis();
        //1.准备文件
        List<String> srcList = processer.prepareSrcFileListPath();
        String resultFile = processer.prepareResultFilePath();

        //2.提取
        FileAnalyser.read2WriteLineByLine(srcList, resultFile, processer);

        long t2 = System.currentTimeMillis();

        time= new Date();
        System.out.println("结束时间 : "+time);
        System.out.println("costs: "+ TranslateUtil.formatMs(t2-t1) +" s");

    }
}
