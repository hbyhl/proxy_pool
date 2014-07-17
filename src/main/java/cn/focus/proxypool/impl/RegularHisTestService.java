package cn.focus.proxypool.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import cn.focus.dc.commons.redis.ShardedRWRedisPool;
import cn.focus.proxypool.dao.ProxyPoolDAO;
import cn.focus.proxypool.dao.ProxyPoolFlagDAO;
import cn.focus.proxypool.dao.ProxyPoolHisDAO;
import cn.focus.proxypool.dao.ProxyUrlsDAO;
import cn.focus.proxypool.dao.UserAgentDAO;
import cn.focus.proxypool.model.ProxyPool;
import cn.focus.proxypool.model.ProxyPoolFlag;
import cn.focus.proxypool.model.ProxyPoolHis;
import cn.focus.proxypool.model.ProxyUrls;
import cn.focus.proxypool.utils.HttpClientUtil;

@Service
public class RegularHisTestService implements Runnable {
	
	private final static Logger log = Logger.getLogger(RegularHisTestService.class);
	
	private Map<Integer, List<ProxyPoolHis>> allKeyMap = new HashMap<Integer, List<ProxyPoolHis>>();
	
	@Autowired
	private ProxyPoolDAO proxyPoolDAO;
	
	@Autowired
	private ProxyPoolHisDAO proxyPoolHisDAO;
	
	@Autowired
	private UserAgentDAO userAgentDAO;
	
	@Autowired
	private ProxyUrlsDAO proxyUrlsDAO;
	
	@Autowired
	private UserAgentDAO ProxyPoolFlagDAO;
	
	@Autowired
	private ProxyPoolFlagDAO proxyPoolFlagDAO;
	
	@Autowired
	private ShardedRWRedisPool shardedRWRedisPool;
	
	private int threadNum = 20;
	
	private int threadNumber = 0;
	
	public void doExchange() {
		
		threadNumber = 0;
		
		ShardedJedis jedis = shardedRWRedisPool.borrowWriteResource();
		jedis.set("foxtest", "haha");
		String str = jedis.get("foxtest");
		log.warn("ret is " + str);
		
		ProxyPoolFlag proxyPoolFlag = proxyPoolFlagDAO.getByflag("proxy_pool");
		if("ING".equals(proxyPoolFlag.getStatus())){
			log.warn("<proxy:RegularHisTestService> proxyPoolFlag is ING ... his test service will start next time ... ");
			return ;
		}
		proxyPoolFlag.setStatus("ING");
		proxyPoolFlagDAO.update(proxyPoolFlag);
		log.info("<proxy:RegularHisTestService>  his test service is starting ... ");
		
		for(int i=0; i< threadNum; i++){
			List<ProxyPoolHis> keyList = new ArrayList<ProxyPoolHis>();
			allKeyMap.put(i, keyList);
		}
    
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadNum); 
		
    	try {
    		
    		List<ProxyPoolHis> proxyHisList = proxyPoolHisDAO.getUsableData();
    		log.info("<proxy:RegularHisTestService> res ProxyPoolHis List is " + proxyHisList.size());
        	
        	int len = proxyHisList.size()/threadNum + 1;
        	Thread thrlist[] = new Thread[threadNum];
        	
    		if(len != 0) {
        		int x=0, count=0;
            	for(ProxyPoolHis proxyHis : proxyHisList){
        			if(x!=0 && x<len*threadNum){
        				if(x%len ==0){
        					thrlist[count] = new Thread(this);
        					fixedThreadPool.execute(thrlist[count]);
        			    	count++;
        				}
        			}
        			allKeyMap.get(count).add(proxyHis);
        			x ++;
        		}
            	thrlist[count] = new Thread(this);
            	fixedThreadPool.execute(thrlist[count]);
    	    	count++;
    	    	fixedThreadPool.shutdown();
        	}
    	} catch (Exception e) {
    		log.error(e.getMessage(), e);
    	} finally {
    		boolean loop = true;  
            do {    
                try {
					loop = !fixedThreadPool.awaitTermination(2, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					log.error(e.getMessage(), e);
				} 
            } while(loop); 
            proxyPoolFlag.setStatus("ED");
     		proxyPoolFlagDAO.update(proxyPoolFlag);
    		log.info("<proxy:RegularHisTestService>  his test service is finish! ");
    	}
	}
	
	@Override
	public void run() {
		 
	 	List<ProxyPoolHis> proxyHisList = new ArrayList<ProxyPoolHis>();
		synchronized ("") {	
			proxyHisList = allKeyMap.get(threadNumber);
			threadNumber ++;
			log.info("<proxy:RegularHisTestService> thread number " + threadNumber + " is starting ... proxyHisList size is " + proxyHisList.size());
		}	    
		
		List<String> uagentList = userAgentDAO.getAllData();
		List<ProxyUrls> urlsList = proxyUrlsDAO.getAllData();
		try {
			int count = 0;
			for(ProxyPoolHis proxyHis : proxyHisList) {
				for(ProxyUrls proxyUrls : urlsList) {
					int ret = HttpClientUtil.testCommonHttp(proxyHis.getIp(), proxyHis.getPort(), proxyUrls.getName(), uagentList);
					ProxyPool proxy = ProxyPool.getInstantce(proxyHis);
					proxy.setUrlname(proxyUrls.getName());
					if(ret == 1) {
						count ++;
						proxy.setFailures(0);
					} else {
						proxy.setFailures(1);
					}
					proxy.setStatus(1);
					ProxyPool proxyOld = proxyPoolDAO.get(proxy.getIp(), proxy.getPort(), proxy.getUrlname(), proxy.getLevel());
					Date date = new Date();
					proxy.setUpdatetime(date);
					if(null!=proxyOld) {
						proxy.setId(proxyOld.getId());
						proxyPoolDAO.update(proxy);
					}else {
						proxy.setCreatetime(date);
						proxyPoolDAO.save(proxy);
					}
				}
				if(count >0) {
					proxyHis.setStatus(1);
				}else {
					proxyHis.setStatus(-1);
				}
				Date date = new Date();
				proxyHis.setUpdatetime(date);
				proxyPoolHisDAO.update(proxyHis);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    }
}