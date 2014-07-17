package cn.focus.proxypool.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.proxypool.dao.UserAgentDAO;

@Service
public class UserAgentService {
	
	@Autowired
	private UserAgentDAO userAgentDAO;
	
    public List<String> getUsableProxy(Integer type ) {
		
		List<String> agentList = userAgentDAO.getData(type);
		return agentList;
	} 

}