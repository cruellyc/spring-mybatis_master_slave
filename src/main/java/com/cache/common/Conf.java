package com.cache.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * @Title: Conf.java
 * @Package com.seed.common
 * @author wzj
 * @date 2014-6-19 下午4:32:54
 */
public class Conf {
	private static Map<String, Object> properties = new HashMap<String, Object>();

	public static int getSize() {
		return properties.size();
	}

	private static final Logger logger = Logger.getLogger(Conf.class);
	private HashMap<String, String> map;

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public void put(String key, String value) {
		this.getMap().put(key, value);
	}

	public void put(String key, int value) {
		this.getMap().put(key, String.format("%d", value));
	}
		/**
		 * @Title: loadProperties
		 * @Description: TODO(装载property)
		 * @return void 返回类型
		 * @param props
		 */
		public static void loadProperties(Properties props) {
			Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Object, Object> entry = it.next();
				String key = (String) entry.getKey();
				Object value = entry.getValue();
				properties.put(key, value);
				logger.info(key + "=" + value);
			}
			logger.debug("<" + properties.size() + ">");
			/*
			try {
				logger.info("\n"
						+ ASCIIArt.draw("SEEDTEC", "*", 110, 30, 10, 20, 21));
			} catch (IOException e) {
				logger.error("no this font skip.");
			}
			*/
		}
}
