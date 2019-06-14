package com.zheng.utils.tool.myProp.impl;

import java.util.Map;

public interface MyConfig {
	
	public Map<Object, Object> getConfigMsg();

	public Map<Object, Object> getConfigMsg(String configPath);

	public Object getConfigValueByKey(String key);

	public void removeConfigByKey(String key);

	public void removeConfigByKey(String configPath, String key);

	public void addKeyIntoConfig(String key, Object value);

	public void addKeyIntoConfig(String configPath, String key, Object value);
}
