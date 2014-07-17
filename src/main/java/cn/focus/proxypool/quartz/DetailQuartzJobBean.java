/*package cn.focus.proxypool.quartz;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DetailQuartzJobBean extends QuartzJobBean {  
   
    private String targetObject;    
    private String targetMethod;    
    private ApplicationContext ctx;    
   
    protected void executeInternal(JobExecutionContext context)    
          throws JobExecutionException {    
       try {     
           ctx = (ApplicationContext)context.getScheduler().getContext().get("applicationContextKey");
           Object otargetObject = ctx.getBean(targetObject);    
           Method m = null;    
           try {    
               m = otargetObject.getClass().getMethod(targetMethod,new Class[] {});    
               m.invoke(otargetObject, new Object[]{});    
           } catch (SecurityException e) {
        	   e.printStackTrace();
           } catch (NoSuchMethodException e) {   
        	   e.printStackTrace();
           }    
       } catch (Exception e) {    
    	   e.printStackTrace();
       }    
    }    
   
    public void setTargetObject(String targetObject) {    
        this.targetObject = targetObject;    
    }    
   
    public void setTargetMethod(String targetMethod) {    
        this.targetMethod = targetMethod;    
    }

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ctx = applicationContext;
	}    
}  
*/