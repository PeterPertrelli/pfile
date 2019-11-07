package com.zxf;

import static org.junit.Assert.assertTrue;

import com.zxf.demo.AFileAnalyser;
import com.zxf.machine.ReadAnalyseProcessMachin;
import com.zxf.util.StringUtil;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    public static void main(String[] args) {
                             //imit-wxc9b70af370d8f724-27913-935950.jpg[LvLogE TIME
        String line = "o-8088-exec-2] (TicketSearchServiceImpl.java:256) - 过滤成人票之前的商品数量：1[LvLogE TIME:\"1566";

        String goodsId = line.substring(line.indexOf("(TicketSearchServiceImpl"),line.indexOf("[LvLogE TIME"));
        System.out.println(goodsId);
//        goodsId = goodsId.substring(goodsId.lastIndexOf("-")+1,goodsId.indexOf(".jpg"));
//        System.out.println(goodsId);
//        System.out.println(String.format("tets %4s    %4d    %5d","abc", 123,123));
//        System.out.println(String.format("tets %4s    %4d    %5d","ab", 12,1232));
//        System.out.println(String.format("tets %4s    %4d    %5d","abc3", 1,123));
//        System.out.println(String.format("tets %4s    %4d    %5d","abc", 12,12));

    }

}
