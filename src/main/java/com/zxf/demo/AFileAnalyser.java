package com.zxf.demo;

import com.zxf.machine.ReadAnalyseProcessMachin;
import com.zxf.process.AnalyseProcess;
import com.zxf.util.DateUtil;
import com.zxf.util.StringUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/12/29
 */
public class AFileAnalyser implements AnalyseProcess {
    public static void main(String[] args) {
        AFileAnalyser extractor = new AFileAnalyser();
        ReadAnalyseProcessMachin.exec(extractor);
    }

    /**
     * 用于获取输出文件路径
     *
     * @return 输出文件路径
     */
    @Override
    public String prepareResultFilePath() {
        return "F:/线上log/emc-weshop-client/123456.txt";
//        return "F:/线上log/emc-weshop-client/goodsindex1.txt";
    }

    /**
     * 源文件路径
     *
     * @return 源文件路径
     */
    @Override
    public List<String> prepareSrcFileListPath() {
        List<String> fileList = new ArrayList<String>();
        fileList.add("F:/线上log/emc-weshop-client/catalina.out-2019-09-19");
        fileList.add("F:/线上log/emc-weshop-client/catalina.out-2019-09-19-1");
//        fileList.add("F:/线上log/emc-weshop-client/catalina.out-2019-08-15");
//        fileList.add("F:/线上log/emc-weshop-client/catalina (1).out-2019-08-15");

//        fileList.add("F:/线上log/tnt_cps/catalina (4).out-2019-01-18");
//        fileList.add("F:/线上log/tnt_cps/catalina (4).out-2019-01-19");
//        fileList.add("F:/线上log/tnt_cps/catalina (4).out-2019-01-20");

//        fileList.add("F:/线上log/emc-weshop-front/catalina (1).out-2018-10-18");
//        for(int i = 1; i <= 16; i++){
//			fileList.add("F:/线上log/scenic_back/catalina("+i+").out");
//		}
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
        if(line.contains("ThreadFilter.java")
                && line.contains(".com")){
            return true;
        }else {
            return false;
        }
    }

    private List<String> urlList = new ArrayList<>();
    private Map<String, UrlCount> urlMap = new HashMap<>();

    /**
     * 对读取到的一行进行处理
     *
     * @param line
     * @param fileName
     */
    @Override
    public void prepareData(String line, String fileName) {

        try{
            boolean isStart = false;
            int subend = -1;
            if(line.contains("start[LvLogE")){
                isStart = true;
                subend = line.indexOf("start[LvLogE")-20;
            }else{
                subend = line.indexOf("end[LvLogE")-20;
            }
            String url = line.substring(line.indexOf("- http")+2,subend);

            String endStr = "";

            if(url.contains("/weshopclient/fs")){
                url = url.substring(0, url.indexOf("/fs")+4);
            }else if (url.contains(";jsessionid=")){
                url = url.substring(0, url.indexOf(";jsessionid="));
            }else if(StringUtil.isNumber(url.substring(url.lastIndexOf("/")+1, url.length()))){
                endStr = url.substring(url.lastIndexOf("/")+1, url.length());
                url = url.substring(0,url.lastIndexOf("/")+1);
            }

            String track = line.substring(line.indexOf("track:")+7, line.indexOf("CLASS:")-2);


            UrlCount count;

            if(!urlList.contains(url)){
                count = new UrlCount();
                count.setUrl(url);
                count.setCount(1L);
                urlList.add(url);
                urlMap.put(url, count);
            }else{
                count = urlMap.get(url);
                count.setCount(count.getCount()+1);
            }
            UrlCount.Duration duration = count.getDuration(track);
            duration.setEndStr(endStr);

            Date time = DateUtil.toDate(line.substring(0, 23), DateUtil.HHMMSSSSS1_DATE_FORMAT);

            if(isStart){
                duration.setStartTime(time);
            }else{
                duration.setEndTime(time);
            }
        }catch (Exception e){

            StringWriter aWriter1 = new StringWriter();
            e.printStackTrace(new PrintWriter(aWriter1));
            System.out.println("-----------------line-----------------");
            System.out.println(line);
            System.out.println("-----------------line-----------------");
            System.out.println("-----------------Exception-----------------");
            System.out.println(aWriter1.toString());
            System.out.println("-----------------Exception-----------------");

            throw e;
        }




    }

