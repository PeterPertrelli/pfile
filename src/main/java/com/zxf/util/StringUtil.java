package com.zxf.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuxiangfei
 * @Description:
 * @date 2018/11/24
 */
public class StringUtil {

    private StringUtil() {
    }

    public static boolean isEmptyString(String str) {
        return str == null ? true : str.trim().equals("");
    }

    public static boolean isNotEmptyString(String str) {
        return !isEmptyString(str);
    }

    public static List<Long> strConverList(String str) {
        List<Long> idList = null;
        if (isNotEmptyString(str)) {
            idList = new ArrayList();
            String[] ids = str.split(",");
            if (null != ids && ids.length > 0) {
                String[] var3 = ids;
                int var4 = ids.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    String item = var3[var5];
                    if (isNotEmptyString(item)) {
                        idList.add(Long.valueOf(item));
                    }
                }
            }
        }

        return idList;
    }
    private static final String POSITIVE_INTEGER_ZEQ = "^[1-9]\\d*$";
    public static boolean isNumber(String str){
        if(isEmptyString(str)){
            return false;
        }else {
            return str.matches(POSITIVE_INTEGER_ZEQ);
        }
    }
}
