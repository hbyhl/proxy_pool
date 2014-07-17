package cn.focus.proxypool.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class PropertiesUtil {
	
	private final static Logger log = Logger.getLogger(PropertiesUtil.class);
	
	private static Properties p = null;
	
	static {
		InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("common.properties");  
	    p = new Properties(); 
        try {
			p.load(inputStream);
			inputStream.close();  
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}   
	}
	
	public static Integer getHttpTimeout() {
		 return Integer.parseInt(p.getProperty("HttpClient.Timeout").trim());
	}
}  



