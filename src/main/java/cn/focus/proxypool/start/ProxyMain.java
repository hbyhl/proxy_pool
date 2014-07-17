package cn.focus.proxypool.start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import cn.focus.proxypool.dao.ProxyPoolHisDAO;
import cn.focus.proxypool.model.ProxyPool;
import cn.focus.proxypool.model.ProxyPoolHis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

  
public class ProxyMain {  
	
	public final static String PROXY_URL = "http://focus-crawls.apps.sohuno.com/proxy/list";
	
	private final static int threadNum = 4;
	
	private static Map<Integer, List<ProxyPool>> allKeyMap = new HashMap<Integer, List<ProxyPool>>();
	
	private final static Logger logger = Logger.getLogger(ProxyMain.class);
	
	private static List<HttpHost> proxyList = new ArrayList<HttpHost>() {
        private static final long serialVersionUID = -3275380625027992912L;
        {
        	JSONObject obj = JSONObject.parseObject(getClient(PROXY_URL));
        	JSONArray array = obj.getJSONArray("data");
        	for (int i = 0 ; i < array.size(); i++) {
        		JSONObject host = array.getJSONObject(i);
        		add(new HttpHost(host.getString("ip"), host.getIntValue("port")));        		
        	}
        }
    };
    
	/**
	 * 获得proxy_list集合
	 * @param url
	 * @param params
	 * @return
	 */
    private static String getClient(String url) {		
		CloseableHttpClient client = HttpClientBuilder.create().build();
		String copyUrl = url;
		try {
			HttpGet get = new HttpGet(copyUrl);
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			
		} catch (IOException e) {
			
		}
		return "";
	}
	
    private static List<String> userAgentList = new ArrayList<String>() {
        private static final long serialVersionUID = -2649066726207050120L;
        {
            add(new String("Mozilla/5.0 (Windows; U; MSIE 7.0; Windows NT 6.0; en-US)"));
            add(new String("Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:15.0) Gecko/20100101 Firefox/15.0.1"));
            add(new String("Mozilla/5.0 (Windows NT 6.2; WOW64; rv:15.0) Gecko/20120910144328 Firefox/15.0.2"));
            add(new String("Opera/9.80 (X11; Linux x86_64; U; fr) Presto/2.9.168 Version/11.50"));
            add(new String("Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; de) Presto/2.9.168 Version/11.52"));
            add(new String("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; Sleipnir/2.9.8)"));
            add(new String("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.0; Trident/5.0)"));
            add(new String("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)"));
            add(new String("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8) AppleWebKit/536.25 (KHTML, like Gecko) Version/6.0 Safari/536.25"));
            add(new String("Opera/9.60 (Windows NT 5.1; U; ja) Presto/2.1.1"));
            add(new String("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)"));
            add(new String("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; EasyBits GO v1.0; InfoPath.1; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)"));
            add(new String("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; Sleipnir/2.9.8)"));
            add(new String("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.92 Safari/537.1 LBBROWSER"));
            add(new String("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2; .NET4.0C; .NET4.0E)"));
            add(new String("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2; .NET4.0C; .NET4.0E)"));
            
        }
    };
    
    private static List<String> urlList = new ArrayList<String>() {
        private static final long serialVersionUID = -2649066726207050120L;
        {
            add(new String("sina"));
            add(new String("house365"));
            add(new String("anjuke"));
            add(new String("soufun"));
        }
    };
  
