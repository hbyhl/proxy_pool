package cn.focus.proxypool.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLContext;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;

import cn.focus.proxypool.model.ProxyPool;
import cn.focus.proxypool.vo.UrlsVo;

public class HttpClientUtil {
	
	private static HttpClientBuilder builder = null;
	private final static Logger log = Logger.getLogger(HttpClientUtil.class);

	static {
		
		SSLContext sslcontext = SSLContexts.createSystemDefault();
        X509HostnameVerifier hostnameVerifier = new BrowserCompatHostnameVerifier(); 
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
		            .register("http", PlainConnectionSocketFactory.INSTANCE)
		            .register("https", new SSLConnectionSocketFactory(sslcontext, hostnameVerifier))
		            .build();

		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);
		connManager.setMaxTotal(8000);
		connManager.setDefaultMaxPerRoute(2000);
		
		 RequestConfig defaultRequestConfig = RequestConfig.custom()
		            .setCookieSpec(CookieSpecs.BEST_MATCH)
		            .setExpectContinueEnabled(true)
		            .setStaleConnectionCheckEnabled(true)
		            .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
		            .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
		            .build();
		 
        builder = HttpClients.custom().setConnectionManager(connManager)
                .setDefaultRequestConfig(defaultRequestConfig);
	}
    
    public static String getUrl(String name) {
    	RoseAppContext factory = new RoseAppContext();
    	UrlsVo urlsVo = (UrlsVo) factory.getBean("configBean");
    	
    	Map<String, String> urlMap = urlsVo.getUrlMap();
    	return urlMap.get(name);
    }
    
    public static List<ProxyPool> testProxyHttp(List<ProxyPool> proxyList, List<String> uagentList) throws ClientProtocolException, IOException {
    	List<ProxyPool> resList = new ArrayList<ProxyPool>();
		for(ProxyPool proxy : proxyList) {
			ProxyPool newproxy = new ProxyPool();
			newproxy.copy(proxy);
			int ret = testCommonHttp(proxy.getIp(), proxy.getPort(), proxy.getUrlname(), uagentList);
			newproxy.setStatus(ret);
			resList.add(newproxy);
		}
		return resList;
    }
    
   /* public static List<ProxyPool> testProxyHisHttp(List<ProxyPoolHis> proxyhisList, List<ProxyUrls> proxyUrlsList, List<String> uagentList) throws ClientProtocolException, IOException {
    	List<ProxyPool> resList = new ArrayList<ProxyPool>();
		for(ProxyPoolHis proxyHis : proxyhisList) {
			for(ProxyUrls ProxyUrls : proxyUrlsList) {
				int ret = testCommonHttp(proxyHis.getIp(), proxyHis.getPort(), ProxyUrls.getName(), uagentList);
				ProxyPool proxy = ProxyPool.getInstantce(proxyHis);
				proxy.setStatus(ret);
				proxy.setUrlname(ProxyUrls.getName());
				resList.add(proxy);
			}
		}
		return resList;
    }*/
	
	public static int testCommonHttp(String ip, int port, String urlname, List<String> uagentList) {
           
		String url = getUrl(urlname);
		CloseableHttpClient httpclient = null;
		try {
			
			RequestConfig defaultRequestConfig = RequestConfig.custom().build();     
	        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
	        		.setConnectionRequestTimeout(PropertiesUtil.getHttpTimeout())
	                .setConnectTimeout(PropertiesUtil.getHttpTimeout())
	                .setSocketTimeout(PropertiesUtil.getHttpTimeout())
	                .setProxy(new HttpHost(ip, port))
	                .build();
	        
	        //随机选取一个useragent
	        Random rand = new Random();
	        int randNum = rand.nextInt(uagentList.size());
	        String uagent = uagentList.get(randNum);
	        
	        builder.setUserAgent(uagent);	   
	        httpclient = builder.build();
        	
    		if(null != url) {
    			HttpGet httpget = new HttpGet(url);
    	        httpget.setConfig(requestConfig);
    	        
    	        CloseableHttpResponse response = httpclient.execute(httpget);
    	        int status = response.getStatusLine().getStatusCode(); 
    	        
    	        if(status == HttpStatus.SC_OK) {
    	        	HttpEntity entity = response.getEntity();
    	            if (entity != null) {
    	            	return 1;
    	            } 
    	        }
    		}
    		
        } catch (Exception e) {
        	log.error("<proxy:HttpClientUtil> ip is " + ip + " port is " + port + " urlname is " + url + " has an error, error message is " + e.getMessage(), e);
        	System.out.println(e);
		}finally {

		}
       
		return -1;
	}
}