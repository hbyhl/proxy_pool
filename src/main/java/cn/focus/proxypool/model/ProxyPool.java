package cn.focus.proxypool.model; 

import cn.focus.dc.commons.model.BaseObject;
import cn.focus.dc.commons.annotation.PrimaryKey;

public class ProxyPool extends BaseObject {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private Integer id;

    private String ip;

    private Integer port;

    private String urlname;

    private Integer level;

    private Integer status;

    private java.util.Date createtime;

    private java.util.Date updatetime;

    private Integer failures;
    
    private String source;

	public void copy(ProxyPool proxypool){
               this.id = proxypool.id;
               this.ip = proxypool.ip;
               this.port = proxypool.port;
               this.urlname = proxypool.urlname;
               this.level = proxypool.level;
               this.status = proxypool.status;
               this.createtime = proxypool.createtime;
               this.updatetime = proxypool.updatetime;
               this.failures = proxypool.failures;
               this.source = proxypool.source;
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
    
    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
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
    
    public static ProxyPool getInstantce(Integer id,String ip,Integer port,String urlname,Integer level,Integer status,java.util.Date createtime,java.util.Date updatetime,Integer failures,String source){
      ProxyPool proxypool = new ProxyPool();
               proxypool.setId(id);
               proxypool.setIp(ip);
               proxypool.setPort(port);
               proxypool.setUrlname(urlname);
               proxypool.setLevel(level);
               proxypool.setStatus(status);
               proxypool.setCreatetime(createtime);
               proxypool.setUpdatetime(updatetime);
               proxypool.setFailures(failures);
               proxypool.setSource(source);
            return proxypool;
    }
    
    public static ProxyPool getInstantce(ProxyPoolHis proxyPoolHis){
        ProxyPool proxypool = new ProxyPool();
                 proxypool.setIp(proxyPoolHis.getIp());
                 proxypool.setPort(proxyPoolHis.getPort());
                 proxypool.setLevel(proxyPoolHis.getLevel());
                 proxypool.setStatus(proxyPoolHis.getStatus());
                 proxypool.setCreatetime(proxyPoolHis.getCreatetime());
                 proxypool.setUpdatetime(proxyPoolHis.getUpdatetime());
                 proxypool.setFailures(proxyPoolHis.getFailures());
                 proxypool.setSource(proxyPoolHis.getSource());
              return proxypool;
      }
}