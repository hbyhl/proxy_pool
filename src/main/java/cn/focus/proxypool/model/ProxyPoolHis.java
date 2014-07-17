package cn.focus.proxypool.model; 

import cn.focus.dc.commons.model.BaseObject;
import cn.focus.dc.commons.annotation.PrimaryKey;

public class ProxyPoolHis extends BaseObject {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private Integer id;

    private String ip;

    private Integer port;

    private Integer level;

    private Integer status;

    private java.util.Date createtime;

    private java.util.Date updatetime;

    private Integer failures;
    
    private String source;

	public void copy(ProxyPoolHis proxypoolhis){
               this.id = proxypoolhis.id;
               this.ip = proxypoolhis.ip;
               this.port = proxypoolhis.port;
               this.level = proxypoolhis.level;
               this.status = proxypoolhis.status;
               this.createtime = proxypoolhis.createtime;
               this.updatetime = proxypoolhis.updatetime;
               this.failures = proxypoolhis.failures;
               this.source = proxypoolhis.source;
          }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
    
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public java.util.Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(java.util.Date createtime) {
        this.createtime = createtime;
    }
    
    public java.util.Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(java.util.Date updatetime) {
        this.updatetime = updatetime;
    }
    
    public Integer getFailures() {
        return failures;
    }

    public void setFailures(Integer failures) {
        this.failures = failures;
    }
    
    public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
    
    public static ProxyPoolHis getInstantce(Integer id,String ip,Integer port,Integer level,Integer status,java.util.Date createtime,java.util.Date updatetime,Integer failures,String source){
      ProxyPoolHis proxypoolhis = new ProxyPoolHis();
               proxypoolhis.setId(id);
               proxypoolhis.setIp(ip);
               proxypoolhis.setPort(port);
               proxypoolhis.setLevel(level);
               proxypoolhis.setStatus(status);
               proxypoolhis.setCreatetime(createtime);
               proxypoolhis.setUpdatetime(updatetime);
               proxypoolhis.setFailures(failures);
               proxypoolhis.setSource(source);
            return proxypoolhis;
    }

}