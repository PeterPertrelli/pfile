package com.zxf.download;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2019/7/9
 */
public class DownloadRunable implements Runnable {

    private String url;
    private String saveDir;
    private String fileName;

    public DownloadRunable(String url, String saveDir, String fileName){
        this.url = url;
        this.saveDir = saveDir;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        this.println(String.format("=============下载 %s     文件名 %s",url, fileName));
        this.download(url, saveDir,fileName);

    }

    public void println(String s){
        System.out.println(s);
    }


    /**
     * 使用传统io stream 下载文件
     * @param url (官网：www.fhadmin.org)
     * @param saveDir
     * @param fileName
     */
    public void download(String url, String saveDir, String fileName) {

        BufferedOutputStream bos = null;
        InputStream is = null;
        try {
            byte[] buff = new byte[8192];
            is = new URL(url).openStream();
            File file = new File(saveDir, fileName);
            file.getParentFile().mkdirs();
            bos = new BufferedOutputStream(new FileOutputStream(file));
            int count = 0;
            while ( (count = is.read(buff)) != -1) {
                bos.write(buff, 0, count);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    /**
     * 利用 commonio 库下载文件，依赖Apache Common IO ，官网 https://commons.apache.org/proper/commons-io/
     * @param url (官网：www.fhadmin.org)
     * @param saveDir
     * @param fileName
     */
    public void downloadByApacheCommonIO(String url, String saveDir, String fileName) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(saveDir, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用NIO下载文件， 需要 jdk 1.4+ (官网：www.fhadmin.org)
     * @param url
     * @param saveDir
     * @param fileName
     */
    public void downloadByNIO(String url, String saveDir, String fileName) {
        ReadableByteChannel rbc = null;
        FileOutputStream fos = null;
        FileChannel foutc = null;
        try {
            rbc = Channels.newChannel(new URL(url).openStream());
            File file = new File(saveDir, fileName);
            file.getParentFile().mkdirs();
            fos = new FileOutputStream(file);
            foutc = fos.getChannel();
            foutc.transferFrom(rbc, 0, Long.MAX_VALUE);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (rbc != null) {
                try {
                    rbc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (foutc != null) {
                try {
                    foutc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 使用NIO下载文件， 需要 jdk 1.7+
     * @param url
     * @param saveDir
     * @param fileName
     */
    public void downloadByNIO2(String url, String saveDir, String fileName) {
        try (InputStream ins = new URL(url).openStream()) {
            Path target = Paths.get(saveDir, fileName);
            Files.createDirectories(target.getParent());
            Files.copy(ins, target, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
