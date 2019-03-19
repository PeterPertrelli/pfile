package com.zxf.demo;

import com.zxf.machine.ReadAnalyseProcessMachin;
import com.zxf.process.AnalyseProcess;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2019/3/19
 */
public class CountGoods implements AnalyseProcess {

    public static void main(String[] args) {
        CountGoods extractor = new CountGoods();
        ReadAnalyseProcessMachin.exec(extractor);
    }

    /**
     * 对读取到的一行进行处理
     *
     * @param line
     * @param fileName
     */
    @Override
    public void prepareData(String line, String fileName) {

        String goodsId = "";

        String flag = "AA";

        if(line.contains("查询云链客小程序二维码START")){
            //erId=31154;goodsId=10915581[LvLogE TIME:"15529605
            goodsId = line.substring(line.indexOf("goodsId=")+8,line.indexOf("[LvLogE TIME"));
            flag = "Start";
        }else if(line.contains("查询云链客小程序二维码END")){
            //eUnlimit-wxc9b70af370d8f724-31154-10915581.jpg[LvLogE TIME:"155296
            goodsId = line.substring(line.indexOf("wxc9b70af370d8f724-")+8,line.indexOf("[LvLogE TIME"));
            goodsId = goodsId.substring(goodsId.lastIndexOf("-")+1,goodsId.indexOf(".jpg"));
            flag = "end";
        }

        Goods goods = goodsMap.get(goodsId);
        if(null == goods){
            goods = new Goods();
            goods.setGoodsId(goodsId);
            goodsList.add(goods);
            goodsMap.put(goodsId, goods);
        }
        if("Start".equals(flag)){
            goods.setStart("Y");
        }else if("end".equals(flag)){
            goods.setEnd("Y");
        }

    }

    private List<Goods> goodsList = new ArrayList<>();
    private Map<String, Goods> goodsMap = new HashMap<>();


    class Goods{
        private String goodsId;
        private String start = "N";
        private String end = "N";

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
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
        fw.write("==================start===================");
        for(Goods goods : goodsList){
            if("Y".equals(goods.getStart()) && "Y".equals(goods.getEnd())){
                continue;
            }
            fw.write(goods.getGoodsId()+"\r\n");
        }
        fw.write("==================end===================");

    }

    /**
     * 用于获取输出文件路径
     *
     * @return 输出文件路径
     */
    @Override
    public String prepareResultFilePath() {
        return "F:/线上log/mec-b2b-scenic/ccccc.txt";
    }

    /**
     * 源文件路径
     *
     * @return 源文件路径
     */
    @Override
    public List<String> prepareSrcFileListPath() {
        List<String> fileList = new ArrayList<String>();
        fileList.add("F:/线上log/mec-b2b-scenic/catalina.out");
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
        return line.contains("查询云链客小程序二维码");
    }
}
