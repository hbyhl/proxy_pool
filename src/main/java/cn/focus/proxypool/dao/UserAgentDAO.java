package cn.focus.proxypool.dao;

import java.util.List;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import cn.focus.proxypool.model.UserAgent;
@DAO(catalog="focus.proxy")
public interface UserAgentDAO {

	@SQL("SELECT  id, url, type FROM user_agent WHERE id = :1")
	UserAgent get(Integer id );

	
	@SQL("SELECT  id, url, type FROM user_agent WHERE id IN (:1)")
	List<UserAgent> getList(List<Integer> idList );
	
	
	@SQL("SELECT  url FROM user_agent")
	List<String> getAllData( );
	
	
	@SQL("SELECT  url FROM user_agent WHERE type = :1")
	List<String> getData(Integer type );
	
	
	@SQL("SELECT  id, url, type FROM user_agent WHERE id IN (:1) ORDER BY find_in_set(id , :2)")
	List<UserAgent> getOrderList(List<Integer> idList, String orderIdsStr );
    
	    @ReturnGeneratedKeys
	@SQL("INSERT INTO user_agent ("+
		    	       "#if(:1.url!=null){url}"+
		    	       "#if(:1.type!=null){,type}"+
	    		") VALUES("+
    	    	       "#if(:1.url!=null){:1.url}"+
    	    	       "#if(:1.type!=null){,:1.type}"+
	    		")") 
	Long save(UserAgent useragent );
	
		@SQL("UPDATE user_agent SET id=:1.id " +
		    		    	       "#if(:1.url!=null){,url=:1.url}"+
	    		    	       "#if(:1.type!=null){,type=:1.type}"+
	    	    " WHERE id=:1.id ")
	void update(UserAgent useragent );
	
	@SQL("DELETE FROM user_agent WHERE id= :1")
	int delete(Integer id);
	
	@SQL("SELECT COUNT(1) FROM user_agent")
	int count();
	
}