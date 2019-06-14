package com.zheng;

import java.util.HashMap;
import java.util.Map;

import com.zheng.utils.tool.mylog.MyLoggerInfo;

public class AppTest {
	static MyLoggerInfo log = MyLoggerInfo.getInstance();

	public static  String getSunheBaseOrg() {
		StringBuilder builder=new StringBuilder();
		builder.append("select * from base_org where rownum <= 10");
		String sql=builder.toString();
		return sql;
	}
	
	public static void main(String[] args) throws Exception {
		Map<Object,Object> map1=new HashMap<>();
		Map<String,Object> map2=new HashMap<>();
		Map<Integer,Object> map3=new HashMap<>();
		map1.put("asda", "c测试");
		map2.put("滴滴", "c测试");
		map3.put(123, "c测试");
		System.out.println(map1);
		System.out.println(map2);
		System.out.println(map3);
		map1.putAll(map2);
		System.out.println(map1);
//		System.out.println(map1.forEach(action););
//		map.put(key, value)
//		System.out.println("你好".hashCode());
//		System.out.println("你真的好".hashCode());
//		exportSqlTOLocal();
//		MyDataBaseConn conn=MyDataBaseConn.getInstance();
//		conn.getConnByProp();
//		String sql="select  ID, LEVELID, LEVELNAME, PARENTID, PATH, STATUS, CREATEUSER, CREATETIME, UPDATEUSER, UPDATETIME, ORGTYPENAME, ORGTYPEID, INCOLOR, DF7, DF10 from base_org where df7 like '%网格%' order by df10";
//		MyDataBaseOperator.exportSql(conn,
//				"F://科立//org2.sql", "base_org", 
//				sql);

		
//		conn.query("select * from sys_user");
//		String querySql="SELECT * FROM (SELECT ROW_.*, ROWNUM ROWNUM_ FROM (SELECT * FROM sys_user) ROW_ WHERE ROWNUM <= 20) WHERE ROWNUM_ >= 10";
//		List list=conn.query(sql);
		
//		System.out.println(list.get(1));
//		System.out.println(JSON.toJSON(MyDataBaseComQuery.getTabAllField(conn, "base_org")));
		
//		conn.queryParam("select * from base_org where MAPBIGTYPENAME =?","网格/组");
//		System.out.println(JSON.toJSONString(MyDataBaseComQuery.getTabAllField("base_org")));
		
//		
	}
}
