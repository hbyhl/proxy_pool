package cn.focus.proxypool.dao;

import java.util.List;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import cn.focus.proxypool.model.ProxyPoolFlag;
@DAO(catalog="focus.proxy")
public interface ProxyPoolFlagDAO {

	@SQL("SELECT  id, flag, status FROM proxy_pool_flag WHERE id = :1")
	ProxyPoolFlag get(Integer id );
	
	
	@SQL("SELECT  id, flag, status FROM proxy_pool_flag WHERE flag = :1")
	ProxyPoolFlag getByflag(String flag );

	
	@SQL("SELECT  id, flag, status FROM proxy_pool_flag WHERE id IN (:1)")
	List<ProxyPoolFlag> getList(List<Integer> idList );
	
	
	@SQL("SELECT  id, flag, status FROM proxy_pool_flag WHERE id IN (:1) ORDER BY find_in_set(id , :2)")
	List<ProxyPoolFlag> getOrderList(List<Integer> idList, String orderIdsStr );
    
	    @ReturnGeneratedKeys
	@SQL("INSERT INTO proxy_pool_flag ("+
				      /* "id"+*/
	    	    				    	       "#if(:1.flag!=null){flag}"+
	    				    	       "#if(:1.status!=null){,status}"+
	    		") VALUES("+
		    	     /*  ":1.id"+*/
	    	    		    	    	       "#if(:1.flag!=null){:1.flag}"+
	    		    	    	       "#if(:1.status!=null){,:1.status}"+
	    		")") 
	Long save(ProxyPoolFlag proxypoolflag );
	
		@SQL("UPDATE proxy_pool_flag SET id=:1.id " +
		    		    	       "#if(:1.flag!=null){,flag=:1.flag}"+
	    		    	       "#if(:1.status!=null){,status=:1.status}"+
	    	    " WHERE id=:1.id ")
	void update(ProxyPoolFlag proxypoolflag );
	
	@SQL("DELETE FROM proxy_pool_flag WHERE id= :1")
	int delete(Integer id);
	
	@SQL("SELECT COUNT(1) FROM proxy_pool_flag")
	int count();
	
}