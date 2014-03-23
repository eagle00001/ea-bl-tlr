package com.ea.bl.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-4-5
 */
public class FileLoadUtils {
	private static final Logger log = LoggerFactory.getLogger(FileLoadUtils.class);
	
	/**
	 * load a file in following sequences
	 * 1. load in current classpath. Or next step.
	 * 2. load by current thread classloader. Or next step.
	 * 3. load by system classloader. Or next step.
	 * 4. load file with absolute path. Or load file failed.
	 * @param filePath
	 * @return
	 */
	public static InputStream loadFile(String filePath){
		if(StringUtils.isBlank(filePath))
			throw new IllegalArgumentException("The filePath can not be null");
		InputStream is = null;
		try {
			is = FileLoadUtils.class.getResourceAsStream(filePath);
			if(is==null)
				is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
			if(is==null)
				is = ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
			if(is==null)
				is = new FileInputStream(filePath);
		} catch (Exception e) {
			log.error("load file["+filePath+"] error:",e);
		}
		return is;
	}
	/**
	 * get file with properties type
	 * @see FileLoadUtils#loadFile(String)
	 * @param filePath
	 * @return
	 */
	public static Properties loadFileWithProperties(String filePath){
		Properties p = new Properties();
		InputStream is = loadFile(filePath);
		try {
			if(is!=null)
				p.load(is);
		} catch (IOException e) {
			log.error("load file["+filePath+"] error:",e);
		}
		return p;
	}
	/**
	 * @see FileLoadUtils#loadFileWithProperties(String)
	 * @param filePath
	 * @return
	 */
	public static Map<Object,Object> loadFileWithMap(String filePath){
		Properties p = loadFileWithProperties(filePath);
		if(p==null){
			return new HashMap<Object,Object>();
		}
		Map<Object,Object> map = new HashMap<Object,Object>(p.size());
		for(Entry<Object,Object> entry:p.entrySet()){
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
