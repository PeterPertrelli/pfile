package com.zxf.download;

import com.zxf.util.FileDownloadTool;
import com.zxf.util.HttpClientResult;
import com.zxf.util.HttpClientUtils;
import com.zxf.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2019/7/9
 */
public class BatchDownloadMavenFiles {


    public static void main(String[] args){

        //    创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        long start = System.currentTimeMillis();

        BatchDownloadMavenFiles d = new BatchDownloadMavenFiles();
        d.download("http://192.168.0.101:8081/nexus/content/groups/public/org/mybatis/generator/mybatis-generator-maven-plugin/",
                "E:\\develop\\maven-repository\\org\\mybatis\\generator\\mybatis-generator-maven-plugin\\", executorService);

        executorService.shutdown();

        long end = System.currentTimeMillis();

        d.println(String.format("######     cost : %s ms", (end-start)));

    }

    /**
     * url的路径与path的路径对应起来，例如最后都是com\lvmama\emc\order\core
     * @param url     http://192.168.0.101:8081/nexus/content/groups/public/com/lvmama/emc/order/core/
     * @param path    E:\develop\maven-repository\com\lvmama\emc\order\core\   必须已存在
     */
    public void download(String url, String path, ExecutorService executorService){
        //1.解析url，获取底下的目录、文件名
        List<BatchDownloadMavenFiles.DownloadPair> downloadPairList = getDownloadPairs(url);
        if (downloadPairList == null){
            return;
        }



        //2.检查path，底下若有文件，删除
        checkPath(path);

        //3.循环子目，
        this.println(String.format("个数 %s", downloadPairList.size()));
        for(BatchDownloadMavenFiles.DownloadPair downloadPair : downloadPairList){


            if(downloadPair.isFile()){
                //3.1 若是文件，直接下载
                this.println(String.format("       任务 %s     文件名 %s", downloadPair.getUrl(), downloadPair.getElement()));
                this.println(String.format("       到   %s", path));
                this.println("");

                //下载方法，使用线程下载
//                FileDownloadTool.download( downloadPair.getUrl(), path, downloadPair.getElement());
                DownloadRunable downloadRunable = new DownloadRunable(downloadPair.getUrl(), path, downloadPair.getElement());
                executorService.execute(downloadRunable);


            }else if(downloadPair.isPath()){
                //3.2 若是文件夹，看本地是否有文件夹，没有则创建，建好后，递归本方法，url与path增加下级目录
                String pathName = downloadPair.getElement();
                pathName = path + pathName.substring(0, pathName.length()-1) +"\\";
//                this.println(pathName);
                File file = new File(pathName);
                if(!file.exists()){
                    file.mkdirs();
                }

                this.println(String.format("进入 %s", downloadPair.getUrl()));
                this.println(String.format("到   %s", pathName));
                this.println("");

                this.download(downloadPair.getUrl(), pathName, executorService);

            }
            //3.3底下无内容，则返回


        }
    }

    private void checkPath(String path) {
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        File[] files = file.listFiles();
        for(File f : files){
            if(f.isFile()){
                f.deleteOnExit();
            }

            f.length();
        }
    }

    /**
     * 获取下载路径，与路径名
     * @param url
     * @return
     */
    private List<BatchDownloadMavenFiles.DownloadPair> getDownloadPairs(String url) {
        String html = "";
        try {

            System.out.println(String.format("reading [%s]", url));
            HttpClientResult result = HttpClientUtils.doGet(url);
            html = result.getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtil.isEmptyString(html)){
            return null;
        }
        List<BatchDownloadMavenFiles.DownloadPair> downloadPairList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Iterator<Element> i = doc.select("a").iterator();
        while (i.hasNext()){
            Element element = i.next();
            if(null == element.childNodes() || 0 >= element.childNodes().size()){
                continue;
            }
            if("Parent Directory".equals(element.childNodes().get(0).toString())){
                continue;
            }

            BatchDownloadMavenFiles.DownloadPair downloadPair = new BatchDownloadMavenFiles.DownloadPair();
            downloadPair.setUrl(element.attr("href"));
            downloadPair.setElement(element.childNodes().get(0).toString());
            downloadPairList.add(downloadPair);
        }
//
//        for(DownloadPair downloadPair : downloadPairList){
//            System.out.println("======================================================");
//            System.out.print(downloadPair.getUrl());
//            System.out.println("               "+downloadPair.getPath());
//        }
        return downloadPairList;
    }

    public void println(String s){
        System.out.println(s);
    }

    class DownloadPair{
        private String url;
        private String element;
        /**
         * FILE  文件
         * PATH  文件夹
         */
        private String type;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getElement() {
            return element;
        }

        public void setElement(String element) {
            this.element = element;
            if(element.endsWith("/")){
                setType("PATH");
            }else{
                setType("FILE");
            }
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isFile(){
            return "FILE".equals(this.type);
        }

        public boolean isPath(){
            return "PATH".equals(this.type);
        }
    }




}
