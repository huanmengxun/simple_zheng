package com.zheng.utils.common;

import java.text.SimpleDateFormat;

/**
 * 将常用的一些有关系时间的格式化放置在此 功能描述：
 * 
 * @Package: com.zheng.utils.common
 * @author: zheng
 * @date: 2019年5月17日 下午2:44:30
 */
public class TimeCommon {
	// 日期
	public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	// 时间
	public static SimpleDateFormat TIMESDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 正午时间
	public static SimpleDateFormat NOONSDF = new SimpleDateFormat("yyyy-MM-dd 12:00:00");

}
