package cn.focus.proxypool.controllers;

import java.util.ArrayList;

import java.util.List;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.focus.proxypool.impl.ProxyPoolService;
import cn.focus.proxypool.impl.RegularHisTestService;
import cn.focus.proxypool.impl.RegularTestService;
import cn.focus.proxypool.impl.UserAgentService;
import cn.focus.proxypool.vo.JsonResponse;
import cn.focus.proxypool.vo.ProxyResponse;

@Path("/")
public class HelloController {

    @Autowired
    ProxyPoolService proxyPoolService;
    
    @Autowired
    RegularHisTestService regularHisTestService;
    
    @Autowired
    RegularTestService regularTestService;
    
    @Autowired
    UserAgentService userAgentService;
    
    private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Get("proxylist")
    public String getProxyList(@Param("type") Integer type, @Param("level") int level) {

    	List<ProxyResponse> responseList = new ArrayList<ProxyResponse>();
        try {
        	responseList = proxyPoolService.getUsableProxy(type, level);
        	return JsonResponse.ok(responseList);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
            return JsonResponse.badResult("");
        }
    }
    
    @Get("test")
    public void doTestService() {

        try {
        	regularTestService.doRegularTest();
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }
    }
    
    @Get("addhis")
    public void doHisService() {

        try {
        	regularHisTestService.doExchange();
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }
    }
    
    @Get("uagentlist")
    public String get(@Param("type") Integer type ) {

        try {
        	List<String> resList = userAgentService.getUsableProxy(type);
        	return JsonResponse.ok(resList);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	return null;
        }
    }
    
}