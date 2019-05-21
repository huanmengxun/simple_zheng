package com.zheng.utils.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 功能描述：自动以数据类型
 * 
 * @Package: com.zheng.utils.common
 * @author: zheng
 */
@Data
public class ResultData {
	/**
	 * 返回提示信息
	 */
	public String message;
	/**
	 * 是否成功
	 */
	public Boolean isSucceed = true;
	/**
	 * 错误码
	 */
	public int errCode = 200;
	/**
	 * list对象装填
	 */
	public List<Object> resultList=new ArrayList<>();
	/**
	 * map对象装填
	 */
	public Map<Object,Object> mapKey=new HashMap<>();

	public ResultData() {
	}
	public ResultData(String message,Boolean isSucceed) {
		this.message=message;
		this.isSucceed=isSucceed;
	}
	
}
