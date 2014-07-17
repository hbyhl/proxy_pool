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

import cn.focus.proxypool.dao.ProxyPoolDAO;
import cn.focus.proxypool.dao.ProxyPoolFlagDAO;
import cn.focus.proxypool.dao.UserAgentDAO;
import cn.focus.proxypool.model.ProxyPool;
import cn.focus.proxypool.model.ProxyPoolFlag;
import cn.focus.proxypool.utils.HttpClientUtil;

@Service
public class RegularTestService implements Runnable {
	
	private final static Logger log = Logger.getLogger(RegularTestService.class);
	
	private Map<Integer, List<ProxyPool>> allKeyMap = new HashMap<Integer, List<ProxyPool>>();
		
	@Autowired
	ProxyPoolDAO proxyPoolDAO;
	
	@Autowired
	private UserAgentDAO userAgentDAO;
	
	@Autowired
	private ProxyPoolFlagDAO proxyPoolFlagDAO;
	
	private int threadNum = 20;
	
	private int threadNumber = 0;
	
	public void doRegularTest(){
		
		threadNumber = 0;
		
		ProxyPoolFlag proxyPoolFlag = proxyPoolFlagDAO.getByflag("proxy_pool");
		if("ING".equals(proxyPoolFlag.getStatus())){
			log.info("<proxy:RegularTestService> proxyPoolFlag is ING ... test service will start next time ... ");
			return ;
		}
		proxyPoolFlag.setStatus("ING");
		proxyPoolFlagDAO.update(proxyPoolFlag);
		log.info("<proxy:RegularTestService>  test service is starting ... ");
		
		for(int i=0; i< threadNum; i++){
			List<ProxyPool> keyList = new ArrayList<ProxyPool>();
			allKeyMap.put(i, keyList);
		}
    
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadNum); 
		
		try {
			
	    	List<ProxyPool> proxyList = proxyPoolDAO.getUsableList();
	    	log.info("<proxy:RegularTestService> res ProxyPool List is " + proxyList.size());
	    	
	    	int len = proxyList.size()/threadNum + 1;
	    	Thread thrlist[] = new Thread[threadNum];
	    	if(len != 0) {
	    		int x=0, count=0;
	        	for(ProxyPool proxy : proxyList){
	    			if(x!=0 && x<len*threadNum){
	    				if(x%len ==0){
	    					thrlist[count] = new Thread(this);
	    					fixedThreadPool.execute(thrlist[count]);
	    			    	count++;
	    				}
	    			}
	    			allKeyMap.get(count).add(proxy);
	    			x ++;
	    		}
	        	thrlist[count] = new Thread(this);
	        	fixedThreadPool.execute(thrlist[count]);
		    	count++;
		    	fixedThreadPool.shutdown();
	    	}
		}catch (Exception e) {
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
    		log.info("<proxy:RegularTestService>  test service is finish! ");
		}
	}
	
	@Override
	public void run() {
		 
	 	List<ProxyPool> proxyList = new ArrayList<ProxyPool>();
		synchronized ("") {	
			proxyList = allKeyMap.get(threadNumber);
			threadNumber ++;
			log.info("<proxy:RegularTestService> thread number " + threadNumber + " is starting ... proxyList size is " + proxyList.size());
		}
		
		List<String> uagentList = userAgentDAO.getAllData();
    	
    	try {
    		List<ProxyPool> resList = HttpClientUtil.testProxyHttp(proxyList, uagentList);	
    
    		for(ProxyPool proxyPool : resList) {
    			if(proxyPool.getStatus() == -1) {
    				int failure = proxyPool.getFailures();
    				failure += 1;
    				if(failure <10) {
    					proxyPool.setStatus(1);
    				}
    				proxyPool.setFailures(failure);
    			} else {
    				proxyPool.setFailures(0);
    			}
    			Date updatetime = new Date();
    			proxyPool.setUpdatetime(updatetime);
    			proxyPoolDAO.update(proxyPool);
    		}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    }
}