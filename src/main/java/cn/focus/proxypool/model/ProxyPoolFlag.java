package cn.focus.proxypool.model; 

import cn.focus.dc.commons.model.BaseObject;
import cn.focus.dc.commons.annotation.PrimaryKey;

public class ProxyPoolFlag extends BaseObject {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private Integer id;

    private String flag;

    private String status;

    public void copy(ProxyPoolFlag proxypoolflag){
               this.id = proxypoolflag.id;
               this.flag = proxypoolflag.flag;
               this.status = proxypoolflag.status;
          }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public static ProxyPoolFlag getInstantce(Integer id,String flag,String status){
      ProxyPoolFlag proxypoolflag = new ProxyPoolFlag();
               proxypoolflag.setId(id);
               proxypoolflag.setFlag(flag);
               proxypoolflag.setStatus(status);
            return proxypoolflag;
    }

}