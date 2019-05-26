package com.zheng.localProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.alibaba.fastjson.JSON;

//1.JYAML
//
//2.SnakeYAML
//
//3.YamlBeans
public class PaerseYmal {

	private final static DumperOptions OPTIONS = new DumperOptions();
	static {
		// 将默认读取的方式设置为块状读取
		OPTIONS.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
	}

	public void read() {
		File file = new File("src/main/resources/zhengApplication.yml");
		Yaml yaml = new Yaml();
		// 读入文件
		// load方法支持String，InputStream，Reader作为输入
		try {
			Map<String,Object> result = yaml.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("dataType1", "mysql2");
			String appFilePath = "src/main/resources/zhengApplication.yml";
			addIntoYml(appFilePath,"dataSource.dbName", "param");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Map<String,Object> addMap(Map<String, Object> dataMap,String key, Object value){
		int firstPoint=key.indexOf('.');
		String firstKey=key.substring(0, firstPoint);
		String subKey=key.substring(firstPoint+1);
		
		Map<String,Object> param=new HashMap<>();
		if(firstPoint!=-1&&firstPoint<key.length()) {
			if(dataMap.get(firstKey)!=null) {
				param=(Map<String,Object>)dataMap.get(firstKey);
				param.put(firstKey, addMap(param, subKey, value));
			}else {
				param.put(firstKey, addMap(new HashMap<String,Object>(), firstKey, value));
			}
		}else {
			param.put(key, value);
		}

		return param;
	}
	
	public static void addIntoYml(String appFilePath, String key, Object value) throws IOException {
		
		File dest = new File(appFilePath);
		Yaml yaml = new Yaml(OPTIONS);
		// 载入当前yml文件
		LinkedHashMap<String, Object> dataMap = yaml.load(new FileReader(dest));
		// 如果yml内容为空,则会引发空指针异常,此处进行判断
		if (null == dataMap) {
			dataMap = new LinkedHashMap<>();
		}
		int firstPoint=key.indexOf('.');
		if(firstPoint!=-1&&firstPoint<key.length()) {
			Map<String,Object> param=addMap(dataMap, key, value);
			System.out.println(param);
			String firstKey=key.substring(0, firstPoint-1);
			dataMap.put(firstKey, param.get(firstKey));
		}else {
			dataMap.put(key, value);
		}
		// 将数据重新写回文件
		yaml.dump(dataMap, new FileWriter(dest));
	}

}
