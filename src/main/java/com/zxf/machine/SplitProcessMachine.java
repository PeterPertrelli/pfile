package com.zxf.machine;

import com.zxf.file.FileLoader;
import com.zxf.file.FileSplitor;
import com.zxf.process.SplitProcess;
import com.zxf.util.TranslateUtil;

import java.util.Date;
import java.util.List;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/11/24
 */
public class SplitProcessMachine {
    public static void exec(SplitProcess splitProcess){
        Date time= new Date();
        System.out.println("=========================开始时间 : "+time);
        long t1 = System.currentTimeMillis();

        FileSplitor.readAndSplitByProcessRule(splitProcess);

        long t2 = System.currentTimeMillis();

        time= new Date();
        System.out.println("=========================结束时间 : "+time);
        System.out.println("costs: "+ TranslateUtil.formatMs(t2-t1) +" s");
    }
}
