package cn.focus.proxypool.model; 

import cn.focus.dc.commons.model.BaseObject;
import cn.focus.dc.commons.annotation.PrimaryKey;

public class ProxyUrls extends BaseObject {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private Integer id;

    private String name;

    private String url;

    public void copy(ProxyUrls proxyurls){
               this.id = proxyurls.id;
               this.name = proxyurls.name;
               this.url = proxyurls.url;
          }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public static ProxyUrls getInstantce(Integer id,String name,String url){
      ProxyUrls proxyurls = new ProxyUrls();
               proxyurls.setId(id);
               proxyurls.setName(name);
               proxyurls.setUrl(url);
            return proxyurls;
    }

}