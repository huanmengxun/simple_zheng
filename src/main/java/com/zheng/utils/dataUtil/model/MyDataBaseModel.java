package com.zheng.utils.dataUtil.model;

import lombok.Data;

/**
 * 功能描述： 表主要字段
 * 
 * @Package: com.zheng.utils.dataUtil.model
 * @author: zheng
 */
@Data
public class MyDataBaseModel {
	/**
	 * 数据库类型
	 */
	private String dateType;
	/**
	 * 数据连接端口号
	 */
	private String port;
	/**
	 * 数据库连接？ip
	 */
	private String dataIp;
	/**
	 * 数据库名称
	 */
	private String dbName;
	/**
	 * 数据库账号名称
	 */
	private String username;
	/**
	 * 数据库密码
	 */
	private String password;
}
