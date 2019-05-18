package simple_zheng;

import java.net.SocketException;

import org.junit.Test;

import com.zheng.utils.common.ComputerConstants;

import lombok.extern.log4j.Log4j2;
@Log4j2
public class ConstantsTest {
	/**
	 * 功能描述：测试电脑端常量测输出
	 *
	 * @author: zheng
	 * @throws SocketException 
	 * @date: 2019年5月18日 下午1:12:54
	 */
	@Test
	public void testComputerConstantsOutput() throws SocketException {
//		log.debug("debug");
//		log.info("info");
//		log.warn("warn");
//		log.error("error");
		ComputerConstants.getLocalIp();
	}
}