    public static void main(String[] args) throws ClientProtocolException,  
            IOException {  
    	
    	/*RoseAppContext ctx = new RoseAppContext();
    	ShardedRWRedisPool shardedRWRedisPool = ctx.getBean(ShardedRWRedisPool.class);
    	
    	ShardedJedis jedis = shardedRWRedisPool.borrowWriteResource();
    	Long rr = shardedRWRedisPool.borrowWriteResource().setnx("proxy_pool:his_thread_flag", "ING");
    	System.out.println("rr is " + rr);*/
    	/*shardedRWRedisPool.borrowWriteResource().del("proxy_pool:his_thread_flag");
    	System.out.println("out is ");*/
    	/*CacheVo cacheVo = new CacheVo();
    	cacheVo.setName("soufun");
    	cacheVo.setUrl("www.soufun.com");
    	jedis.set("fox_test", CacheUtil.encode(cacheVo));
    	String res = jedis.get("fox_test");
    	CacheVo resVo = (CacheVo)CacheUtil.decode(res);
    	System.out.println("name is " + resVo.getName() + " url is " + resVo.getUrl());*/
    	
    	/*RoseAppContext ctx = new RoseAppContext();
    	UserAgentDAO dao = ctx.getBean(UserAgentDAO.class);
    	
    	for(String str:userAgentList){
    		UserAgent userAgent = new UserAgent();
    		userAgent.setUrl(str);
    		userAgent.setType("PC");
    		dao.save(userAgent);
    	}*/
    	
    	/*RoseAppContext ctx = new RoseAppContext();
    	ProxyPoolFlagDAO dao = ctx.getBean(ProxyPoolFlagDAO.class);
    	
		ProxyPoolFlag proxyPoolFlag = new ProxyPoolFlag();
		proxyPoolFlag.setFlag("proxy_pool_flag");
		proxyPoolFlag.setStatus("ed");
		dao.save(proxyPoolFlag);
		
		ProxyPoolFlag proxyPoolFlag1 = new ProxyPoolFlag();
		proxyPoolFlag1.setFlag("proxy_pool");
		proxyPoolFlag1.setStatus("ed");
		dao.save(proxyPoolFlag1);*/
    	
    	/*RoseAppContext ctx = new RoseAppContext();
    	ProxyUrlsDAO dao = ctx.getBean(ProxyUrlsDAO.class);
  
    	RoseAppContext factory = new RoseAppContext();
    	UrlsVo urlsVo = (UrlsVo) factory.getBean("configBean");
    	
    	Map<String, String> urlMap = urlsVo.getUrlMap();
    	ProxyUrls proxyUrls = new ProxyUrls();
    	
    	int i=0;
    	for(Map.Entry<String, String> entry : urlMap.entrySet()){
    		proxyUrls.setId(i++);
    		proxyUrls.setName(entry.getKey());
    		proxyUrls.setUrl(entry.getValue());
    		dao.save(proxyUrls);
    		System.out.println("map key is " + entry.getKey());
    	}
    	*/
    	
     	RoseAppContext ctx = new RoseAppContext();
     	ProxyPoolHisDAO dao = ctx.getBean(ProxyPoolHisDAO.class);
    	
    	for(HttpHost httpHost:proxyList) {
    			ProxyPoolHis proxyPoolHis = new ProxyPoolHis();
    			proxyPoolHis.setIp(httpHost.getHostName());
    			proxyPoolHis.setPort(httpHost.getPort());
            	proxyPoolHis.setStatus(0);
            	proxyPoolHis.setLevel(1);
            	proxyPoolHis.setFailures(0);
            	Date date = new Date();
            	proxyPoolHis.setCreatetime(date);
            	proxyPoolHis.setUpdatetime(date);
            	proxyPoolHis.setSource("SC");
        		
        		dao.save(proxyPoolHis);
    	}
    	
    	/*for(HttpHost httpHost:proxyList) {
			ProxyPoolHis proxyPoolHis = new ProxyPoolHis();
			proxyPoolHis.setIp(httpHost.getHostName());
			proxyPoolHis.setPort(httpHost.getPort());
        	proxyPoolHis.setStatus(0);
        	proxyPoolHis.setLevel(1);
        	proxyPoolHis.setFailures(0);
        	Date date = new Date();
        	proxyPoolHis.setCreatetime(date);
        	proxyPoolHis.setUpdatetime(date);
        	proxyPoolHis.setSource("SC");
        	
        	int ret = HttpClientUtil.testCommonHttp(httpHost.getHostName(), httpHost.getPort(), "anjuke", userAgentList);
    	}
*/
    	/*String timeout = PropertiesUtil.getHttpTimeout();
    	System.out.println("out is " + timeout);*/
    	
    	/*for(int i=0; i< 5; i++){
			List<ProxyPool> keyList = new ArrayList<ProxyPool>();
			allKeyMap.put(i, keyList);
		}
    	
    	RoseAppContext ctx = new RoseAppContext();
     	ProxyPoolDAO dao = ctx.getBean(ProxyPoolDAO.class);
    	List<ProxyPool> proxyList = dao.getUsableList(1);
    	
    	int len = proxyList.size()/5 + 1;
    	TaskThread thrlist[] = new TaskThread[5];
    	if(len != 0) {
    		int x=0, count=0;
        	for(ProxyPool proxy : proxyList){
    			if(x!=0 && x<len*5){
    				if(x%len ==0){
    					thrlist[count] = new TaskThread(allKeyMap.get(count));
						thrlist[count].start();
    			    	count++;
    				}
    			}
    			allKeyMap.get(count).add(proxy);
    			x ++;
    		}
        	thrlist[count] = new TaskThread(allKeyMap.get(count));
        	thrlist[count].start();
	    	count++;
	    	for(int i=0; i<count; i++){
				try {
					thrlist[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
    	}*/
    }  
}  