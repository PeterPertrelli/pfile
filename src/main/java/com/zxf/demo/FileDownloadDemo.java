package com.zxf.demo;

import com.zxf.util.FileDownloadTool;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/8/22
 */
public class FileDownloadDemo {
    public static void main(String[] args) {

        FileDownloadTool.download("http://www.17500.cn/getData/ssq.TXT", "F:\\study\\resource\\bfroud", "ssq.TXT");
        System.out.println("done...");

    }
}
