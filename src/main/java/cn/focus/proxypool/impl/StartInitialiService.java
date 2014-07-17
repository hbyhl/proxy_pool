package cn.focus.proxypool.impl;

import javax.annotation.PostConstruct;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.proxypool.vo.UrlsVo;

@Service
public class StartInitialiService {
	
	/*private final static Logger log = Logger.getLogger(RegularHisTestService.class);
	
	@Autowired
	private RegularHisTestService regularHisTestService;
	
	public StartInitialiService() {
		
	}
	
	public StartInitialiService(RegularHisTestService regularHisTestService) {
		this.regularHisTestService = regularHisTestService;
	}

	@PostConstruct
    public void init( ) {
		
		log.info("<StartInitialiService:init> his database is initialize ... ");
		regularHisTestService.doExchange();
	}	*/
}