package cn.focus.proxypool.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.proxypool.dao.ProxyPoolDAO;
import cn.focus.proxypool.dao.ProxyUrlsDAO;
import cn.focus.proxypool.model.ProxyPool;
import cn.focus.proxypool.model.ProxyUrls;
import cn.focus.proxypool.vo.ProxyResponse;


@Service
public class ProxyPoolService {
	
	@Autowired
	private ProxyPoolDAO proxyPoolDAO;
	
	@Autowired
	private ProxyUrlsDAO proxyUrlsDAO;
	
    public List<ProxyResponse> getUsableProxy(Integer type, Integer level) {
		
    	List<ProxyResponse> proxyResponseList = new ArrayList<ProxyResponse>();
    	ProxyUrls proxyUrls = proxyUrlsDAO.get(type);
    	if(null != proxyUrls) {
    		List<ProxyPool> proxyPoolList = proxyPoolDAO.getUsableList(proxyUrls.getName(), level, 1);
    		for(ProxyPool proxyPool: proxyPoolList) {
    			ProxyResponse proxyResponse = new ProxyResponse();
    			proxyResponse.setIp(proxyPool.getIp());
    			proxyResponse.setPort(proxyPool.getPort());
    			proxyResponseList.add(proxyResponse);
    		}
    	}
    	return proxyResponseList;
	}
}