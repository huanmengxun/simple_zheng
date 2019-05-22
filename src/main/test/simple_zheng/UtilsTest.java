package simple_zheng;

import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.mysql.cj.xdevapi.Result;
import com.zheng.utils.common.ComputerConstants;
import com.zheng.utils.dataUtil.JDBCUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UtilsTest {
	/**
	 * 功能描述：测试电脑端常量测输出
	 *
	 * @author: zheng
	 * @throws SocketException
	 * @date: 2019年5月18日 下午1:12:54
	 */
	@Test
	public void testComputerConstantsOutput() throws SocketException {
		ComputerConstants.getLocalIp();
		ComputerConstants.getLocalIps();
	}

	/**
	 * 功能描述：测试数据库连接
	 *
	 * @author: zheng
	 * @date: 2019年5月19日 下午10:17:48
	 * @throws SocketException
	 */
	@Test
	public void testDatabaseLink() throws SocketException {
		Connection conn = JDBCUtils.getMysqlConn(null, null, "", null, "huan");
		String sql = "show databases";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				log.info("查询值为{}",rs.getString(1));
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
