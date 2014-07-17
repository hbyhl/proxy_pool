package cn.focus.proxypool.model; 

import cn.focus.dc.commons.model.BaseObject;
import cn.focus.dc.commons.annotation.PrimaryKey;

public class UserAgent extends BaseObject {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private Integer id;

    private String url;

    private Integer type;

    public void copy(UserAgent useragent){
               this.id = useragent.id;
               this.url = useragent.url;
               this.type = useragent.type;
          }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    public static UserAgent getInstantce(Integer id,String url,Integer type){
      UserAgent useragent = new UserAgent();
               useragent.setId(id);
               useragent.setUrl(url);
               useragent.setType(type);
            return useragent;
    }

}