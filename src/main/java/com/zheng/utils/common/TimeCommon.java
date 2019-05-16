package com.zheng.utils.common;

import java.text.SimpleDateFormat;

public class TimeCommon {
	 //日期
	 public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	 //时间
	 public	static SimpleDateFormat TIMESDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 //上班时间
	 public static SimpleDateFormat TOWORKSDF = new SimpleDateFormat("yyyy-MM-dd 08:30:00");
	 //下班时间
	 public static SimpleDateFormat OFFWORKSDF = new SimpleDateFormat("yyyy-MM-dd 17:30:00");
	 //晚上时间
	 public static SimpleDateFormat NIGHTSDF = new SimpleDateFormat("yyyy-MM-dd 20:00:00");
	 //上午时间
	 public static SimpleDateFormat AMSDF = new SimpleDateFormat("yyyy-MM-dd 08:00:00");
	 //正午时间
	 public static SimpleDateFormat NOONSDF = new SimpleDateFormat("yyyy-MM-dd 12:00:00");
	 //下午时间
	 public static SimpleDateFormat PMSDF = new SimpleDateFormat("yyyy-MM-dd 13:00:00");

}
