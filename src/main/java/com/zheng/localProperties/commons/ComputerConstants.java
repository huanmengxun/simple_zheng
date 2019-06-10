package com.zheng.localProperties.commons;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.zheng.utils.mylog.MyLoggerInfo;

/**
 * 功能描述：电脑常量，需要调用方法进行获取
 * 
 * @Package: com.zheng.utils.common
 * @author: zheng
 * @date: 2019年5月18日 下午1:21:42
 */
public class ComputerConstants {
	static MyLoggerInfo log = MyLoggerInfo.getInstance();

	/**
	 * 功能描述：获取对外ip
	 *
	 * @author: zheng
	 * @date: 2019年5月18日 下午1:23:50
	 * @return
	 */
	public static String getLocalIp() {
		try {
			InetAddress address = InetAddress.getLocalHost();
			log.info("本机地址{}", address);
			return address.getHostAddress();
		} catch (UnknownHostException e) {
//			log.warn("错误信息{}",e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> getLocalIps() {
		List<String> ipList = new ArrayList<>();
		Enumeration<NetworkInterface> allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress address = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					address = (InetAddress) addresses.nextElement();
					if (address != null && address instanceof Inet4Address) {
						log.info("本机地址：{}", address);
						ipList.add(address.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			log.warn("错误信息{}", e.getMessage());
			e.printStackTrace();
		}
		return ipList;
	}
}
