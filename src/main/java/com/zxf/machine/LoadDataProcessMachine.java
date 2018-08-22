package com.zxf.machine;

import com.zxf.file.FileLoader;
import com.zxf.process.LoadDataProcess;
import com.zxf.util.TranslateUtil;

import java.util.Date;
import java.util.List;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/8/22
 */
public class LoadDataProcessMachine {
    public static void exec(LoadDataProcess processer) {
        Date time= new Date();
        System.out.println("开始时间 : "+time);
        long t1 = System.currentTimeMillis();
        //1.准备文件
        List<String> srcList = processer.prepareSrcFileListPath();

        //2.提取
        FileLoader.loadDataLineByLine(srcList, processer);

        long t2 = System.currentTimeMillis();

        time= new Date();
        System.out.println("结束时间 : "+time);
        System.out.println("costs: "+ TranslateUtil.formatMs(t2-t1) +" s");

    }
}
