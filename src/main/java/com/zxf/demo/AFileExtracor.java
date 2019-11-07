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


    public static void main(String[] args) {
        AFileExtracor extractor = new AFileExtracor();
        ReadSimultaneousProcessMachine.exec(extractor);
    }
    /**
     * 用于获取输出文件路径
     *
     * @return 输出文件路径
     */
    @Override
    public String prepareResultFilePath() {
        return "F:/线上log/emc-weshop-front/1111111.txt";
    }

    /**
     * 用于后去源文件路径
     *
     * @return 源文件路径
     */
    @Override
    public List<String> prepareSrcFileListPath() {
        List<String> fileList = new ArrayList<String>();
//        fileList.add("F:/线上log/mec-b2b-scenic/catalina.out");
//        fileList.add("F:/线上log/mec-b2b-scenic/catalina (1).out");

        fileList.add("F:/线上log/emc-weshop-front/3catalina.out");
        fileList.add("F:/线上log/emc-weshop-front/4catalina.out");
        fileList.add("F:/线上log/emc-weshop-prod/prod3catalina.out");
        fileList.add("F:/线上log/emc-weshop-prod/prod4catalina.out");
//        fileList.add("F:/线上log/emc-weshop-client/catalina.out-2019-08-15");
//        fileList.add("F:/线上log/emc-weshop-client/catalina (1).out-2019-08-15");
//        fileList.add("F:/线上log/emc-weshop-gateway/catalina.out");

//        fileList.add("F:/线上log/emc-weshop-client/catalina.out");
//        fileList.add("F:/线上log/emc-weshop-client/catalina (1).out");
//        fileList.add("F:/线上log/emc-wesohp-member/catalina.out");
//        fileList.add("F:/线上log/emc-wesohp-member/catalina (1).out");
//        fileList.add("F:/线上log/emc-weshop-front/catalina (1).out-2018-10-18");
//        for(int i = 1; i <= 16; i++){
//			fileList.add("F:/线上log/scenic_back/catalina("+i+").out");
//		}
        return fileList;
    }

    private boolean can = false;

    /**
     * 是否对读取到的文件内容进行输出
     *
     * @param line
     * @return
     */
    @Override
    public boolean need(String line) {
        if(line.contains("22ec9e3052119af")
                 || line.contains("100102860")
//                 || line.contains("商品查询参数")
//                 || line.contains("订单列表缓存预加载")
//                 && line.contains("o4yzB1AAicLfk5bD2CfzQwlElzlw")
//                 && line.contains("2598042")
                ){
            return true;
        }else {
            return false;
        }

//        return can;
//        return true;
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

//        if(line.startsWith("2018-10-28 07:39:52,814 INFO  tnt_hotel_price_service")){
//            can = true;
//        }
//        if(line.startsWith("2018-10-28 07:40:21,750 INFO  tnt_hotel_price_service ")){
//            can = false;
//        }
//
//
//
//        if(can){
//
//            fw.write(line + "\r\n");
//        }
        //2019-04-30 14:53:01,523 INFO[LvLogS]emc-weshop-gateway [http-nio-8083-exec-10] (WeixinMiniController.java:258) - qrcodeFilename:
        // http://pic.lvmama.com/pics//uploads/pc/place2/2019-04-30-3a49d5da-05c0-4e9a-b226-21ed82dacc77/goodsActivityCodeUnlimit-wxc9b70af370d8f724-21933-12120264.jpg
        // [LvLogE TIME:"1556607181523" LVL:"INFO" APP:"emc-weshop-gateway" PAPP:"172.20.4.96" HOST:"server9-221" track:"2290499301005fe" CLASS:"com.lvmama.emc.weshop.gateway.web.WeixinMiniController"]
//imit-wxc9b70af370d8f724-27913-935950.jpg[LvLogE TIME


//        line = line.substring(line.indexOf("(TicketProductCacheController"),line.indexOf("[LvLogE TIME"));


        fw.write(line + "\r\n");






    }

}