    /**
     * 对收集到的信息进行处理，然后进行打印
     *
     * @param fw
     * @throws IOException
     */
    @Override
    public void processAndPrint(FileWriter fw) throws IOException {

        long allCount = 0;
        for(String url : urlList){
            UrlCount count = urlMap.get(url);
            allCount+= count.getCount();
            count.cal();
        }

//        Collections.sort(urlList, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                UrlCount c1 = urlMap.get(o1);
//                UrlCount c2 = urlMap.get(o2);
//
//                return c2.getCount().intValue()-c1.getCount().intValue();
//            }
//        });
//
        fw.write("按照 调用量 排序：\r\n");
        Long current = 0L;
        for(String url : urlList){
            UrlCount count = urlMap.get(url);
            current += count.getCount();
            if(url.contains("weshopclient")){
                fw.write(String.format("%70s    count: %6d       eve: %5d ms      max: %5d ms      %s          %s\r\n",
                        url, count.getCount(), count.getEveDuration(), count.getMaxDuration(), ((float)count.getCount()/(float)allCount),
                        ((float)current/(float)allCount)));

            }else{
                fw.write(String.format("%70s    count: %6d       eve: %5d ms      max: %5d ms      %s          %s\r\n",
                        url.replaceAll("www.yobab2b.com","www.yobab2b.com/weshopfront"),
                             count.getCount(), count.getEveDuration(), count.getMaxDuration(), ((float)count.getCount()/(float)allCount),
                        ((float)current/(float)allCount)));
            }



            count.cal();
        }
//
//
//        fw.write("\r\n按照 平均时间 排序：\r\n");
//        Collections.sort(urlList, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                UrlCount c1 = urlMap.get(o1);
//                UrlCount c2 = urlMap.get(o2);
//                Long a = c2.getEveDuration()-c1.getEveDuration();
//                return a.intValue();
//            }
//        });
//
//        for(String url : urlList){
//            UrlCount count = urlMap.get(url);
//            fw.write(String.format("%70s    count: %6d       eve: %5d ms      max: %5d ms  \r\n", url, count.getCount(), count.getEveDuration(), count.getMaxDuration()));
//        }


//        fw.write("\r\n按照 最小时间 排序：\r\n");
//        Collections.sort(urlList, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                UrlCount c1 = urlMap.get(o1);
//                UrlCount c2 = urlMap.get(o2);
//                Long a = c2.getMinDuration()-c1.getMinDuration();
//                return a.intValue();
//            }
//        });

//        for(String url : urlList){
//            UrlCount count = urlMap.get(url);
//
//            if(!url.contains("/weshopclient/order/getOrderBaseInfo")){
//                continue;
//            }
//
//            fw.write(String.format("%70s    count: %6d       eve: %5d ms      max: %5d ms   min: %5d ms   %s \r\n",
//                    url, count.getCount(), count.getEveDuration(), count.getMaxDuration(), count.getMinDuration(), count.getMaxDurationStr()));
//
////            if("http://www.yobab2b.com/weshopclient/ticket/index/".equals(url)){
//              if(url.contains("/weshopclient/order/getOrderBaseInfo")){
//                List<UrlCount.Duration> durationList = count.getDurationList();
//                Map<String, UrlCount.Duration> durationMap = count.getDurationMap();
//                Collections.sort(durationList, new Comparator<UrlCount.Duration>() {
//                    @Override
//                    public int compare(UrlCount.Duration o1, UrlCount.Duration o2) {
//                        Long a = o2.getDuration()-o1.getDuration();
//                        return a.intValue();
//                    }
//                });
//
//                for(UrlCount.Duration d : durationList){
//                    fw.write(String.format("%20s   \r\n", d.getDuration()));
//                }
//
//            }
//
//        }

//        fw.write("\r\n按照 最大时间 排序：\r\n");
//        Collections.sort(urlList, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                UrlCount c1 = urlMap.get(o1);
//                UrlCount c2 = urlMap.get(o2);
//                Long a = c2.getMaxDuration()-c1.getMaxDuration();
//                return a.intValue();
//            }
//        });
//
//        for(String url : urlList){
//            UrlCount count = urlMap.get(url);
//            fw.write(String.format("%70s    count: %6d       eve: %5d ms      max: %5d ms   min: %5d ms   %s \r\n",
//                    url, count.getCount(), count.getEveDuration(), count.getMaxDuration(), count.getMinDuration(), count.getMaxDurationStr()));
//
////            if("http://www.yobab2b.com/weshopclient/ticket/index/".equals(url)){
////
////                List<UrlCount.Duration> durationList = count.getDurationList();
////                Map<String, UrlCount.Duration> durationMap = count.getDurationMap();
////                Collections.sort(durationList, new Comparator<UrlCount.Duration>() {
////                    @Override
////                    public int compare(UrlCount.Duration o1, UrlCount.Duration o2) {
////                        Long a = o2.getDuration()-o1.getDuration();
////                        return a.intValue();
////                    }
////                });
////
////                for(UrlCount.Duration d : durationList){
////                    fw.write(String.format("%20s   \r\n", d.getDuration()));
////                }
////
////            }
//
//
//
//
//        }






    }

}
