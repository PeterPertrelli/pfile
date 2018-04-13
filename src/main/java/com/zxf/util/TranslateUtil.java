package com.zxf.util;

import java.math.BigDecimal;

public class TranslateUtil {
	
	public static String formatSizeStr(BigDecimal sizeNum){
		BigDecimal diviNum = new BigDecimal(1024);
		
		//byte
		BigDecimal[] resultsK = sizeNum.divideAndRemainder(diviNum);
		String bytes = resultsK[1].toString()+" byte ";
		if(BigDecimal.ZERO.equals(resultsK[0])){
			return bytes;
		}
		
		//kb
		BigDecimal[] resultsM = resultsK[0].divideAndRemainder(diviNum);
		String kb = resultsM[1].toString()+" K ";
		if(BigDecimal.ZERO.equals(resultsM[0])){
			return kb+bytes ;
		}
		
		//mb
		BigDecimal[] resultsG = resultsM[0].divideAndRemainder(diviNum);
		String mb = resultsG[1].toString()+" M ";
		if(BigDecimal.ZERO.equals(resultsG[0])){
			return mb+kb+bytes ;
		}
		//gb

		String gb = resultsG[0].toString()+" G ";
		
		return gb+mb+kb+bytes ;
	}

	public static String formatMs(long msTime) {

		String ms = msTime % 1000 + " ms";
		if (msTime / 1000 == 0) {
			return ms;
		}

		long sTime = msTime / 1000;
		String s = sTime % 60 + " s ";
		if (sTime / 60 == 0) {
			return s + ms;
		}

		long mTime = sTime / 60;
		String m = mTime % 60 + " m ";
		if (mTime / 60 == 0) {
			return m + s + ms;
		}

		long hTime = mTime / 60;
		String h = hTime % 60 + " h ";
		if (hTime / 24 == 0) {
			return h + m + s + ms;
		}

		String d = hTime / 24 + " d ";

		return d + h + m + s + ms;

	}

}
